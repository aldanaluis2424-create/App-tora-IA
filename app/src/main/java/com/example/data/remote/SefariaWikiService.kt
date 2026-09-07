package com.example.data.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

data class SefariaTextResult(
    val ref: String,
    val hebrewText: String,
    val englishText: String
)

data class WikipediaSummaryResult(
    val title: String,
    val description: String?,
    val extract: String
)

data class ExternalContextEnrichment(
    val sefaria: SefariaTextResult? = null,
    val wikipedia: WikipediaSummaryResult? = null,
    val bibleApi: BibleVerseResult? = null
) {
    fun toContextPrompt(): String {
        val sb = StringBuilder()
        if (bibleApi != null && bibleApi.text.isNotBlank()) {
            sb.append("\n[TEXTO BÍBLICO EN ESPAÑOL DESDE BIBLE-API (${bibleApi.reference} - ${bibleApi.translationName})]:\n")
            sb.append(bibleApi.text).append("\n")
        }
        if (sefaria != null && (sefaria.hebrewText.isNotBlank() || sefaria.englishText.isNotBlank())) {
            sb.append("\n[TEXTO SAGRADO DESDE SEFARIA API (${sefaria.ref})]:\n")
            if (sefaria.hebrewText.isNotBlank()) {
                sb.append("Texto Hebreo Original: ${sefaria.hebrewText}\n")
            }
            if (sefaria.englishText.isNotBlank()) {
                sb.append("Versión / Traducción: ${sefaria.englishText}\n")
            }
        }
        if (wikipedia != null && wikipedia.extract.isNotBlank()) {
            sb.append("\n[CONTEXTO HISTÓRICO/ENCICLOPÉDICO WIKIPEDIA API (${wikipedia.title})]:\n")
            if (!wikipedia.description.isNullOrBlank()) {
                sb.append("Descripción: ${wikipedia.description}\n")
            }
            sb.append("Resumen: ${wikipedia.extract}\n")
        }
        return sb.toString().trim()
    }
}

/**
 * Servicio de integración para las APIs públicas de Sefaria y Wikipedia.
 * Proporciona acceso a textos bíblicos, comentarios tradicionales y contexto enciclopédico.
 */
