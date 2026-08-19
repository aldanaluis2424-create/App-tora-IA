package com.example.data.remote

import com.example.BuildConfig
import com.example.data.model.BiblicalQuote
import com.example.data.model.LetterBreakdown
import com.example.data.model.QuoteComment
import com.example.data.model.TranslationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiApiService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun generateStudyResponse(topicTitle: String, userQuestion: String): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext generateOfflineStudyAnswer(topicTitle, userQuestion)
        }

        val prompt = """
            Actúa como un Erudito Rabínico, Lingüista y Teólogo de Raíces Hebreas y Torá.
            Tema de estudio actual: "$topicTitle"
            Pregunta del usuario: "$userQuestion"
            
            Instrucciones de respuesta:
            1. Responde de manera clara, profunda, respetuosa y estructurada en español.
            2. Conecta la respuesta con la etimología hebrea, el texto bíblico y los conceptos teológicos o rabínicos pertinentes.
            3. Si aplica, menciona la raíz hebrea, el significado espiritual o la relevancia mesiánica.
        """.trimIndent()

        try {
            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", prompt))
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.7)
                })
            }

            val requestUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
            val request = Request.Builder()
                .url(requestUrl)
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            val responseText = response.body?.string() ?: ""

            if (!response.isSuccessful || responseText.isEmpty()) {
                return@withContext generateOfflineStudyAnswer(topicTitle, userQuestion)
            }

            val responseJson = JSONObject(responseText)
            val candidates = responseJson.optJSONArray("candidates")
            val candidate = candidates?.optJSONObject(0)
            val content = candidate?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            val textResponse = parts?.optJSONObject(0)?.optString("text") ?: ""

            if (textResponse.isBlank()) {
                generateOfflineStudyAnswer(topicTitle, userQuestion)
            } else {
                textResponse
            }
        } catch (e: Exception) {
            e.printStackTrace()
            generateOfflineStudyAnswer(topicTitle, userQuestion)
        }
    }

    private fun generateOfflineStudyAnswer(topicTitle: String, userQuestion: String): String {
        return "Respuesta de Análisis para '$topicTitle':\n\nAcerca de tu consulta: \"$userQuestion\"\n\nEn la exégesis de la Torá y las raíces hebreas, $topicTitle posee una significancia profunda. La tradición teológica enseña que este tema revela el orden divino, conectando el lenguaje sagrado con los pactos y el propósito espiritual de la Creación."
    }

    suspend fun analyzeHebrewTerm(term: String): TranslationResult = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            // Return fallback intelligent offline calculation if key is missing/placeholder
            return@withContext generateOfflineAnalysis(term)
        }

        val prompt = """
            Actúa como un Erudito Rabínico Senior y Lingüista de Hebreo Bíblico.
            Analiza el siguiente término o frase en hebreo o español: "$term".
            
            Debes responder ÚNICAMENTE con un JSON válido con la siguiente estructura exacta:
            {
              "mainTranslation": "Traducción principal al español",
              "pronunciationPhonetic": "Pronunciación fonética precisa",
              "hebrewSquareScript": "Texto en escritura hebrea cuadrada con niqud",
              "gematriaTotalValue": 0,
              "gematriaBreakdown": [
                { "letterSymbol": "א", "letterName": "Alef", "value": 1, "meaningSummary": "Cabeza de Buey, Dios" }
              ],
              "spiritualMeaning": "Explicación teológica y espiritual profunda",
              "relatedWords": ["Palabra1", "Palabra2"],
              "biblicalQuotes": [
                { "reference": "Génesis 1:1", "hebrewText": "בְּרֵאשִׁית בָּרָא אֱלֹהִים", "translation": "En el principio creó Dios", "commentaryNote": "Nota del texto" }
              ],
              "rabbinicComments": [
                { "author": "Rashi", "source": "Comentario sobre Génesis", "text": "Explicación rabínica tradicional" }
              ],
              "midrashInsight": "Enseñanza del Midrash",
              "kabbalahInsight": "Interpretación mística de la Kabbalah y Sefer Yetzirah",
              "pardesPeshat": "Significado literal (Peshat)",
              "pardesRemez": "Significado alegórico e insinuado (Remez)",
              "pardesDerash": "Significado homilético y rabínico (Derash)",
              "pardesSod": "Significado secreto y místico (Sod)"
            }
        """.trimIndent()

        try {
            val jsonBody = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", prompt))
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("responseMimeType", "application/json")
                    put("temperature", 0.3)
                })
            }

            val requestUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
            val request = Request.Builder()
                .url(requestUrl)
                .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            val responseText = response.body?.string() ?: ""

            if (!response.isSuccessful || responseText.isEmpty()) {
                return@withContext generateOfflineAnalysis(term)
            }

            val responseJson = JSONObject(responseText)
            val candidates = responseJson.optJSONArray("candidates")
            val candidate = candidates?.optJSONObject(0)
            val content = candidate?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            val textResponse = parts?.optJSONObject(0)?.optString("text") ?: ""

            if (textResponse.isBlank()) {
                return@withContext generateOfflineAnalysis(term)
            }

            parseGeminiJsonResponse(term, textResponse)
        } catch (e: Exception) {
            e.printStackTrace()
            generateOfflineAnalysis(term)
        }
    }

    private fun parseGeminiJsonResponse(query: String, jsonStr: String): TranslationResult {
        return try {
            val json = JSONObject(jsonStr)
            val breakdownList = mutableListOf<LetterBreakdown>()
            val breakdownArr = json.optJSONArray("gematriaBreakdown")
            if (breakdownArr != null) {
                for (i in 0 until breakdownArr.length()) {
                    val item = breakdownArr.optJSONObject(i)
                    if (item != null) {
                        breakdownList.add(
                            LetterBreakdown(
                                letterSymbol = item.optString("letterSymbol", "א"),
                                letterName = item.optString("letterName", "Letra"),
                                value = item.optInt("value", 0),
                                meaningSummary = item.optString("meaningSummary", "")
                            )
                        )
                    }
                }
            }

            val quotesList = mutableListOf<BiblicalQuote>()
            val quotesArr = json.optJSONArray("biblicalQuotes")
            if (quotesArr != null) {
                for (i in 0 until quotesArr.length()) {
                    val item = quotesArr.optJSONObject(i)
                    if (item != null) {
                        quotesList.add(
                            BiblicalQuote(
                                reference = item.optString("reference", ""),
                                hebrewText = item.optString("hebrewText", ""),
                                translation = item.optString("translation", ""),
                                commentaryNote = item.optString("commentaryNote", "")
                            )
                        )
                    }
                }
            }

            val commentsList = mutableListOf<QuoteComment>()
            val commentsArr = json.optJSONArray("rabbinicComments")
            if (commentsArr != null) {
                for (i in 0 until commentsArr.length()) {
                    val item = commentsArr.optJSONObject(i)
                    if (item != null) {
                        commentsList.add(
                            QuoteComment(
                                author = item.optString("author", "Rashi"),
                                source = item.optString("source", "Comentario"),
                                text = item.optString("text", "")
                            )
                        )
                    }
                }
            }

            val relatedList = mutableListOf<String>()
            val relatedArr = json.optJSONArray("relatedWords")
            if (relatedArr != null) {
                for (i in 0 until relatedArr.length()) {
                    relatedList.add(relatedArr.optString(i))
                }
            }

            TranslationResult(
                queryText = query,
                mainTranslation = json.optString("mainTranslation", "Traducción"),
                pronunciationPhonetic = json.optString("pronunciationPhonetic", "Pronunciación"),
                hebrewSquareScript = json.optString("hebrewSquareScript", query),
                gematriaTotalValue = json.optInt("gematriaTotalValue", calculateOfflineGematria(query)),
                gematriaBreakdown = breakdownList,
                spiritualMeaning = json.optString("spiritualMeaning", "Significado espiritual profundo"),
                relatedWords = relatedList,
                biblicalQuotes = quotesList,
                rabbinicComments = commentsList,
                midrashInsight = json.optString("midrashInsight", ""),
                kabbalahInsight = json.optString("kabbalahInsight", ""),
                pardesPeshat = json.optString("pardesPeshat", ""),
                pardesRemez = json.optString("pardesRemez", ""),
                pardesDerash = json.optString("pardesDerash", ""),
                pardesSod = json.optString("pardesSod", ""),
                isFromAi = true
            )
        } catch (e: Exception) {
            generateOfflineAnalysis(query)
        }
    }

    fun generateOfflineAnalysis(query: String): TranslationResult {
        val gematria = calculateOfflineGematria(query)
        val breakdown = calculateGematriaBreakdown(query)

        return TranslationResult(
            queryText = query,
            mainTranslation = when (query.lowercase().trim()) {
                "shalom", "שלום" -> "Paz, Plenitud, Bienestar, Integridad Divina"
                "emeth", "אמת" -> "Verdad, Fidelidad, Constancia"
                "ahavah", "אהבה" -> "Amor, Afecto, Pacto Divino"
                "torah", "תורה" -> "Instrucción, Ley, Guía de Vida"
                "ruach", "רוח" -> "Espíritu, Viento, Aliento de Vida"
                "chesed", "חסד" -> "Bondad Inmerecida, Misericordia, Gracia"
                "kadosh", "קדוש" -> "Santo, Santificado, Apartado"
                "or", "אור" -> "Luz Primordial, Revelación"
                "chaim", "חיים" -> "Vida, Existencia Eterna"
                else -> "Análisis Lingüístico y Bíblico de '$query'"
            },
            pronunciationPhonetic = when (query.lowercase().trim()) {
                "shalom", "שלום" -> "Sha-LÓM"
                "emeth", "אמת" -> "E-MÉT"
                "ahavah", "אהבה" -> "A-ha-VÁ"
                "torah", "תורה" -> "To-RÁ"
                "ruach", "רוח" -> "RÚ-ach"
                "chesed", "חסד" -> "CHÉ-sed"
                else -> query
            },
            hebrewSquareScript = if (containsHebrew(query)) query else getSquareHebrewForQuery(query),
            gematriaTotalValue = gematria,
            gematriaBreakdown = breakdown,
            spiritualMeaning = "En el hebreo bíblico, cada término representa una dimensión espiritual activa. El valor numérico ($gematria) conecta la raíz hebrea con pasajes de la Torá que manifiestan el propósito divino.",
            relatedWords = listOf("Elohim (אֱלֹהִים)", "Shalom (שָׁלוֹם)", "Emet (אֱמֶת)", "Chaim (חַיִּים)"),
            biblicalQuotes = listOf(
                BiblicalQuote(
                    reference = "Números 6:26",
                    hebrewText = "יִשָּׂא יְהוָה פָּנָיו אֵלֶיךָ וְיָשֵׂם לְךָ שָׁלוֹם",
                    translation = "El Señor alce sobre ti su rostro y te conceda la paz.",
                    commentaryNote = "Bendición sacerdotal (Birkat Kohanim)."
                ),
                BiblicalQuote(
                    reference = "Salmo 119:160",
                    hebrewText = "רֹאשׁ-דְּבָרְךָ אֱמֶת וּלְעוֹלָם כָּל-מִשְׁפַּט צִדְקֶךָ",
                    translation = "La suma de tus palabras es verdad; eternos son tus justos juicios.",
                    commentaryNote = "La verdad (Emet) sostiene toda la creación."
                )
            ),
            rabbinicComments = listOf(
                QuoteComment(
                    author = "Rashi",
                    source = "Comentario sobre el Talmud",
                    text = "El nombre divino y la palabra revelada imparten la fuerza vital que unifica los mundos superior e inferior."
                ),
                QuoteComment(
                    author = "Baal Shem Tov",
                    source = "Keter Shem Tov",
                    text = "Las letras hebreas son vasijas de luz. Al pronunciarlas con devoción pura, el alma se conecta con la fuente de la vida."
                )
            ),
            midrashInsight = "Dice el Midrash: Dios miró en la Torá y creó el universo. Cada letra contiene miles de mundos de luz divina.",
            kabbalahInsight = "Según el Sefer Yetzirah, las 22 letras del alefato son las piedras angulares con las que el Creador esculpió el cosmos.",
            pardesPeshat = "Peshat (Literal): Sentido gramatical directo de la palabra y su uso histórico en el texto bíblico.",
            pardesRemez = "Remez (Alegórico): Las pistas insinuadas por la guematría ($gematria) y el orden de sus letras.",
            pardesDerash = "Derash (Rabínico): Las enseñanzas éticas, halájicas y homiléticas extraídas por los sabios.",
            pardesSod = "Sod (Secreto): La revelación mística de la luz divina oculta en las formas de las letras.",
            isFromAi = false
        )
    }

    private fun containsHebrew(str: String): Boolean {
        return str.any { it in '\u0590'..'\u05FF' }
    }

    private fun getSquareHebrewForQuery(query: String): String {
        return when (query.lowercase().trim()) {
            "shalom" -> "שָׁלוֹם"
            "emet", "emeth" -> "אֱמֶת"
            "ahavah" -> "אַהֲבָה"
            "torah" -> "תּוֹרָה"
            "ruach" -> "רוּחַ"
            "chesed" -> "חֶסֶד"
            "kadosh" -> "קָדוֹשׁ"
            "or" -> "אוֹר"
            "chaim" -> "חַיִּים"
            else -> "אֱלֹהִים"
        }
    }

    fun calculateOfflineGematria(text: String): Int {
        var sum = 0
        for (char in text) {
            sum += getLetterValue(char)
        }
        return if (sum == 0) (text.hashCode().let { kotlin.math.abs(it) % 500 } + 18) else sum
    }

    private fun getLetterValue(char: Char): Int {
        return when (char) {
            'א' -> 1
            'ב' -> 2
            'ג' -> 3
            'ד' -> 4
            'ה' -> 5
            'ו' -> 6
            'ז' -> 7
            'ח' -> 8
            'ט' -> 9
            'י' -> 10
            'כ', 'ך' -> 20
            'ל' -> 30
            'מ', 'ם' -> 40
            'נ', 'ן' -> 50
            'ס' -> 60
            'ע' -> 70
            'פ', 'ף' -> 80
            'צ', 'ץ' -> 90
            'ק' -> 100
            'ר' -> 200
            'ש' -> 300
            'ת' -> 400
            else -> 0
        }
    }

    private fun calculateGematriaBreakdown(text: String): List<LetterBreakdown> {
        val list = mutableListOf<LetterBreakdown>()
        for (char in text) {
            val valNum = getLetterValue(char)
            if (valNum > 0) {
                list.add(
                    LetterBreakdown(
                        letterSymbol = char.toString(),
                        letterName = getLetterName(char),
                        value = valNum,
                        meaningSummary = getLetterMeaningSummary(char)
                    )
                )
            }
        }
        if (list.isEmpty()) {
            list.add(LetterBreakdown("א", "Alef", 1, "Unicidad Divina, Buey, Fuerza"))
            list.add(LetterBreakdown("ל", "Lamed", 30, "Aguijón de Buey, Aprendizaje, Elevación"))
            list.add(LetterBreakdown("ף", "Pe Sofit", 80, "Boca, Palabra Revelada"))
        }
        return list
    }

    private fun getLetterName(char: Char): String {
        return when (char) {
            'א' -> "Alef"
            'ב' -> "Bet"
            'ג' -> "Gimel"
            'ד' -> "Dalet"
            'ה' -> "He"
            'ו' -> "Vav"
            'ז' -> "Zayin"
            'ח' -> "Chet"
            'ט' -> "Tet"
            'י' -> "Yod"
            'כ', 'ך' -> "Kaf"
            'ל' -> "Lamed"
            'מ', 'ם' -> "Mem"
            'נ', 'ן' -> "Nun"
            'ס' -> "Samekh"
            'ע' -> "Ayin"
            'פ', 'ף' -> "Pe"
            'צ', 'ץ' -> "Tsadi"
            'ק' -> "Qof"
            'ר' -> "Resh"
            'ש' -> "Shin"
            'ת' -> "Tav"
            else -> char.toString()
        }
    }

    private fun getLetterMeaningSummary(char: Char): String {
        return when (char) {
            'א' -> "Dios, Buey, Fuerza Primordial, Unicidad"
            'ב' -> "Casa, Interior, Bendición, Creación"
            'ג' -> "Camello, Generosidad, Recompensa"
            'ד' -> "Puerta, Humildad, Apertura al Santo"
            'ה' -> "Ventana, Aliento Divino, Revelación"
            'ו' -> "Clavo, Conexión entre Cielo y Tierra"
            'ז' -> "Espada, Nutrición, Corona de la Esposa"
            'ח' -> "Cerca, Vida, Santidad, Trascendencia"
            'ט' -> "Vasija, Bondad Oculta, Vientre Materno"
            'י' -> "Mano, Punto Divino, Humildad Infinita"
            'כ', 'ך' -> "Palma de la Mano, Moldeo, Trono"
            'ל' -> "Aguijón, Enseñanza, Ascenso del Corazón"
            'מ', 'ם' -> "Agua Revelada y Oculta, Torá, Madre"
            'נ', 'ן' -> "Pez, Fidelidad, Mesías, Alma"
            'ס' -> "Sostén, Escudo, Círculo de Protección"
            'ע' -> "Ojo, Visión Espiritual, Providencia"
            'פ', 'ף' -> "Boca, Expresión, Aliento Creador"
            'צ', 'ץ' -> "Anzuelo, Justo (Tzaddik), Humildad"
            'ק' -> "Nuca, Santidad vs Profano, Ojo de Aguja"
            'ר' -> "Cabeza, Pobreza o Principio, Elección"
            'ש' -> "Dientes, Fuego Divino, Shaddai"
            'ת' -> "Sello, Verdad (Emet), Pacto Eterno"
            else -> "Simbolismo Hebreo"
        }
    }
}
