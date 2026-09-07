package com.example.data.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.net.URLEncoder
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

data class BibleVerseResult(
    val reference: String,
    val text: String,
    val translationName: String,
    val verses: List<BibleVerseItem> = emptyList()
)

data class BibleVerseItem(
    val bookName: String,
    val chapter: Int,
    val verse: Int,
    val text: String
)

/**
 * Servicio de integración con la API gratuita y pública Bible-API (https://bible-api.com)
 * Permite consultar pasajes y versículos en español (Reina-Valera 1909 u otras versiones).
 */
class BibleApiService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(8, TimeUnit.SECONDS)
        .build()

    private val cache = ConcurrentHashMap<String, BibleVerseResult>()

    /**
     * Consulta un versículo o pasaje bíblico en español
     * Ej: "genesis 1:1", "salmos 23", "juan 3:16", "exodo 3:14"
     */
    suspend fun fetchVerseInSpanish(rawQuery: String): BibleVerseResult? = withContext(Dispatchers.IO) {
        val cleanQuery = sanitizeBibleQuery(rawQuery)
        if (cleanQuery.isBlank()) return@withContext null

        val cacheKey = cleanQuery.lowercase().trim()
        cache[cacheKey]?.let { return@withContext it }

        try {
            val encodedQuery = URLEncoder.encode(cleanQuery, "UTF-8")
            // Usamos translation=rvr1909 para español
            val url = "https://bible-api.com/$encodedQuery?translation=rvr1909"
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val bodyStr = response.body?.string() ?: ""
                val json = JSONObject(bodyStr)

                val ref = json.optString("reference", rawQuery)
                val text = json.optString("text", "").trim()
                val transName = json.optString("translation_name", "Reina-Valera 1909")

                val versesList = mutableListOf<BibleVerseItem>()
                val versesArr = json.optJSONArray("verses")
                if (versesArr != null) {
                    for (i in 0 until versesArr.length()) {
                        val vObj = versesArr.getJSONObject(i)
                        versesList.add(
                            BibleVerseItem(
                                bookName = vObj.optString("book_name", ""),
                                chapter = vObj.optInt("chapter", 1),
                                verse = vObj.optInt("verse", 1),
                                text = vObj.optString("text", "").trim()
                            )
                        )
                    }
                }

                if (text.isNotBlank()) {
                    val result = BibleVerseResult(
                        reference = ref,
                        text = text,
                        translationName = transName,
                        verses = versesList
                    )
                    cache[cacheKey] = result
                    return@withContext result
                }
            } else {
                // Probar sin translation parameter como fallback si la versión rvr1909 no estuviera indexada para ese término
                val fallbackUrl = "https://bible-api.com/$encodedQuery"
                val fallbackReq = Request.Builder().url(fallbackUrl).build()
                val fallbackResp = client.newCall(fallbackReq).execute()
                if (fallbackResp.isSuccessful) {
                    val fBody = fallbackResp.body?.string() ?: ""
                    val fJson = JSONObject(fBody)
                    val ref = fJson.optString("reference", rawQuery)
                    val text = fJson.optString("text", "").trim()
                    if (text.isNotBlank()) {
                        val result = BibleVerseResult(
                            reference = ref,
                            text = text,
                            translationName = fJson.optString("translation_name", "Standard"),
                            verses = emptyList()
                        )
                        cache[cacheKey] = result
                        return@withContext result
                    }
                }
            }
        } catch (e: Exception) {
            Log.w("BibleApiService", "Error fetching from Bible-API for $rawQuery: ${e.message}")
        }

        null
    }

    private fun sanitizeBibleQuery(query: String): String {
        var q = query.trim()
        // Normalizar acentos y nombres en español para bible-api.com
        val replacements = mapOf(
            "génesis" to "genesis",
            "éxodo" to "exodus",
            "exodo" to "exodus",
            "levítico" to "leviticus",
            "levitico" to "leviticus",
            "números" to "numbers",
            "numeros" to "numbers",
            "deuteronomio" to "deuteronomy",
            "josué" to "joshua",
            "josue" to "joshua",
            "jueces" to "judges",
            "salmos" to "psalms",
            "salmo" to "psalms",
            "proverbios" to "proverbs",
            "cantar de los cantares" to "song of solomon",
            "cantares" to "song of solomon",
            "isaías" to "isaiah",
            "isaias" to "isaiah",
            "jeremías" to "jeremiah",
            "jeremias" to "jeremiah",
            "lamentaciones" to "lamentations",
            "ezequiel" to "ezekiel",
            "mateo" to "matthew",
            "marcos" to "mark",
            "lucas" to "luke",
            "juan" to "john",
            "hechos" to "acts",
            "romanos" to "romans",
            "apocalipsis" to "revelation"
        )

        for ((es, en) in replacements) {
            if (q.startsWith(es, ignoreCase = true)) {
                q = q.replaceFirst(Regex("(?i)^$es"), en)
                break
            }
        }
        return q
    }
}
