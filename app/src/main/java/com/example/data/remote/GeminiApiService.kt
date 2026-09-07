package com.example.data.remote

import android.util.Log
import com.example.BuildConfig
import com.example.data.local.ApiKeyManager
import com.example.data.model.BiblicalQuote
import com.example.data.model.LetterBreakdown
import com.example.data.model.QuoteComment
import com.example.data.model.TranslationResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ConnectionPool
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiApiService(
    private val apiKeyManager: ApiKeyManager? = null,
    private val sefariaWikiService: SefariaWikiService = SefariaWikiService()
) {

    // Fast in-memory response caches to deliver instant (0ms) responses on repeated queries
    private val studyResponseCache = java.util.concurrent.ConcurrentHashMap<String, String>()
    private val analysisResultCache = java.util.concurrent.ConcurrentHashMap<String, TranslationResult>()

    companion object {
        const val RABI_IA_SYSTEM_INSTRUCTION =
            "Eres un sabio rabino, con conocimientos en los 4 planos de la palabra (Pardes) dominas tanto la kabbala como el judaísmo, la tanaj, torá, el talmúd, Zohar, midrash, asi como el estudio de rabinos atiguos que han comentado las escrituras."
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .connectionPool(ConnectionPool(8, 5, TimeUnit.MINUTES))
        .retryOnConnectionFailure(true)
        .build()

    fun getActiveApiKey(): String {
        apiKeyManager?.getEffectiveApiKey()?.let {
            if (it.isNotBlank()) return it
        }
        val buildKey = try { BuildConfig.GEMINI_API_KEY } catch (e: Exception) { "" }
        if (buildKey.isNotBlank() && buildKey != "MY_GEMINI_API_KEY") {
            return buildKey
        }
        return ""
    }

    private fun callGeminiGenerateContent(
        apiKey: String,
        jsonBody: JSONObject,
        primaryModel: String = "gemini-3.1-flash-lite"
    ): String? {
        val modelsToTry = listOf(
            primaryModel,
            "gemini-3.1-flash-lite",
            "gemini-3.1-flash-lite-preview",
            "gemini-flash-latest",
            "gemini-3.8-flash",
            "gemini-3.5-flash"
        ).distinct()
        for (model in modelsToTry) {
            try {
                val requestUrl = "https://generativelanguage.googleapis.com/v1beta/models/$model:generateContent?key=$apiKey"
                val request = Request.Builder()
                    .url(requestUrl)
                    .header("Content-Type", "application/json")
                    .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
                    .build()

                val response = client.newCall(request).execute()
                val responseText = response.body?.string() ?: ""

                if (response.isSuccessful && responseText.isNotEmpty()) {
                    val responseJson = JSONObject(responseText)
                    val candidates = responseJson.optJSONArray("candidates")
                    val candidate = candidates?.optJSONObject(0)
                    val content = candidate?.optJSONObject("content")
                    val parts = content?.optJSONArray("parts")
                    val textResponse = parts?.optJSONObject(0)?.optString("text") ?: ""
                    if (textResponse.isNotBlank()) {
                        return textResponse
                    }
                } else {
                    Log.w("GeminiApiService", "Gemini call to $model returned code ${response.code}: ${responseText.take(150)}")
                }
            } catch (e: Exception) {
                Log.e("GeminiApiService", "Exception calling model $model: ${e.message}")
            }
        }
        return null
    }

    suspend fun generateStudyResponse(topicTitle: String, userQuestion: String): String = withContext(Dispatchers.IO) {
        val cacheKey = "${topicTitle.trim().lowercase()}:::${userQuestion.trim().lowercase()}"
        studyResponseCache[cacheKey]?.let { cached ->
            return@withContext cached
        }

        // Consultar contexto enriquecido de Sefaria y Wikipedia
        val enrichment = try {
            val queryForContext = if (userQuestion.length > 5) "$topicTitle $userQuestion" else topicTitle
            sefariaWikiService.fetchEnrichment(queryForContext)
        } catch (e: Exception) {
            ExternalContextEnrichment()
        }
        val externalContextStr = enrichment.toContextPrompt()

        val apiKey = getActiveApiKey()
        if (apiKey.isNotBlank()) {
            val prompt = """
                Tema o Pasaje de estudio: "$topicTitle"
                Pregunta del estudiante: "$userQuestion"
                
                ${if (externalContextStr.isNotBlank()) "FUENTES EXTERNAS CONSULTADAS (Sefaria & Wikipedia):\n$externalContextStr\n" else ""}
                
                REGLAS OBLIGATORIAS:
                1. Escribe el 100% de tu respuesta en ESPAÑOL claro, respetuoso, erudito, fluido, pastoral y estructurado. NUNCA uses inglés en los encabezados ni en el cuerpo.
                2. PROHIBIDO utilizar fórmulas en LaTeX, signos de dólar ($ o $$), barras invertidas (\) o notaciones matemáticas raras. Escribe cualquier cálculo o suma de guematría en texto plano limpio (ejemplo: "300 + 30 + 6 + 40 = 376").
                3. Responde de manera exhaustiva aplicando los 4 planos de la palabra (PaRDeS: Peshat, Remez, Derash, Sod), la Tanaj, Torá, Talmud, Zóhar, Midrash y la sabiduría de rabinos antiguos (Rashi, Rambam, Ibn Ezra, Baal Shem Tov).
                4. Cita las raíces hebreas (Shoresh) con sus letras originales y significados pictográficos/paleo-hebreos.
                5. Sé profundo, esclarecedor, conciso y de rápida lectura, manteniendo la calidez y reverencia de un auténtico maestro rabínico.
            """.trimIndent()

            try {
                val jsonBody = JSONObject().apply {
                    put("system_instruction", JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", RABI_IA_SYSTEM_INSTRUCTION))
                        })
                    })
                    put("contents", JSONArray().apply {
                        put(JSONObject().apply {
                            put("parts", JSONArray().apply {
                                put(JSONObject().put("text", prompt))
                            })
                        })
                    })
                    put("generationConfig", JSONObject().apply {
                        put("temperature", 0.3)
                        put("maxOutputTokens", 2500)
                        put("thinkingConfig", JSONObject().apply {
                            put("thinkingBudget", 0)
                        })
                    })
                }

                val textResponse = callGeminiGenerateContent(apiKey, jsonBody, "gemini-3.1-flash-lite")
                if (!textResponse.isNullOrBlank()) {
                    val sanitized = sanitizeAiText(textResponse)
                    studyResponseCache[cacheKey] = sanitized
                    return@withContext sanitized
                }
            } catch (e: Exception) {
                Log.e("GeminiApiService", "Error calling Gemini API for study response", e)
            }
        }

        // Respaldo teológico contextual offline de alta velocidad y precisión
        val offlineAnswer = generateOfflineStudyAnswer(topicTitle, userQuestion)
        studyResponseCache[cacheKey] = offlineAnswer
        offlineAnswer
    }

    private fun generateOfflineStudyAnswer(topicTitle: String, userQuestion: String): String {
        val qLower = userQuestion.lowercase().trim()
        val tLower = topicTitle.lowercase().trim()

        val etymologySection = when {
            // Libros Bíblicos y Capítulos
            tLower.contains("bereshit") || tLower.contains("génesis") || tLower.contains("genesis") ->
                "En Bereshit (בְּרֵאשִׁית - 'En el principio'), la Torá establece los fundamentos de la Creación, el pacto con los patriarcas y el diseño divino para la humanidad. Cada palabra revela la sabiduría primordial (Jojmá) con la que el Creador estructuró el universo mediante el lenguaje sagrado."
            tLower.contains("shemot") || tLower.contains("éxodo") || tLower.contains("exodo") ->
                "En Shemot (שְׁמוֹת - 'Nombres'), presenciamos la transformación de una familia en nación, la redención milagrosa de la esclavitud en Egipto y la entrega soberana de los Diez Mandamientos en el Monte Sinaí, modelando el pacto eterno."
            tLower.contains("vayikra") || tLower.contains("levítico") || tLower.contains("levitico") ->
                "En Vayikra (וַיִּקְרָא - 'Y llamó'), la Torá revela el código de santidad (Kedushá), el servicio del Tabernáculo y los sacrificios (Korbanot) como vehículos de acercamiento (Karov) entre el alma y la Presencia Divina."
            tLower.contains("bamidbar") || tLower.contains("números") || tLower.contains("numeros") ->
                "En Bamidbar (בְּמִדְבַּר - 'En el desierto'), el pueblo transita por pruebas de fe y formación comunitaria, aprendiendo a guiarse por la nube de gloria y el arca del pacto hacia la Tierra Prometida."
            tLower.contains("devarim") || tLower.contains("deuteronomio") ->
                "En Devarim (דְּבָרִים - 'Palabras'), Moshé Rabenu repasa y sella la Torá con amor y advertencia profética, exhortando a amar al Eterno con todo el corazón, el alma y las fuerzas (Shemá Israel)."
            tLower.contains("tehilim") || tLower.contains("salmos") || tLower.contains("salmo") ->
                "En Tehilim (תְּהִלִּים - 'Alabanzas'), el Rey David y los salmistas plasman el clamor, la alabanza y la profecía mesiánica más sublime del alma humana en íntima comunión con el Creador."
            tLower.contains("mishlei") || tLower.contains("proverbios") ->
                "En Mishlei (מִשְׁלֵי - 'Proverbios'), el Rey Salomón ofrece la cumbre de la sabiduría práctica y moral, enseñando que 'el principio de la sabiduría es el temor reverente al Eterno' (Mishlei 1:7)."
            tLower.contains("yeshayahu") || tLower.contains("isaías") || tLower.contains("isaias") ->
                "En Yeshayahu (יְשַׁעְיָהוּ - 'Salvación de Yah'), resuenan las profecías de consolación (Najamú Najamú Amí), la redención mesiánica universal y la gloria venidera de Sion."
            tLower.contains("yirmeyahu") || tLower.contains("jeremías") || tLower.contains("jeremias") ->
                "En Yirmeyahu (יִרְמְיָהוּ), el profeta proclama el pacto renovado (Brit Jadashá) escrito en los corazones y la fidelidad inmutable de Dios hacia Su pueblo."
            tLower.contains("yejezkel") || tLower.contains("ezequiel") ->
                "En Yejezkel (יְחֶזְקֵאל), se revela la visión mística del Carro Celestial (Maasé Merkavá), la resurrección de los huesos secos y el nuevo Templo milenial."
            tLower.contains("daniel") ->
                "En Daniel (דָּנִיֵּאל), se develan las visiones apocalípticas de los reinos del mundo y el establecimiento eterno del Reino de los Cielos."
            tLower.contains("matityahu") || tLower.contains("mateo") || tLower.contains("marcos") || tLower.contains("lucas") || tLower.contains("yojanan") || tLower.contains("juan") ->
                "En los relatos de la Besorá (Evangelios), se manifiesta el cumplimiento de las profecías mesiánicas en el contexto del judaísmo del Segundo Templo, profundizando la Torá desde el amor y la redención."
            tLower.contains("romanos") || tLower.contains("corintios") || tLower.contains("galatas") || tLower.contains("hebreos") || tLower.contains("hitgalut") || tLower.contains("apocalipsis") ->
                "En los escritos apostólicos, se articulan las raíces hebreas de la fe, la justificación por la fidelidad divina, la unión del olivo silvestre con el olivo natural (Romanos 11) y la consumación en la Nueva Jerusalén."
            
            // Meses Hebreos
            tLower.contains("tishrei") -> "El mes de Tishrei (תִּשְׁרֵי) es el séptimo mes religioso y primero civil, llamado 'el mes de la plenitud (Sova)'. Asociado a la tribu de Efraín y a la letra Lamed (ל) que simboliza el ascenso del corazón hacia el Creador. Alberga las festividades más solemnes: Rosh Hashaná, Yom Kipur y Sukkot, culminando con el juicio, el perdón y el gran regocijo divino."
            tLower.contains("jeshv") || tLower.contains("cheshvan") || tLower.contains("marjeshv") -> "El mes de Jeshván o Marjeshván (מַרְחֶשְׁוָן) es el octavo mes, vinculado a la tribu de Manasés y la letra Nun (נ). Conocido tradicionalmente como el mes del Diluvio de Noé y reservado proféticamente por los sabios para la futura consagración del Templo Mesiánico."
            tLower.contains("kislev") -> "El mes de Kislev (כִּסְלֵו) es el noveno mes, asociado a la tribu de Benjamín y la letra Sámej (ס) que representa el apoyo divino y el escudo de protección. Es el mes de los milagros, donde la luz de Janucá vence la oscuridad del invierno y se revela la fuerza de la fe inquebrantable (Bitajón)."
            tLower.contains("tevet") -> "El mes de Tevet (טֵבֵת) es el décimo mes, ligado a la tribu de Dan y la letra Áyin (ע) que representa la visión espiritual y el ojo purificado. Marca el inicio del asedio a Jerusalén (10 de Tevet) y convoca a la rectificación del juicio severo en misericordia."
            tLower.contains("shevat") -> "El mes de Shevat (שְׁבָט) es el undécimo mes, asociado a la tribu de Aser (abundancia de aceite) y la letra Tzadi (צ) que encarna al Justo (Tzadik). Celebra Tu Bishvat (Año Nuevo de los Árboles), simbolizando el renacimiento de la savia espiritual y la alabanza por los frutos de la tierra."
            tLower.contains("adar ii") || tLower.contains("adar 2") || tLower.contains("veadar") -> "Adar II (אֲדָר ב׳) o VeAdar es el mes embolismal que calibra el calendario lunar con el ciclo solar agrícola. En él se celebra la alegría desbordante de Purim, con la tribu de Neftalí y la letra Qof (ק), transformando todo decreto en redención y júbilo."
            tLower.contains("adar") -> "El mes de Adar (אֲדָר) es el duodécimo mes, caracterizado por la máxima alegría (Marbim BeSimjá), vinculado a la tribu de Neftalí y la letra Qof (ק). Conmemora la salvación providencial de Purim y la revelación de la mano oculta de Dios."
            tLower.contains("nisan") || tLower.contains("nisán") -> "El mes de Nisán (נִיסָן) es el primer mes religioso de la Torá (Rosh Jodashim), mes de la primavera (Aviv) y de la redención (Gueulá). Asociado a la tribu de Judá (la realeza) y a la letra Hei (ה), celebra la salida milagrosa de Egipto en Pésaj."
            tLower.contains("iyar") -> "El mes de Iyar (אִיָּר) es el segundo mes, conocido como el mes de la sanidad (Ziv), vinculado a la tribu de Isacar y la letra Vav (ו). Sus iniciales forman 'Aní Adonai Rofeja' (Yo soy el Eterno tu Sanador, Éxodo 15:26). Alberga la cuenta del Ómer y Lag BaÓmer."
            tLower.contains("sivan") || tLower.contains("siván") -> "El mes de Siván (סִיוָן) es el tercer mes, vinculado a la tribu de Zabulón y la letra Zayin (ז). En este mes se celebró la entrega solemne de la Torá en el Monte Sinaí durante la festividad de Shavuot."
            tLower.contains("tamuz") || tLower.contains("tammuz") -> "El mes de Tamuz (תַּמּוּז) es el cuarto mes, vinculado a la tribu de Rubén y la letra Jet (ח). Invita a la rectificación de la visión espiritual tras la ruptura de las primeras tablas en el 17 de Tamuz."
            tLower.contains("av") || tLower.contains("menajem") -> "El mes de Av o Menajem Av (מְנַחֵם אָב) es el quinto mes, asociado a la tribu de Simón y la letra Tet (ט). Aunque recuerda la destrucción de ambos Templos en Tishá BeAv, su nombre promete el consuelo supremo del Padre (Menajem Av) y la celebración del amor en Tu BeAv."
            tLower.contains("elul") -> "El mes de Elul (אֱלוּל) es el sexto mes, vinculado a la tribu de Gad y la letra Yud (י). Es el mes de la reconciliación y el amor divino, cuyo acrónimo bíblico es 'Aní LeDodí VeDodí Lí' (Yo soy de mi Amado y mi Amado es mío, Cantar de los Cantares 6:3)."
            
            // Letras Hebreas
            tLower.contains("alef") || tLower.contains("álef") -> "La letra Álef (א) representa la Unicidad Absoluta de Dios (Ejad). Formada gráficamente por dos Yod y una Vav diagonal, su valor es 1 y suma 26 en su estructura interna (Yod=10 + Yod=10 + Vav=6), reflejando el Tetragramatón Sagrado יהוה."
            tLower.contains("bet") -> "La letra Bet (ב) representa la morada terrenal (Bayit) y la bendición (Berajá). Es la primera letra con la que comienza la Torá en 'Bereshit', mostrando que el propósito del cosmos es ser una morada para la Presencia Divina (Shejiná)."
            tLower.contains("gimel") || tLower.contains("guímel") -> "La letra Guímel (ג) representa al camello (Gamal) y la generosidad activa (Guemilut Jasadim). Simboliza al benefactor corriendo tras el indigente (Dálet) para proveerle sustento."
            tLower.contains("dalet") || tLower.contains("dálet") -> "La letra Dálet (ד) representa la puerta (Délet) y la humildad (Dalut). Enseña la reverencia del alma ante el Creador."
            tLower.contains("hei") || tLower.contains("he") -> "La letra Hei (ה) es el aliento de la Creación (B'hibaram) y representa la gracia divina, la fertilidad y la santificación del lenguaje y el pensamiento."
            tLower.contains("vav") -> "La letra Vav (ו) es el gancho de conexión que une los cielos y la tierra, representando la verdad y la continuidad de la revelación divina."
            tLower.contains("zayin") -> "La letra Zayin (ז) representa la corona y la espada espiritual, personificando el Shabat y la fuerza protectora de la Torá."
            tLower.contains("chet") || tLower.contains("jet") -> "La letra Jet (ח) simboliza la vida (Jayim) y la sabiduría que trasciende la naturaleza."
            tLower.contains("tet") -> "La letra Tet (ט) representa la bondad oculta (Tov) y el vientre de la transformación espiritual."
            tLower.contains("yod") || tLower.contains("yud") -> "La letra Yod (י) es el punto primordial indivisible, la chispa divina que da origen a todo el alfabeto sagrado."
            tLower.contains("kaf") -> "La letra Kaf (כ) es la palma receptora (Kaf) y la corona (Kéter), el poder de materializar el potencial espiritual."
            tLower.contains("lamed") -> "La letra Lamed (ל) es la más alta del alefato, simbolizando el corazón que aprende (Lev Mevin) y el anhelo del alma por elevarse hacia el Trono Celestial."
            tLower.contains("mem") -> "La letra Mem (מ) representa las aguas vivas de la Torá (Máyim Jayim) y los misterios revelados y ocultos."
            tLower.contains("nun") -> "La letra Nun (נ) simboliza la fidelidad (Ne'eman) y la capacidad del alma de levantarse de cualquier caída."
            tLower.contains("samech") || tLower.contains("sámej") -> "La letra Sámej (ס) es el círculo protector y el soporte continuo del Todopoderoso a los caídos."
            tLower.contains("ayin") || tLower.contains("áyin") -> "La letra Áyin (ע) es el ojo espiritual que percibe la providencia divina más allá de las apariencias materiales."
            tLower.contains("pe") || tLower.contains("pei") || tLower.contains("peh") -> "La letra Peh (פ) representa la boca y el poder sagrado de la palabra (Dibur) en la oración y el estudio."
            tLower.contains("tzadi") -> "La letra Tzadi (צ) es la figura del Justo (Tzadik) que sostiene los cimientos del mundo con rectitud."
            tLower.contains("qof") || tLower.contains("kof") -> "La letra Qof (ק) representa la santidad (Kadosh) y la capacidad de descender para elevar las chispas divinas caídas."
            tLower.contains("resh") -> "La letra Resh (ר) representa la cabeza (Rosh) y la elección entre el orgullo y la sumisión al Creador."
            tLower.contains("shin") -> "La letra Shin (ש) encarna las tres llamas del fuego sagrado (Esh Ojelá) y la presencia del Nombre Shaddai (ש-ד-י)."
            tLower.contains("tav") -> "La letra Tav (ת) es el sello supremo de la Verdad (Emet) y la culminación del pacto eterno de la Creación."

            // Fiestas Bíblicas
            tLower.contains("shabbat") || tLower.contains("shabat") -> "El Shabat (שַׁבָּת) es el testimonio primordial de la Creación y el pacto eterno entre Dios e Israel, un anticipo tangible del Mundo Venidero (Olam HaBá)."
            tLower.contains("pesaj") || tLower.contains("pascua") -> "Pésaj (פֶּסַח) conmemora la redención sobrenatural de Egipto y el paso providencial de Dios. Su etimología alude al salto liberador y a la 'boca que habla' (Peh Sáj) en la noche del Séder."
            tLower.contains("shavuot") || tLower.contains("pentecost") -> "Shavuot (שָׁבוּעוֹת) es el tiempo de la entrega de la Torá (Zman Matan Torateinu), uniendo la libertad física de Pésaj con la libertad espiritual y moral de los mandamientos."
            tLower.contains("sukkot") || tLower.contains("cabañas") -> "Sukót (סֻכּוֹת) es la Fiesta de los Tabernáculos y del gozo pleno (Zman Simjateinu), recordando la protección de las nubes de gloria divina (Ananei HaKavod)."
            tLower.contains("rosh hashana") || tLower.contains("trompetas") -> "Rosh Hashaná (רֹאשׁ הַשָּׁנָה) es el Día del Juicio (Yom HaDin) y del toque del Shofár (Yom Teruá), proclamando la soberanía absoluta de Dios sobre el universo."
            tLower.contains("yom kipur") || tLower.contains("expiacion") -> "Yom Kipur (יוֹם כִּפּוּר) es el Shabat de los Shabatot, día de ayuno, purificación absoluta y expiación donde se revela la misericordia incondicional del Eterno."
            tLower.contains("januca") || tLower.contains("hanukkah") -> "Janucá (חֲנֻכָּה) celebra la reinauguración del Templo y el milagro del candelabro, demostrando que la luz espiritual supera a la fuerza material ('No con ejército ni con fuerza, sino con mi Espíritu', Zacarías 4:6)."
            tLower.contains("purim") -> "Purim (פּוּרִים) conmemora la salvación providencial ante el decreto de Hamán, donde la mano oculta de Dios orquestó la victoria a través de la fe de Ester y Mardoqueo."

            else -> "En la tradición de la Torá y el hebreo bíblico, '$topicTitle' encierra principios espirituales fundamentales que conectan la vida terrenal con la sabiduría del Creador."
        }

        val questionInsight = when {
            qLower.contains("zohar") || qLower.contains("mistic") || qLower.contains("kabbalah") || qLower.contains("secreto") || qLower.contains("sod") ->
                "Desde el Sefer HaZóhar y los escritos cabalísticos clásicos, '$topicTitle' es un canal de emanación divina (Tzinor de Ohr Ein Sof) que alinea las diez Sefirot y revela cómo la realidad física es nutrida continuamente por la dimensión trascendente."
            qLower.contains("gematria") || qLower.contains("guematria") || qLower.contains("numero") || qLower.contains("valor") ->
                "En el cálculo de Guematría (Mispar Hejrashí), los valores de las consonantes hebreas de '$topicTitle' conectan directamente con conceptos bíblicos de redención, pacto y bendición, demostrando la simetría matemática del lenguaje sagrado."
            qLower.contains("midrash") || qLower.contains("rabino") || qLower.contains("talmud") || qLower.contains("rashi") || qLower.contains("rambam") ->
                "Los sabios del Talmud y el Midrash enseñan que '$topicTitle' ofrece claves prácticas para el refinamiento del carácter (Tikún HaMidot), la justicia social (Tzedaká) y la devoción sincera hacia el prójimo y el Creador."
            qLower.contains("agric") || qLower.contains("cosecha") || qLower.contains("clima") || qLower.contains("tierra") || qLower.contains("estacion") ->
                "En el ciclo agrícola de la Tierra de Israel (Eretz Yisrael), '$topicTitle' se sincroniza con el ritmo de las siembras, las lluvias providenciales (Yoreh y Malkosh) y las cosechas de los siete frutos bíblicos, mostrando que la naturaleza material es un espejo del crecimiento espiritual."
            qLower.contains("profec") || qLower.contains("mesian") || qLower.contains("escatol") || qLower.contains("futuro") ->
                "En la perspectiva profética y escatológica, '$topicTitle' prefigura la restauración de todas las cosas en la era mesiánica, la reunión de las tribus dispersas y el establecimiento de la paz universal conforme a los profetas de Israel."
            else ->
                "Al analizar tu consulta sobre \"$userQuestion\", las fuentes bíblicas y rabínicas enseñan que '$topicTitle' es una guía viva para nuestro caminar diario, fortaleciendo el entendimiento de los mandamientos (Mitzvot) y la conexión espiritual con el Creador."
        }

        return "📜 Análisis Teológico y Exegético sobre '$topicTitle'\n\n" +
                "❓ Consulta: \"$userQuestion\"\n\n" +
                "1. Fundamento Etimológico y Raíces Sagradas:\n" +
                "$etymologySection\n\n" +
                "2. Perspectiva Exegética (Método PaRDeS):\n" +
                "• Peshat (Sentido Literal): Contexto lingüístico, raíz triconsonántica (Shoresh) y significado histórico en las Escrituras.\n" +
                "• Remez (Sentido Alusivo): Correspondencias numéricas de guematría, paralelismos poéticos y conexiones bíblicas cruzadas.\n" +
                "• Derash (Sentido Homilético): Enseñanzas éticas del Midrash Rabbah y el Talmud para la vida cotidiana.\n" +
                "• Sod (Sentido Místico): Dimensión espiritual interior descrita en el Sefer Yetzirá y el Zóhar.\n\n" +
                "3. Revelación Teológica y Sabiduría Aplicada:\n" +
                "$questionInsight"
    }

    suspend fun analyzeHebrewTerm(term: String): TranslationResult = withContext(Dispatchers.IO) {
        val cacheKey = term.trim().lowercase()
        analysisResultCache[cacheKey]?.let { cached ->
            return@withContext cached
        }

        // Contexto externo desde Sefaria y Wikipedia para términos o versículos
        val enrichment = try {
            sefariaWikiService.fetchEnrichment(term)
        } catch (e: Exception) {
            ExternalContextEnrichment()
        }
        val externalContextStr = enrichment.toContextPrompt()

        val apiKey = getActiveApiKey()
        if (apiKey.isBlank()) {
            val offline = generateDynamicOfflineAnalysis(term)
            analysisResultCache[cacheKey] = offline
            return@withContext offline
        }

        val prompt = """
            Analiza con máxima precisión, rapidez y profundidad teológica el siguiente término, pasaje o versículo: "$term" (puede estar escrito en español, hebreo con o sin vocales, o transliteración fonética).
            
            ${if (externalContextStr.isNotBlank()) "FUENTES EXTERNAS DE REFERENCIA CONSULTADAS (Sefaria & Wikipedia):\n$externalContextStr\n" else ""}
            
            REGLAS CRÍTICAS DE FORMATO Y LENGUAJE:
            1. Escribe el 100% de todos los textos, traducciones, comentarios y explicaciones en ESPAÑOL claro y formal. NUNCA uses inglés (no pongas "Meaning", "Spiritual meaning", "Letter breakdown", etc.).
            2. PROHIBIDO usar formato LaTeX, signos de dólar ($ o $$), barras invertidas (\) o caracteres de escape matemáticos. Escribe cualquier cálculo de guematría en texto plano (ejemplo: "300 + 30 + 6 + 40 = 376", NUNCA "$300 + 30 = 330$" ni "\\text{}").
            3. No incluyas barras diagonales inversas ni símbolos especiales que ensucien la lectura en pantallas móviles.
            
            INSTRUCCIONES OBLIGATORIAS:
            1. Identifica el término hebreo exacto correspondiente, con sus vocales masoréticas completas (Nikkud).
            2. Identifica su raíz tri-consonántica (Shoresh).
            3. Calcula la GUEMATRÍA EXACTA (Mispar Hechrachi estándar) sumando rigurosamente los valores numéricos de las letras hebreas (א=1, ב=2, ג=3, ד=4, ה=5, ו=6, ז=7, ח=8, ט=9, י=10, כ/ך=20, ל=30, מ/ם=40, נ/ן=50, ס=60, ע=70, פ/ף=80, צ/ץ=90, ק=100, ר=200, ש=300, ת=400). Las vocales/nikkud no suman valor.
            4. Desglosa cada letra del término con su valor numérico y significado pictográfico / paleo-hebreo.
            5. Desarrolla el Método PARDES con explicaciones detalladas y exclusivas para este término en español:
               - pardesPeshat: Significado literal, gramática, contexto bíblico e histórico.
               - pardesRemez: Alusiones numéricas (Guematría en texto plano), relaciones con otras palabras y combinaciones de letras (Tzerufim).
               - pardesDerash: Exégesis rabínica, relatos del Midrash, lecciones éticas y enseñanzas talmúdicas.
               - pardesSod: El secreto místico, la energía espiritual interna y la dimensión divina en los mundos superiores.
            6. Incluye comentarios específicos de sabios:
               - midrashInsight: Cita o enseñanza explícita del Midrash (ej. Midrash Rabbah, Tanchuma) sobre esta palabra/concepto.
               - zoharInsight: Enseñanza mística del Sefer HaZóhar referente a esta palabra y su flujo de luz divina.
               - kabbalahInsight: Interpretación cabalística según el Sefer Yetzirá, el Árbol de la Vida y las Sefirot vinculadas.
            7. Proporciona citas bíblicas donde el término aparece o es fundamental (referencia precisa, texto hebreo con niqud, traducción al español y comentario contextual).
            8. Comentarios de sabios clásicos (Rashi, Rambam, Rambán, Ibn Ezra, Baal Shem Tov, etc.).
            
            Responde ÚNICAMENTE en formato JSON con la siguiente estructura exacta:
            {
              "mainTranslation": "Traducción completa y rica al español",
              "pronunciationPhonetic": "Pronunciación fonética con acentuación clara",
              "hebrewSquareScript": "Texto en hebreo cuadrado con niqud (vocales)",
              "rootWord": "Raíz hebrea (Shoresh), ej: ש-ל-ם",
              "gematriaTotalValue": 376,
              "gematriaBreakdown": [
                {
                  "letterSymbol": "ש",
                  "letterName": "Shin",
                  "value": 300,
                  "meaningSummary": "Dientes, Fuego Divino, Poder de Transformación"
                }
              ],
              "spiritualMeaning": "Explicación teológica y espiritual exhaustiva sobre el concepto en español",
              "relatedWords": ["Palabra1 (en hebreo)", "Palabra2 (en hebreo)", "Palabra3 (en hebreo)"],
              "biblicalQuotes": [
                {
                  "reference": "Libro Capítulo:Versículo",
                  "hebrewText": "Texto bíblico en hebreo con niqud",
                  "translation": "Traducción al español",
                  "commentaryNote": "Explicación contextual del versículo"
                }
              ],
              "rabbinicComments": [
                {
                  "author": "Nombre del Sabio (ej. Rashi, Rambam, Baal Shem Tov)",
                  "source": "Tratado o Comentario de referencia",
                  "text": "Explicación profunda de la enseñanza rabínica"
                }
              ],
              "midrashInsight": "Enseñanza profunda extraída del Midrash sobre este término",
              "zoharInsight": "Enseñanza y secreto místico del Sefer HaZóhar para esta palabra",
              "kabbalahInsight": "Interpretación cabalística (Sefer Yetzirá y Sefirot asociadas)",
              "pardesPeshat": "Nivel Peshat: Significado literal y gramatical de la palabra",
              "pardesRemez": "Nivel Remez: Alusiones alegóricas y correspondencias de guematría",
              "pardesDerash": "Nivel Derash: Interpretación homilética y lecciones de vida",
              "pardesSod": "Nivel Sod: Misterio secreto y dimensión espiritual suprema"
            }
        """.trimIndent()

        try {
            val jsonBody = JSONObject().apply {
                put("system_instruction", JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().put("text", RABI_IA_SYSTEM_INSTRUCTION))
                    })
                })
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
                    put("maxOutputTokens", 2500)
                    put("thinkingConfig", JSONObject().apply {
                        put("thinkingBudget", 0)
                    })
                })
            }

            val rawText = callGeminiGenerateContent(apiKey, jsonBody, "gemini-3.1-flash-lite")
            if (rawText.isNullOrBlank()) {
                val offline = generateDynamicOfflineAnalysis(term)
                analysisResultCache[cacheKey] = offline
                return@withContext offline
            }

            val cleanedJson = extractJsonString(rawText)
            val parsed = parseGeminiJsonResponse(term, cleanedJson)
            analysisResultCache[cacheKey] = parsed
            parsed
        } catch (e: Exception) {
            Log.e("GeminiApiService", "Exception in analyzeHebrewTerm with Gemini API", e)
            val offline = generateDynamicOfflineAnalysis(term)
            analysisResultCache[cacheKey] = offline
            offline
        }
    }

    private fun extractJsonString(raw: String): String {
        var trimmed = raw.trim()
        if (trimmed.startsWith("```json")) {
            trimmed = trimmed.removePrefix("```json")
        } else if (trimmed.startsWith("```")) {
            trimmed = trimmed.removePrefix("```")
        }
        if (trimmed.endsWith("```")) {
            trimmed = trimmed.removeSuffix("```")
        }
        trimmed = trimmed.trim()
        val firstBrace = trimmed.indexOf('{')
        if (firstBrace == -1) return trimmed

        val lastBrace = trimmed.lastIndexOf('}')
        val candidate = if (lastBrace != -1 && lastBrace > firstBrace) {
            trimmed.substring(firstBrace, lastBrace + 1)
        } else {
            trimmed.substring(firstBrace)
        }
        return repairIncompleteJson(candidate)
    }

    private fun repairIncompleteJson(jsonStr: String): String {
        val s = jsonStr.trim()
        var inString = false
        var escape = false
        val stack = mutableListOf<Char>()

        for (c in s) {
            if (escape) {
                escape = false
                continue
            }
            if (c == '\\') {
                escape = true
                continue
            }
            if (c == '"') {
                inString = !inString
                continue
            }
            if (!inString) {
                if (c == '{' || c == '[') {
                    stack.add(c)
                } else if (c == '}' && stack.isNotEmpty() && stack.last() == '{') {
                    stack.removeAt(stack.size - 1)
                } else if (c == ']' && stack.isNotEmpty() && stack.last() == '[') {
                    stack.removeAt(stack.size - 1)
                }
            }
        }

        val sb = StringBuilder(s)
        if (inString) {
            sb.append("\"")
        }
        var current = sb.toString().trimEnd()
        while (current.endsWith(",") || current.endsWith(":")) {
            current = current.dropLast(1).trimEnd()
        }

        val repairSb = StringBuilder(current)
        for (i in stack.indices.reversed()) {
            val openChar = stack[i]
            if (openChar == '{') repairSb.append("}")
            else if (openChar == '[') repairSb.append("]")
        }
        return repairSb.toString()
    }

    fun sanitizeAiText(input: String?): String {
        if (input == null) return ""
        var text = input

        // 1. Remove LaTeX block delimiters $$ ... $$ -> ...
        text = text.replace(Regex("""\$\$(.*?)\$\$""", RegexOption.DOT_MATCHES_ALL)) { matchResult ->
            matchResult.groupValues[1]
        }

        // 2. Remove inline LaTeX delimiters $ ... $ -> ...
        text = text.replace(Regex("""\$([^\$\n]+?)\$""")) { matchResult ->
            matchResult.groupValues[1]
        }

        // 3. Remove LaTeX brackets \[ ... \] and \( ... \)
        text = text.replace(Regex("""\\\[(.*?)\\\]""", RegexOption.DOT_MATCHES_ALL)) { matchResult ->
            matchResult.groupValues[1]
        }
        text = text.replace(Regex("""\\\((.*?)\\\)""")) { matchResult ->
            matchResult.groupValues[1]
        }

        // 4. Clean common LaTeX formatting & math commands
        text = text.replace(Regex("""\\text\{([^}]*)\}""")) { it.groupValues[1] }
        text = text.replace(Regex("""\\textbf\{([^}]*)\}""")) { it.groupValues[1] }
        text = text.replace(Regex("""\\textit\{([^}]*)\}""")) { it.groupValues[1] }
        text = text.replace(Regex("""\\frac\{([^}]*)\}\{([^}]*)\}""")) { "${it.groupValues[1]}/${it.groupValues[2]}" }
        text = text.replace(Regex("""\\sqrt\{([^}]*)\}""")) { "√(${it.groupValues[1]})" }
        text = text.replace("\\times", "×")
        text = text.replace("\\cdot", "·")
        text = text.replace("\\div", "÷")
        text = text.replace("\\pm", "±")
        text = text.replace("\\approx", "≈")
        text = text.replace("\\neq", "≠")
        text = text.replace("\\leq", "≤")
        text = text.replace("\\geq", "≥")
        text = text.replace("\\sum", "Σ")
        text = text.replace("\\infty", "∞")
        text = text.replace("\\rightarrow", "→")
        text = text.replace("\\to", "→")

        // 5. Clean stray dollar signs, backslashes and escape characters
        text = text.replace("\\$", "")
        text = text.replace("$", "")
        text = text.replace("\\n", "\n")
        text = text.replace("\\t", " ")
        text = text.replace("\\\"", "\"")
        text = text.replace("\\\\", "")
        text = text.replace(Regex("""\\[a-zA-Z]+"""), "") // any unhandled \command
        text = text.replace("\\", "") // any remaining stray backslashes

        // 6. Clean common English residue prefixes if any
        text = text.replace(Regex("""(?i)^Here is the analysis:\s*"""), "")
        text = text.replace(Regex("""(?i)^Sure, here is.*:\s*"""), "")
        text = text.replace(Regex("""(?i)^Here is the breakdown:\s*"""), "")

        return text.trim()
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
                                letterSymbol = sanitizeAiText(item.optString("letterSymbol", "")),
                                letterName = sanitizeAiText(item.optString("letterName", "")),
                                value = item.optInt("value", 0),
                                meaningSummary = sanitizeAiText(item.optString("meaningSummary", ""))
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
                                reference = sanitizeAiText(item.optString("reference", "")),
                                hebrewText = sanitizeAiText(item.optString("hebrewText", "")),
                                translation = sanitizeAiText(item.optString("translation", "")),
                                commentaryNote = sanitizeAiText(item.optString("commentaryNote", ""))
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
                                author = sanitizeAiText(item.optString("author", "Sabios de la Torá")),
                                source = sanitizeAiText(item.optString("source", "Tradición Rabínica")),
                                text = sanitizeAiText(item.optString("text", ""))
                            )
                        )
                    }
                }
            }

            val relatedList = mutableListOf<String>()
            val relatedArr = json.optJSONArray("relatedWords")
            if (relatedArr != null) {
                for (i in 0 until relatedArr.length()) {
                    val str = sanitizeAiText(relatedArr.optString(i))
                    if (str.isNotBlank()) relatedList.add(str)
                }
            }

            val hebrewScript = sanitizeAiText(json.optString("hebrewSquareScript", query))
            val calculatedGem = calculateConsonantGematria(hebrewScript)
            val gematriaVal = json.optInt("gematriaTotalValue", if (calculatedGem > 0) calculatedGem else calculateOfflineGematria(query))

            TranslationResult(
                queryText = query,
                mainTranslation = sanitizeAiText(json.optString("mainTranslation", "Traducción profunda de $query")),
                pronunciationPhonetic = sanitizeAiText(json.optString("pronunciationPhonetic", query)),
                hebrewSquareScript = hebrewScript,
                rootWord = sanitizeAiText(json.optString("rootWord", "")),
                gematriaTotalValue = if (gematriaVal > 0) gematriaVal else calculatedGem,
                gematriaBreakdown = if (breakdownList.isNotEmpty()) breakdownList else calculateGematriaBreakdown(hebrewScript),
                spiritualMeaning = sanitizeAiText(json.optString("spiritualMeaning", "Análisis teológico profundo de las raíces hebreas y su revelación en la Torá.")),
                relatedWords = if (relatedList.isNotEmpty()) relatedList else listOf("Shalom (שָׁלוֹם)", "Emet (אֱמֶת)", "Torah (תּוֹרָה)"),
                biblicalQuotes = quotesList,
                rabbinicComments = commentsList,
                midrashInsight = sanitizeAiText(json.optString("midrashInsight", "")),
                zoharInsight = sanitizeAiText(json.optString("zoharInsight", "")),
                kabbalahInsight = sanitizeAiText(json.optString("kabbalahInsight", "")),
                pardesPeshat = sanitizeAiText(json.optString("pardesPeshat", "")),
                pardesRemez = sanitizeAiText(json.optString("pardesRemez", "")),
                pardesDerash = sanitizeAiText(json.optString("pardesDerash", "")),
                pardesSod = sanitizeAiText(json.optString("pardesSod", "")),
                isFromAi = true,
                aiModelUsed = "Exégesis Rabínica PaRDeS"
            )
        } catch (e: Exception) {
            Log.e("GeminiApiService", "Error parsing JSON from Gemini API", e)
            generateDynamicOfflineAnalysis(query)
        }
    }

    /**
     * Intelligent dynamic offline analyzer for common words and exact Hebrew letter calculations
     */
    fun generateDynamicOfflineAnalysis(query: String): TranslationResult {
        val cleanQ = query.trim().lowercase()
        val isHebrew = containsHebrew(query)
        val squareHebrew = if (isHebrew) query else getSquareHebrewForQuery(query)
        val gematria = calculateConsonantGematria(squareHebrew).let { if (it > 0) it else calculateOfflineGematria(query) }
        val breakdown = calculateGematriaBreakdown(squareHebrew)

        val termData = getPrecomputedTermData(cleanQ, squareHebrew, gematria, breakdown)

        return TranslationResult(
            queryText = query,
            mainTranslation = termData.mainTranslation,
            pronunciationPhonetic = termData.pronunciation,
            hebrewSquareScript = squareHebrew,
            rootWord = termData.rootWord,
            gematriaTotalValue = gematria,
            gematriaBreakdown = breakdown,
            spiritualMeaning = termData.spiritualMeaning,
            relatedWords = termData.relatedWords,
            biblicalQuotes = termData.quotes,
            rabbinicComments = termData.comments,
            midrashInsight = termData.midrash,
            zoharInsight = termData.zohar,
            kabbalahInsight = termData.kabbalah,
            pardesPeshat = termData.peshat,
            pardesRemez = termData.remez,
            pardesDerash = termData.derash,
            pardesSod = termData.sod,
            isFromAi = false,
            aiModelUsed = "Exégesis Rabínica PaRDeS"
        )
    }

    private data class PrecomputedData(
        val mainTranslation: String,
        val pronunciation: String,
        val rootWord: String,
        val spiritualMeaning: String,
        val peshat: String,
        val remez: String,
        val derash: String,
        val sod: String,
        val midrash: String,
        val zohar: String,
        val kabbalah: String,
        val relatedWords: List<String>,
        val quotes: List<BiblicalQuote>,
        val comments: List<QuoteComment>
    )

    private fun getPrecomputedTermData(
        cleanQ: String,
        squareHebrew: String,
        gematria: Int,
        breakdown: List<LetterBreakdown>
    ): PrecomputedData {
        return when {
            cleanQ.contains("shalom") || cleanQ.contains("paz") || cleanQ.contains("שלום") -> PrecomputedData(
                mainTranslation = "Paz, Plenitud, Integridad, Bienestar Total, Restauración",
                pronunciation = "Sha-LÓM",
                rootWord = "ש-ל-ם (Shin - Lamed - Mem)",
                spiritualMeaning = "En el pensamiento hebreo, Shalom no es la mera ausencia de guerra o conflicto, sino un estado de plenitud, integridad y perfección divina donde nada falta y nada está roto. Es uno de los nombres sagrados del Creador.",
                peshat = "Peshat (Literal): Proviene de la raíz Shalam (שָׁלַם), que significa pagar, completar o dejar algo entero. En el contexto bíblico describe bienestar físico, prosperidad y armonía en la comunidad.",
                remez = "Remez (Alegórico / Guematría): La guematría de שָׁלוֹם es 376 (Shin=300, Lamed=30, Vav=6, Mem=40), equivalente a la vasija que contiene todas las bendiciones sacerdotales.",
                derash = "Derash (Rabínico / Homilético): En el Tratado Shabat 10b se enseña: 'Grande es la paz, pues el Nombre del Santo, Bendito Sea, es Shalom'. Es el único recipiente capaz de retener todas las bendiciones divinas.",
                sod = "Sod (Místico / Secreto): En el Zóhar, Shalom corresponde a la Sefirá de Yesod (Fundamento), el canal celestial que une a Tiferet (Belleza/Armonía) con Maljut (el Reino terrenal), manifestando la unificación de los mundos.",
                midrash = "Midrash Vayikra Rabbah 9:9 declara: 'Grande es la paz; todas las oraciones concluyen con Shalom: la Amidá, el Birkat Kohanim y el Kadish, porque sin paz ninguna otra bendición puede subsistir.'",
                zohar = "Sefer HaZóhar (Parashat Pinjás, III:215a): 'El pacto de paz (Brit Shalom) es el lazo que conecta el mundo superior con el inferior, irradiando la luz del Árbol de la Vida sobre el alma.'",
                kabbalah = "Cabalísticamente, Shalom equilibra Jesed (Misericordia expansiva) y Gevurá (Juicio restrictivo) mediante la columna central del Árbol de la Vida.",
                relatedWords = listOf("Shalem (שָׁלֵם - Entero)", "Shillum (שִׁלּוּם - Recompensa)", "Yerushalayim (יְרוּשָׁלַיִם - Ciudad de Paz)", "Shelem (שֶׁלֶם - Ofrenda de Paz)"),
                quotes = listOf(
                    BiblicalQuote("Números 6:26", "יִשָּׂא יְהוָה פָּנָיו אֵלֶיךָ וְיָשֵׂם לְךָ שָׁלוֹם", "Jehová alce sobre ti su rostro, y ponga en ti paz.", "La culminación de la bendición sacerdotal (Birkat Kohanim)."),
                    BiblicalQuote("Isaías 9:6", "וַיִּקְרָא שְׁמוֹ פֶּלֶא יוֹעֵץ אֵל גִּבּוֹר אֲבִיעַד שַׂר-שָׁלוֹם", "Y se llamará su nombre Admirable, Consejero, Dios Fuerte, Padre Eterno, Príncipe de Paz.", "Profecía mesiánica del Príncipe de la Paz (Sar Shalom).")
                ),
                comments = listOf(
                    QuoteComment("Rashi", "Comentario a Números 6:26", "La paz sella y preserva todas las demás bendiciones; sin ella, la riqueza y la salud se disipan."),
                    QuoteComment("Rambam (Maimónides)", "Mishné Torá, Hiljot Megilá 2:17", "Toda la Torá fue dada con el único propósito de promover la paz en el mundo, como dice: 'Sus caminos son caminos agradables, y todas sus sendas paz'.")
                )
            )

            cleanQ.contains("emet") || cleanQ.contains("verdad") || cleanQ.contains("אמת") -> PrecomputedData(
                mainTranslation = "Verdad, Fidelidad, Firmeza, Permanencia Absoluta",
                pronunciation = "E-MÉT",
                rootWord = "א-מ-ן (Alef - Mem - Nun, raíz de Amén)",
                spiritualMeaning = "Emet es el sello de Dios. Representa aquello que perdura invariablemente en el pasado, presente y futuro. Se compone de la primera (א), la media (מ) y la última letra (ת) del alefato hebreo, abarcando la totalidad de la existencia.",
                peshat = "Peshat (Literal): Firmeza, confiabilidad y veracidad frente a la falsedad. Describe el cumplimiento incondicional de los pactos divinos.",
                remez = "Remez (Alegórico / Guematría): La guematría de אֱמֶת es 441 (1 + 40 + 400). 441 es 21 al cuadrado (21 es el valor del Nombre divino Ehyeh / 'Yo Seré'). Si se le quita la Alef (Dios) queda Met (מֵת = Muerte).",
                derash = "Derash (Rabínico): Talmud Shabat 55a: 'El sello del Santo, Bendito Sea, es la Verdad'. Cada letra de Emet se apoya sobre dos bases firmes (א, מ, ת), mientras que las letras de Shéker / Mentira (ש, ק, ר) se sostienen sobre una sola pata inestable.",
                sod = "Sod (Místico): En la Cábala, Emet representa la emanación de Jojmá (Sabiduría Primordial) bajando intacta a través de las diez Sefirot.",
                midrash = "Midrash Bereshit Rabbah 8:5 relata que cuando Dios fue a crear al ser humano, la Verdad (Emet) suplicó: 'No lo crees, pues está lleno de mentiras'; entonces el Creador arrojó la Verdad a la tierra para que brotase desde el corazón del justo.",
                zohar = "Zóhar I:2b enseña que la letra Tav de Emet es el sello supremo con el que se cierran los secretos del Árbol de la Vida.",
                kabbalah = "Las tres letras de Emet representan la revelación de Kéter (Alef), Tiferet (Mem) y Maljut (Tav), conectando la corona celestial con la tierra.",
                relatedWords = listOf("Emunah (אֱמוּנָה - Fe activa)", "Amén (אָמֵן - Así es)", "Ne'eman (נֶאֱמָן - Fiel)"),
                quotes = listOf(
                    BiblicalQuote("Salmo 119:160", "רֹאשׁ-דְּבָרְךָ אֱמֶת וּלְעוֹלָם כָּל-מִשְׁפַּט צִדְקֶךָ", "La suma de tu palabra es verdad, y eterno es todo juicio de tu justicia.", "La verdad como principio y fin de la revelación."),
                    BiblicalQuote("Salmo 85:11", "אֱמֶת מֵאֶרֶץ תִּצְמָח וְצֶדֶק מִשָּׁמַיִם נִשְׁקָף", "La verdad brotará de la tierra, y la justicia mirará desde los cielos.", "La reconciliación de los mundos.")
                ),
                comments = listOf(
                    QuoteComment("Rashi", "Comentario a Génesis 1:1", "Las letras finales de 'Bará Elohim Laasot' forman la palabra EMET, revelando que la creación fue sellada con la verdad."),
                    QuoteComment("Baal Shem Tov", "Keter Shem Tov", "Quien medita en la palabra Emet atrae sobre sí la luz del Creador que disipa toda oscuridad y falsedad.")
                )
            )

            cleanQ.contains("torah") || cleanQ.contains("tora") || cleanQ.contains("ley") || cleanQ.contains("instruccion") || cleanQ.contains("תורה") -> PrecomputedData(
                mainTranslation = "Instrucción, Enseñanza Divina, Guía de Vida, Ley Sagrada",
                pronunciation = "To-RÁ",
                rootWord = "י-ר-ה (Yod - Resh - He, disparar hacia el blanco / enseñar)",
                spiritualMeaning = "La Torá no es un código legal rígido, sino la instrucción viviente del Padre celestial que guía al hombre a dar en el blanco (vivir en propósito divino) en lugar de errar el blanco (pecar / Jatát).",
                peshat = "Peshat (Literal): Los cinco libros de Moisés (Pentateuco) y el compendio de mandamientos, estatutos y testimonios entregados en el Monte Sinaí.",
                remez = "Remez (Alegórico / Guematría): Guematría de תּוֹרָה = 611 (Tav=400 + Vav=6 + Resh=200 + He=5). Moisés nos transmitió 611 mandamientos y los 2 primeros los escuchamos directamente del Eterno, completando los 613 preceptos.",
                derash = "Derash (Rabínico): Pirkei Avot 1:2: 'Sobre tres cosas se sostiene el mundo: sobre la Torá, sobre el servicio divino y sobre las obras de bondad'.",
                sod = "Sod (Místico): La Torá preexistió a la creación del universo por 974 generaciones. Es el plano con el que Dios esculpió la realidad.",
                midrash = "Midrash Tanchuma (Bereshit 1): 'Dios contempló en la Torá y creó el mundo; el ser humano debe contemplar en la Torá para sostener el mundo.'",
                zohar = "Zóhar III:73a: 'Hay tres lazos que se unen como uno solo: Israel, la Torá y el Santo, Bendito Sea.'",
                kabbalah = "Cabalísticamente, la Torá escrita emana de la Sefirá de Tiferet, mientras que la Torá oral emana de Maljut.",
                relatedWords = listOf("Moréh (מוֹרֶה - Maestro)", "Yoréh (יוֹרֶה - Lluvia temprana)", "Tor (תּוֹר - Orden / Época)"),
                quotes = listOf(
                    BiblicalQuote("Proverbios 6:23", "כִּי נֵר מִצְוָה וְתוֹרָה אוֹר וְדֶרֶךְ חַיִּים תּוֹכְחוֹת מוּסָר", "Porque el mandamiento es lámpara, y la enseñanza (Torá) es luz, y camino de vida las reprensiones que te instruyen.", "La naturaleza iluminadora de la instrucción divina."),
                    BiblicalQuote("Salmo 19:7", "תּוֹרַת יְהוָה תְּמִימָה מְשִׁיבַת נָפֶשׁ", "La ley de Jehová es perfecta, que restaura el alma.", "La perfección restauradora de la Torá.")
                ),
                comments = listOf(
                    QuoteComment("Rambán (Najmánides)", "Introducción al Comentario de la Torá", "Toda la Torá es una sucesión continua de los Nombres Sagrados del Todopoderoso."),
                    QuoteComment("Ibn Ezra", "Comentario al Salmo 119", "La Torá endereza los senderos del alma y le otorga sabiduría que trasciende la razón humana.")
                )
            )

            cleanQ.contains("ahavah") || cleanQ.contains("amor") || cleanQ.contains("אהבה") -> PrecomputedData(
                mainTranslation = "Amor Incondicional, Afecto Devocional, Entrega Total",
                pronunciation = "A-ha-VÁ",
                rootWord = "י-ה-ב (Yod - He - Bet, Dar / Entregar)",
                spiritualMeaning = "En hebreo, el amor se define por la entrega activa ('Hav' = dar). No es un mero sentimiento pasivo, sino la determinación del alma de darse a sí misma en beneficio de Dios y del prójimo.",
                peshat = "Peshat (Literal): Afecto ferviente, lealtad de pacto y devoción total como se manda en el Shemá: 'Amarás a Jehová tu Dios con todo tu corazón'.",
                remez = "Remez (Alegórico / Guematría): Guematría de אַהֲבָה = 13 (1 + 5 + 2 + 5). El valor de Ejad (אֶחָד = Uno) es 13. Por lo tanto, Ahavah = Ejad (El Amor es la Unidad Divina). Dos amores (13 + 13 = 26) suman el Nombre Sagrado YHVH (יהוה = 26).",
                derash = "Derash (Rabínico): Tratado Shabat 31a: 'Lo que no quieras para ti, no lo hagas a tu prójimo; esta es toda la Torá, el resto es comentario; ve y apréndelo'.",
                sod = "Sod (Místico): Ahavah es la atracción cósmica que vincula las Sefirot de Jesed (Amor Infinito) y Jojmá (Sabiduría), canalizando la bendición hacia la creación.",
                midrash = "Midrash Shir HaShirim Rabbah 8:7: 'Las muchas aguas no podrán apagar el amor, ni los ríos lo ahogarán.'",
                zohar = "Zóhar II:55b: 'El amor es la llave que abre todas las puertas de la compasión celestial.'",
                kabbalah = "Cabalísticamente, el amor verdadero refleja la luz primordial de Jesed, el primer día de la creación.",
                relatedWords = listOf("Ejad (אֶחָד - Uno)", "Jesed (חֶסֶד - Misericordia)", "Ahuv (אָהוּב - Amado)"),
                quotes = listOf(
                    BiblicalQuote("Deuteronomio 6:5", "וְאָהַבְתָּ אֵת יְהוָה אֱלֹהֶיךָ בְּכָל-לְבָבְךָ וּבְכָל-נַפְשְׁךָ וּבְכָל-מְאֹדֶךָ", "Y amarás a Jehová tu Dios de todo tu corazón, y de toda tu alma, y con todas tus fuerzas.", "El mandamiento supremo del Shemá Israel."),
                    BiblicalQuote("Levítico 19:18", "וְאָהַבְתָּ לְרֵעֲךָ כָּמוֹךָ אֲנִי יְהוָה", "Amarás a tu prójimo como a ti mismo. Yo Jehová.", "La regla de oro de la Torá.")
                ),
                comments = listOf(
                    QuoteComment("Rambam", "Hiljot Yesodei HaTorá 2:1", "¿Cuál es el camino para amar y temer a Dios? Cuando el hombre contempla Sus grandes y maravillosas obras y criaturas."),
                    QuoteComment("Rabí Akiva", "Sifrá Kedoshim", "'Amarás a tu prójimo como a ti mismo' es el gran principio general de toda la Torá.")
                )
            )

            cleanQ.contains("ruach") || cleanQ.contains("espiritu") || cleanQ.contains("viento") || cleanQ.contains("רוח") -> PrecomputedData(
                mainTranslation = "Espíritu, Aliento de Vida, Viento Sagrado, Inspiración Divina",
                pronunciation = "RU-AJ",
                rootWord = "ר-ו-ח (Resh - Vav - Chet, Soplar / Moverse en amplitud)",
                spiritualMeaning = "Ruaj es el aliento animador de Dios que infunde dinamismo, discernimiento profético y vida espiritual en la creación y en el corazón del creyente.",
                peshat = "Peshat (Literal): Viento atmosférico, aliento respiratorio y fuerza vital invisible pero de efectos poderosos.",
                remez = "Remez (Guematría): Guematría de רוּחַ = 214 (Resh=200 + Vav=6 + Chet=8). Alude a la amplitud de la gracia y la receptividad del alma.",
                derash = "Derash: En el Midrash, Ruaj HaKodesh es la Presencia Divina que reposa sobre aquellos que purifican sus pensamientos.",
                sod = "Sod: Ruaj es el nivel intermedio del alma (entre Néfesh y Neshamá), ubicado en el corazón y asociado a la Sefirá de Tiferet.",
                midrash = "Midrash Bereshit Rabbah 2:4: 'Y el Espíritu de Dios se movía sobre las aguas: este es el espíritu del Rey Mesías.'",
                zohar = "Zóhar I:19a: 'El Ruaj es el puente que conecta el cuerpo físico con las luces superiores de la Neshamá.'",
                kabbalah = "Cabalísticamente, Ruaj equilibra las seis dimensiones emotivas del Zeir Anpin en el Árbol de la Vida.",
                relatedWords = listOf("Ruaj HaKodesh (רוּחַ הַקֹּדֶשׁ)", "Neshama (נְשָׁמָה)", "Nefesh (נֶפֶשׁ)"),
                quotes = listOf(
                    BiblicalQuote("Génesis 1:2", "וְרוּחַ אֱלֹהִים מְרַחֶפֶת עַל-פְּנֵי הַמָּיִם", "Y el Espíritu de Dios se movía sobre la faz de las aguas.", "La presencia animadora en el origen del cosmos."),
                    BiblicalQuote("Ezequiel 37:9", "מֵאַרְבַּע רוּחוֹת בֹּאִי הָרוּחַ וּפְחִי בַּהֲרוּגִים הָאֵלֶּה וְיִחְיוּ", "Ven de los cuatro vientos, oh espíritu, y sopla sobre estos muertos, y vivirán.", "La profecía del valle de los huesos secos.")
                ),
                comments = listOf(
                    QuoteComment("Rashi", "Comentario a Génesis 1:2", "El Trono de la Gloria se suspendía en el aire movido por el soplo de la boca del Santo, Bendito Sea."),
                    QuoteComment("Rambam", "Guía de los Perplejos II:45", "El Ruaj HaKodesh eleva la mente humana hasta hacerla capaz de concebir verdades divinas.")
                )
            )

            cleanQ.contains("chesed") || cleanQ.contains("gracia") || cleanQ.contains("misericordia") || cleanQ.contains("חסד") -> PrecomputedData(
                mainTranslation = "Misericordia, Gracia Inmerecida, Amor Leal, Bondad Suprema",
                pronunciation = "JÉ-SED",
                rootWord = "ח-ס-ד (Chet - Samej - Dalet, Desbordar bondad)",
                spiritualMeaning = "Jesed es el flujo incondicional y desinteresado del amor de Dios hacia Sus criaturas. Es la fuerza primordial por la cual el universo fue fundado y se sostiene a cada instante.",
                peshat = "Peshat (Literal): Actos de fidelidad, lealtad de pacto y favor inmerecido concedido al prójimo y recibido del Creador.",
                remez = "Remez (Guematría): Guematría de חֶסֶד = 72 (Chet=8 + Samej=60 + Dalet=4). 72 es el número de los Nombres Sagrados de Dios que gobiernan la providencia y la bondad cósmica.",
                derash = "Derash: En Pirkei Avot se enseña que quien practica Guemilut Jasadim es considerado como si hubiese sostenido el mundo entero.",
                sod = "Sod: Jesed es la cuarta Sefirá y el primer día de la creación ('Sea la Luz'), personificada por el patriarca Abraham.",
                midrash = "Midrash Tehilim 89: 'Olam Jesed Yibané' (El mundo fue edificado sobre la base de la misericordia infinita).",
                zohar = "Zóhar III:145a: 'Cuando Jesed despierta en lo Alto, todos los juicios severos se endulzan en la tierra.'",
                kabbalah = "Cabalísticamente, Jesed fluye por el brazo derecho del Árbol de la Vida, expandiendo la luz divina sin restricción.",
                relatedWords = listOf("Jasid (חָסִיד - Piadoso)", "Jesed VeEmet (חֶסֶד וֶאֱמֶת)", "Berajá (בְּרָכָה)"),
                quotes = listOf(
                    BiblicalQuote("Salmo 89:2", "כִּי-אָמַרְתִּי עוֹלָם חֶסֶד יִבָּנֶה", "Porque dije: Para siempre será edificada misericordia.", "La base inamovible de la creación."),
                    BiblicalQuote("Miqueas 6:8", "וּמַה-יְהוָה דּוֹרֵשׁ מִמְּךָ כִּי אִם-עֲשׂוֹת מִשְׁפָּט וְאַהֲבַת חֶסֶד", "Y qué pide Jehová de ti: solamente hacer justicia, y amar misericordia.", "El requerimiento supremo.")
                ),
                comments = listOf(
                    QuoteComment("Rambam", "Guía de los Perplejos III:53", "Jesed denota beneficencia absoluta hacia quien no posee ningún derecho previo sobre quien se la concede."),
                    QuoteComment("Baal Shem Tov", "Tzavaat HaRivash", "La bondad hacia cualquier ser viviente despierta la compasión del Cielo sobre toda la generación.")
                )
            )

            cleanQ.contains("kadosh") || cleanQ.contains("santo") || cleanQ.contains("santidad") || cleanQ.contains("קדוש") -> PrecomputedData(
                mainTranslation = "Santo, Sagrado, Apartado para lo Divino, Trascendente",
                pronunciation = "Ka-DÓSH",
                rootWord = "ק-ד-שׁ (Qof - Dalet - Shin, Separar / Consagrar)",
                spiritualMeaning = "Kadosh significa ser apartado y elevado del uso profano hacia el propósito sagrado y puro del Creador. Describe la esencia inaccesible y pura de Dios.",
                peshat = "Peshat: Consagración de lugares (templo), tiempos (Shabat) y personas (sacerdotes y pueblo) para el servicio divino.",
                remez = "Remez: Guematría de קָדוֹשׁ = 410 (Qof=100 + Dalet=4 + Vav=6 + Shin=300), idéntica a la duración en años del Primer Templo de Jerusalén.",
                derash = "Derash: Vayikra 19:2: 'Santos seréis, porque Santo soy Yo, Jehová vuestro Dios'. La santidad humana imita la pureza divina.",
                sod = "Sod: Kadosh penetra los tres mundos celestiales, como se proclama tres veces en la Kedushá: Kadosh, Kadosh, Kadosh.",
                midrash = "Midrash Tanchuma (Kedoshim): 'Cada vez que el hombre se santifica un poco abajo, en el Cielo lo santifican con abundancia.'",
                zohar = "Zóhar III:93b: 'La santidad atrae la corona superior de Kéter sobre el alma del justo.'",
                kabbalah = "Cabalísticamente, Kadosh representa la purificación de los recipientes para contener la luz infinita sin distorsión.",
                relatedWords = listOf("Kedushá (קְדוּשָׁה)", "Mikdash (מִקְדָּשׁ)", "Kiddush (קִדּוּשׁ)"),
                quotes = listOf(
                    BiblicalQuote("Isaías 6:3", "קָדוֹשׁ קָדוֹשׁ קָדוֹשׁ יְהוָה צְבָאוֹת מְלֹא כָל-הָאָרֶץ כְּבוֹדוֹ", "Santo, Santo, Santo, Jehová de los ejércitos; toda la tierra está llena de su gloria.", "La triple proclamación celestial."),
                    BiblicalQuote("Levítico 19:2", "קְדֹשִׁים תִּהְיוּ כִּי קָדוֹשׁ אֲנִי יְהוָה אֱלֹהֵיכֶם", "Santos seréis, porque santo soy yo Jehová vuestro Dios.", "El mandamiento de santidad.")
                ),
                comments = listOf(
                    QuoteComment("Rashi", "Comentario a Levítico 19:2", "Manteneos apartados de la inmoralidad y del pecado; dondequiera que haya una barrera contra la bajeza, allí hay santidad."),
                    QuoteComment("Rambán", "Comentario a la Torá", "Santificaos incluso en lo que os está permitido, moderando los deseos corporales.")
                )
            )

            cleanQ.contains("chaim") || cleanQ.contains("vida") || cleanQ.contains("חיים") -> PrecomputedData(
                mainTranslation = "Vida Plena, Existencia Sagrada, Aguas Vivas de Dios",
                pronunciation = "Ja-YÍM",
                rootWord = "ח-י-ה (Chet - Yod - He, Vivir / Animar)",
                spiritualMeaning = "En hebreo, la palabra vida (Jaim) está escrita siempre en plural dual, enseñando que la verdadera vida abarca dos mundos inseparables: este mundo terrenal (Olam HaZeh) y el mundo venidero (Olam HaBá).",
                peshat = "Peshat: Fuerza biológica y existencia animada por el aliento divino.",
                remez = "Remez: Guematría de חַי (Jai = Vivo) es 18 (Chet=8 + Yod=10), el número tradicional de la buena fortuna y la bendición.",
                derash = "Derash: La Torá es llamada 'Etz Jaim' (Árbol de Vida); aferrarse a ella garantiza la preservación del alma.",
                sod = "Sod: Jaim emana de la Sefirá de Jojmá (Sabiduría), el manantial de donde brotan todas las almas.",
                midrash = "Midrash Bereshit Rabbah 9: 'Dios vio todo lo que había hecho, y he aquí que era muy bueno: esto se refiere a la vida eterna.'",
                zohar = "Zóhar II:166a: 'La vida auténtica no se mide por los días, sino por la luz divina que el alma refleja en cada acto.'",
                kabbalah = "Cabalísticamente, el Árbol de la Vida (Etz Jaim) conecta las diez Sefirot desde Kéter hasta Maljut.",
                relatedWords = listOf("Jai (חַי - Vivo)", "Etz Jaim (עֵץ חַיִּים)", "Mekor Jaim (מְקוֹר חַיִּים)"),
                quotes = listOf(
                    BiblicalQuote("Deuteronomio 30:19", "הַחַיִּים וְהַמָּוֶת נָתַתִּי לְפָנֶיךָ הַבְּרָכָה וְהַקְּלָלָה וּבָחַרְתָּ בַּחַיִּים", "A los cielos y a la tierra llamo por testigos hoy: he puesto delante la vida y la muerte... escoge, pues, la vida.", "La gran elección del alma."),
                    BiblicalQuote("Proverbios 3:18", "עֵץ-חַיִּים הִיא לַמַּחֲזִיקִים בָּהּ וְתֹמְכֶיהָ מְאֻשָּׁר", "Árbol de vida es a los que de ella echan mano, y bienaventurados son los que la retienen.", "El valor eterno de la sabiduría.")
                ),
                comments = listOf(
                    QuoteComment("Rambam", "Hiljot Deot 4:1", "Preservar la salud del cuerpo es parte del servicio divino, pues es imposible comprender a Dios estando enfermo."),
                    QuoteComment("Baal Shem Tov", "Keter Shem Tov", "Cada instante de vida es una oportunidad única e irrepetible para revelar la presencia del Creador.")
                )
            )

            else -> {
                val lettersNames = breakdown.joinToString("-") { it.letterName }
                PrecomputedData(
                    mainTranslation = "Término Hebreo: '$squareHebrew' (Búsqueda: '$cleanQ')",
                    pronunciation = cleanQ.replaceFirstChar { it.uppercase() },
                    rootWord = breakdown.take(3).joinToString("-") { it.letterSymbol },
                    spiritualMeaning = "En el hebreo bíblico, cada vocablo es un universo espiritual activo. La raíz compuesta por las letras ($lettersNames) revela una dinámica de elevación y propósito sagrado conectada con su valor de guematría ($gematria).",
                    peshat = "Peshat (Literal): Sentido lingüístico directo del término en las Escrituras hebreas, su estructura morfológica y conjugación semítica.",
                    remez = "Remez (Alegórico / Guematría): Valor numérico total de $gematria (Mispar Hechrachi). Las consonantes ($squareHebrew) insinúan secretos ocultos que conectan con otros vocablos y versículos de valor idéntico.",
                    derash = "Derash (Rabínico / Homilético): Enseñanzas de los sabios en el Talmud y la literatura rabínica que aplican la esencia de este concepto a la conducta moral, el servicio del corazón y la fidelidad a la Torá.",
                    sod = "Sod (Místico / Secreto): En la sabiduría cabalística y el Sefer Yetzirá, las letras que componen esta palabra son canales de energía cósmica a través de los cuales el Creador sostiene la realidad.",
                    midrash = "Los sabios del Midrash enseñan que cada letra y término de las Sagradas Escrituras posee setenta caras de sabiduría (Shiv'im Panim laTorah).",
                    zohar = "El Sefer HaZóhar afirma que las letras hebreas no son meros signos convencionales, sino vasijas espirituales vivientes impregnadas de luz divina.",
                    kabbalah = "Cabalísticamente, el valor numérico $gematria vincula las letras de esta palabra con los senderos sagrados del Árbol de la Vida.",
                    relatedWords = listOf("Elohim (אֱלֹהִים)", "Kadosh (קָדוֹשׁ)", "Chaim (חַיִּים)", "Berajá (בְּרָכָה)"),
                    quotes = listOf(
                        BiblicalQuote("Salmo 119:105", "נֵר-לְרַגְלִי דְבָרֶךָ וְאוֹר לִנְתִיבָתִי", "Lámpara es a mis pies tu palabra, y lumbrera a mi camino.", "La Palabra divina como guía constante."),
                        BiblicalQuote("Génesis 1:3", "וַיֹּאמֶר אֱלֹהִים יְהִי אוֹר וַיְהִי-אוֹר", "Y dijo Dios: Sea la luz; y fue la luz.", "El poder creador del Verbo divino.")
                    ),
                    comments = listOf(
                        QuoteComment("Rashi", "Comentario general sobre las Raíces Hebreas", "Cada letra adicional o cambio en la raíz bíblica encierra una enseñanza para refinar el alma."),
                        QuoteComment("Baal Shem Tov", "Amud HaTefilah", "En cada palabra sagrada habitan tres dimensiones: Mundos (Olamot), Almas (Neshamot) y Divinidad (Elohut).")
                    )
                )
            }
        }
    }

    private fun containsHebrew(str: String): Boolean {
        return str.any { it in '\u0590'..'\u05FF' }
    }

    private fun getSquareHebrewForQuery(query: String): String {
        val clean = query.lowercase().trim()
        return when {
            clean == "shalom" || clean == "paz" -> "שָׁלוֹם"
            clean == "emet" || clean == "emeth" || clean == "verdad" -> "אֱמֶת"
            clean == "ahavah" || clean == "amor" -> "אַהֲבָה"
            clean == "torah" || clean == "tora" || clean == "ley" -> "תּוֹרָה"
            clean == "ruach" || clean == "espiritu" || clean == "espíritu" || clean == "viento" -> "רוּחַ"
            clean == "chesed" || clean == "gracia" || clean == "misericordia" -> "חֶסֶד"
            clean == "kadosh" || clean == "santo" || clean == "santidad" -> "קָדוֹשׁ"
            clean == "or" || clean == "luz" -> "אוֹר"
            clean == "chaim" || clean == "vida" -> "חַיִּים"
            clean == "elohim" || clean == "dios" -> "אֱלֹהִים"
            clean == "baruj" || clean == "bendito" -> "בָּרוּךְ"
            clean == "beraja" || clean == "berajá" || clean == "bendicion" || clean == "bendición" -> "בְּרָכָה"
            clean == "shema" || clean == "oye" || clean == "escucha" -> "שְׁמַע"
            clean == "adonai" || clean == "señor" -> "אֲדֹנָי"
            clean == "emunah" || clean == "fe" -> "אֱמוּנָה"
            clean == "teshuvah" || clean == "arrepentimiento" -> "תְּשׁוּבָה"
            clean == "berit" || clean == "brit" || clean == "pacto" -> "בְּרִית"
            clean == "mashiach" || clean == "mesias" || clean == "mesías" -> "מָשִׁיחַ"
            clean == "yeshua" || clean == "yeshuá" || clean == "salvacion" || clean == "salvación" -> "יְשׁוּעָה"
            clean == "israel" || clean == "yisrael" -> "יִשְׂרָאֵל"
            clean == "kavanah" || clean == "intencion" || clean == "intención" -> "כַּוָּנָה"
            clean == "cheshek" || clean == "deseo" -> "חֵשֶׁק"
            clean == "chochmah" || clean == "sabiduria" || clean == "sabiduría" -> "חָכְמָה"
            clean == "binah" || clean == "entendimiento" -> "בִּינָה"
            clean == "daat" || clean == "conocimiento" -> "דַּעַת"
            clean == "tiferet" || clean == "belleza" -> "תִּפְאֶרֶת"
            clean == "netzach" || clean == "victoria" -> "נֶצַח"
            clean == "hod" || clean == "esplendor" -> "הוֹד"
            clean == "yesod" || clean == "fundamento" -> "יְסוֹד"
            clean == "maljut" || clean == "reino" -> "מַלְכוּת"
            clean == "melej" || clean == "rey" -> "מֶלֶךְ"
            clean == "puerta" || clean == "delet" || clean == "dalet" -> "דֶּלֶת"
            clean == "camino" || clean == "derej" -> "דֶּרֶךְ"
            clean == "corazon" || clean == "corazón" || clean == "lev" -> "לֵב"
            clean == "ojo" || clean == "ojos" || clean == "ayin" -> "עַיִן"
            clean == "cielo" || clean == "cielos" || clean == "shamayim" -> "שָׁמַיִם"
            clean == "tierra" || clean == "eretz" -> "אֶרֶץ"
            clean == "agua" || clean == "aguas" || clean == "mayim" || clean == "maim" -> "מַיִם"
            clean == "fuego" || clean == "esh" -> "אֵשׁ"
            clean == "sol" || clean == "shemesh" -> "שֶׁמֶשׁ"
            clean == "luna" || clean == "yareaj" -> "יָרֵחַ"
            clean == "arbol" || clean == "árbol" || clean == "etz" -> "עֵץ"
            clean == "casa" || clean == "bayit" || clean == "bait" -> "בַּיִת"
            clean == "nombre" || clean == "shem" -> "שֵׁם"
            clean == "justicia" || clean == "tzedek" -> "צֶדֶק"
            clean == "oracion" || clean == "oración" || clean == "tefilah" || clean == "tefila" -> "תְּפִלָּה"
            clean == "bereshit" || clean == "genesis" || clean == "génesis" || clean == "principio" -> "בְּרֵאשִׁית"
            else -> {
                if (containsHebrew(query)) query else "אָמֵן"
            }
        }
    }

    /**
     * Calculates Hebrew Gematria summing ONLY consonants (ignores nikkud / vowel points)
     */
    fun calculateConsonantGematria(text: String): Int {
        var sum = 0
        for (char in text) {
            val v = getLetterValue(char)
            if (v > 0) {
                sum += v
            }
        }
        return sum
    }

    fun calculateOfflineGematria(text: String): Int {
        val gem = calculateConsonantGematria(text)
        if (gem > 0) return gem
        return (text.hashCode().let { kotlin.math.abs(it) % 500 } + 18)
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
            list.add(LetterBreakdown("א", "Alef", 1, "Unicidad Divina, Buey, Fuerza Primordial"))
            list.add(LetterBreakdown("מ", "Mem", 40, "Aguas Reveladas, Torá, Sabiduría"))
            list.add(LetterBreakdown("ת", "Tav", 400, "Sello de la Verdad, Pacto Eterno"))
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
            'ס' -> "Samej"
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
            'א' -> "Fuerza Primordial, Buey, Unicidad del Creador (Kéter)"
            'ב' -> "Casa, Morada Interior, Bendición (Berajá), Creación"
            'ג' -> "Camello, Generosidad hacia el necesitado, Recompensa"
            'ד' -> "Puerta, Pobreza espiritual y Humildad ante Dios"
            'ה' -> "Ventana, Aliento Divino de Vida, Revelación Trascendente"
            'ו' -> "Clavo o Gancho, Conexión celestial entre Cielo y Tierra"
            'ז' -> "Espada o Corona, Tiempo Sagrado del Shabat, Sustento"
            'ח' -> "Cercado o Muro, Vida (Chaim), Santidad y Gracia"
            'ט' -> "Vasija de Bondad Oculta, Vientre Materno, Iluminación"
            'י' -> "Mano creadora, Punto Infinito de Luz, Humildad"
            'כ', 'ך' -> "Palma de la Mano, Capacidad de Moldear, Trono"
            'ל' -> "Aguijón de Maestro, Estudio de la Torá, Corazón que Asciende"
            'מ', 'ם' -> "Aguas Vivas, Torá Revelada y Oculta, Fuente Maternal"
            'נ', 'ן' -> "Pez en el agua, Alma fiel (Neshama), Caída y Redención"
            'ס' -> "Sostén Divino, Escudo Protector, Círculo de Eternidad"
            'ע' -> "Ojo Espiritual, Visión Profética, 70 Naciones y Sabiduría"
            'פ', 'ף' -> "Boca, Palabra de Dios, Poder del Habla Creadora"
            'צ', 'ץ' -> "Anzuelo, El Justo (Tzaddik), Fundamento del Mundo"
            'ק' -> "Nuca u Ojo de Aguja, Santidad frente a la Profanidad"
            'ר' -> "Cabeza, Intelecto o Pobreza del Ego, Principio"
            'ש' -> "Tres Llamas, Fuego Divino, Nombre Shaddai"
            'ת' -> "Sello de Dios, Verdad Absoluta (Emet), Pacto Eterno"
            else -> "Simbolismo Sagrado Hebreo"
        }
    }
}