class SefariaWikiService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(4, TimeUnit.SECONDS)
        .readTimeout(6, TimeUnit.SECONDS)
        .build()

    private val sefariaCache = java.util.concurrent.ConcurrentHashMap<String, SefariaTextResult>()
    private val wikiCache = java.util.concurrent.ConcurrentHashMap<String, WikipediaSummaryResult>()
    private val bibleApiService = BibleApiService()

    /**
     * Script JavaScript representativo para consultas en entornos web o WebView
     */
    val jsScriptFunction: String = """
        async function fetchSefariaWikiAndBible(refOrTopic) {
            let sefariaData = null;
            let wikiData = null;
            let bibleData = null;
            try {
                const sefariaRes = await fetch(`https://www.sefaria.org/api/texts/` + encodeURIComponent(refOrTopic) + `?context=0`);
                if (sefariaRes.ok) sefariaData = await sefariaRes.json();
            } catch(e) { console.warn('Sefaria fetch error', e); }
            
            try {
                const bibleRes = await fetch(`https://bible-api.com/` + encodeURIComponent(refOrTopic) + `?translation=rvr1909`);
                if (bibleRes.ok) bibleData = await bibleRes.json();
            } catch(e) { console.warn('Bible-API fetch error', e); }

            try {
                const wikiRes = await fetch(`https://es.wikipedia.org/api/rest_v1/page/summary/` + encodeURIComponent(refOrTopic));
                if (wikiRes.ok) wikiData = await wikiRes.json();
            } catch(e) { console.warn('Wiki fetch error', e); }
            
            return { sefaria: sefariaData, bible: bibleData, wikipedia: wikiData };
        }
    """.trimIndent()

    suspend fun fetchEnrichment(query: String): ExternalContextEnrichment = withContext(Dispatchers.IO) {
        val trimmed = query.trim()
        if (trimmed.isBlank()) return@withContext ExternalContextEnrichment()

        withTimeoutOrNull(2500L) {
            val sefariaRef = parseToSefariaRef(trimmed)
            val sefariaDeferred = if (!sefariaRef.isNullOrBlank()) {
                async(Dispatchers.IO) { fetchSefariaText(sefariaRef) }
            } else null

            val wikiTerm = extractWikiSearchTerm(trimmed)
            val wikiDeferred = if (!wikiTerm.isNullOrBlank()) {
                async(Dispatchers.IO) { fetchWikipediaSummary(wikiTerm) }
            } else null

            ExternalContextEnrichment(
                sefaria = sefariaDeferred?.await(),
                wikipedia = wikiDeferred?.await(),
                bibleApi = null
            )
        } ?: ExternalContextEnrichment()
    }

    private fun looksLikeBibleRef(text: String): Boolean {
        return text.any { it.isDigit() } && (text.contains(":") || text.contains(" "))
    }

    suspend fun fetchSefariaText(sefariaRef: String): SefariaTextResult? = withContext(Dispatchers.IO) {
        val cacheKey = sefariaRef.lowercase().trim()
        sefariaCache[cacheKey]?.let { return@withContext it }

        try {
            val url = "https://www.sefaria.org/api/texts/${URLEncoder.encode(sefariaRef, "UTF-8")}?context=0"
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) return@withContext null

            val bodyString = response.body?.string() ?: return@withContext null
            val json = JSONObject(bodyString)

            val ref = json.optString("ref", sefariaRef)
            val heText = extractTextFromJson(json.opt("he"))
            val enText = extractTextFromJson(json.opt("text"))

            if (heText.isBlank() && enText.isBlank()) return@withContext null

            val result = SefariaTextResult(
                ref = ref,
                hebrewText = cleanHtmlTags(heText),
                englishText = cleanHtmlTags(enText)
            )
            sefariaCache[cacheKey] = result
            result
        } catch (e: Exception) {
            Log.w("SefariaWikiService", "Error querying Sefaria for $sefariaRef: ${e.message}")
            null
        }
    }

    suspend fun fetchWikipediaSummary(topic: String): WikipediaSummaryResult? = withContext(Dispatchers.IO) {
        val cacheKey = topic.lowercase().trim()
        wikiCache[cacheKey]?.let { return@withContext it }

        try {
            val encoded = URLEncoder.encode(topic.trim(), "UTF-8")
            val url = "https://es.wikipedia.org/api/rest_v1/page/summary/$encoded"
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) return@withContext null

            val bodyString = response.body?.string() ?: return@withContext null
            val json = JSONObject(bodyString)

            val title = json.optString("title", topic)
            val desc = json.optString("description", "")
            val extract = json.optString("extract", "")

            if (extract.isBlank()) return@withContext null

            val result = WikipediaSummaryResult(
                title = title,
                description = if (desc.isNotBlank()) desc else null,
                extract = cleanHtmlTags(extract)
            )
            wikiCache[cacheKey] = result
            result
        } catch (e: Exception) {
            Log.w("SefariaWikiService", "Error querying Wikipedia for $topic: ${e.message}")
            null
        }
    }

    private fun extractTextFromJson(obj: Any?): String {
        return when (obj) {
            is String -> obj
            is JSONArray -> {
                val list = mutableListOf<String>()
                for (i in 0 until obj.length()) {
                    val item = obj.get(i)
                    list.add(extractTextFromJson(item))
                }
                list.filter { it.isNotBlank() }.joinToString(" ")
            }
            else -> ""
        }
    }

    private fun cleanHtmlTags(input: String): String {
        return input.replace(Regex("<[^>]*>"), "").replace("&quot;", "\"").replace("&amp;", "&").trim()
    }

    private fun parseToSefariaRef(rawQuery: String): String? {
        val q = rawQuery.trim().lowercase()

        val bookMappings = mapOf(
            "génesis" to "Genesis", "genesis" to "Genesis", "bereshit" to "Genesis", "bereshis" to "Genesis",
            "éxodo" to "Exodus", "exodo" to "Exodus", "shemot" to "Exodus", "shemos" to "Exodus",
            "levítico" to "Leviticus", "levitico" to "Leviticus", "vayikra" to "Leviticus",
            "números" to "Numbers", "numeros" to "Numbers", "bamidbar" to "Numbers",
            "deuteronomio" to "Deuteronomy", "devarim" to "Deuteronomy",
            "josué" to "Joshua", "josue" to "Joshua", "yehoshua" to "Joshua",
            "jueces" to "Judges", "shoftim" to "Judges",
            "rut" to "Ruth",
            "1 samuel" to "I_Samuel", "i samuel" to "I_Samuel", "1samuel" to "I_Samuel", "shmuel 1" to "I_Samuel",
            "2 samuel" to "II_Samuel", "ii samuel" to "II_Samuel", "2samuel" to "II_Samuel", "shmuel 2" to "II_Samuel",
            "1 reyes" to "I_Kings", "i reyes" to "I_Kings", "1reyes" to "I_Kings", "melajim 1" to "I_Kings",
            "2 reyes" to "II_Kings", "ii reyes" to "II_Kings", "2reyes" to "II_Kings", "melajim 2" to "II_Kings",
            "isaías" to "Isaiah", "isaias" to "Isaiah", "yeshayahu" to "Isaiah", "yeshayahu" to "Isaiah",
            "jeremías" to "Jeremiah", "jeremias" to "Jeremiah", "yirmeyahu" to "Jeremiah",
            "ezequiel" to "Ezekiel", "yejezkel" to "Ezekiel",
            "daniel" to "Daniel",
            "oseas" to "Hosea", "hoshea" to "Hosea",
            "joel" to "Joel", "yoel" to "Joel",
            "amós" to "Amos", "amos" to "Amos",
            "abdías" to "Obadiah", "abdias" to "Obadiah", "ovadiah" to "Obadiah",
            "jonás" to "Jonah", "jonas" to "Jonah", "yonah" to "Jonah",
            "miqueas" to "Micah", "micah" to "Micah", "mija" to "Micah",
            "nahúm" to "Nahum", "nahum" to "Nahum", "najum" to "Nahum",
            "habacuc" to "Habakkuk", "javakuk" to "Habakkuk",
            "sofonías" to "Zephaniah", "sofonias" to "Zephaniah", "tzefaniah" to "Zephaniah",
            "hageo" to "Haggai", "jagai" to "Haggai",
            "zacarías" to "Zechariah", "zacarias" to "Zechariah", "zejariah" to "Zechariah",
            "malaquías" to "Malachi", "malaquias" to "Malachi", "malaji" to "Malachi",
            "salmos" to "Psalms", "salmo" to "Psalms", "tehilim" to "Psalms", "psalms" to "Psalms",
            "proverbios" to "Proverbs", "proverbio" to "Proverbs", "mishlei" to "Proverbs",
            "job" to "Job", "iyov" to "Job",
            "cantar de los cantares" to "Song_of_Songs", "cantares" to "Song_of_Songs", "shir hashirim" to "Song_of_Songs",
            "lamentaciones" to "Lamentations", "eijah" to "Lamentations",
            "eclesiastés" to "Ecclesiastes", "eclesiastes" to "Ecclesiastes", "kohelet" to "Ecclesiastes",
            "ester" to "Esther",
            "esdras" to "Ezra", "ezra" to "Ezra",
            "nehemías" to "Nehemiah", "nehemias" to "Nehemiah", "nejemiyah" to "Nehemiah",
            "1 crónicas" to "I_Chronicles", "1cronicas" to "I_Chronicles",
            "2 crónicas" to "II_Chronicles", "2cronicas" to "II_Chronicles"
        )

        // Comprobar patrón Libro Cap:Verso (ej: Genesis 1:1 o Salmos 23)
        val pattern = Pattern.compile("(?i)^([a-záéíóúñ\\s0-9]+?)\\s+(\\d+)(?:[:.](\\d+))?$")
        val matcher = pattern.matcher(q)
        if (matcher.find()) {
            val rawBook = matcher.group(1)?.trim() ?: ""
            val chapter = matcher.group(2) ?: "1"
            val verse = matcher.group(3)

            val sefariaBook = bookMappings[rawBook] ?: rawBook.capitalizeFirstLetter()
            return if (!verse.isNullOrBlank()) {
                "$sefariaBook.$chapter.$verse"
            } else {
                "$sefariaBook.$chapter"
            }
        }

        // Si es solo nombre del libro conocido
        bookMappings[q]?.let {
            return "$it.1.1"
        }

        return null
    }

    private fun extractWikiSearchTerm(rawQuery: String): String? {
        val q = rawQuery.trim()
        if (q.length < 2) return null

        // Si es versículo numérico, Wikipedia no tiene páginas de versículos aislados, pero sí de libros
        val clean = q.replace(Regex("\\d+[:.]?\\d*"), "").trim()
        if (clean.length in 2..50) {
            return clean
        }
        return q.take(40)
    }

    private fun String.capitalizeFirstLetter(): String {
        return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}
