package com.example.data.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.net.URLEncoder
import java.util.Calendar
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

data class HebcalDateResult(
    val gregorianYear: Int,
    val gregorianMonth: Int,
    val gregorianDay: Int,
    val hebrewYear: Int,
    val hebrewMonthName: String,
    val hebrewDay: Int,
    val hebrewFormatted: String,
    val events: List<String> = emptyList(),
    val isRoshChodesh: Boolean = false
)

/**
 * Servicio de integración con la API pública y gratuita de Hebcal (https://www.hebcal.com/converter)
 * Permite convertir fechas gregorianas a fechas hebreas y viceversa, además de obtener festividades y Rosh Jódesh.
 */
class HebcalApiService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(8, TimeUnit.SECONDS)
        .build()

    private val cache = ConcurrentHashMap<String, HebcalDateResult>()

    /**
     * Convierte una fecha gregoriana a fecha hebrea usando Hebcal API
     */
    suspend fun convertGregorianToHebrew(year: Int, month: Int, day: Int): HebcalDateResult = withContext(Dispatchers.IO) {
        val cacheKey = "g2h_${year}_${month}_$day"
        cache[cacheKey]?.let { return@withContext it }

        try {
            val url = "https://www.hebcal.com/converter?cfg=json&gy=$year&gm=$month&gd=$day&g2h=1"
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val bodyStr = response.body?.string() ?: ""
                val json = JSONObject(bodyStr)

                val gy = json.optInt("gy", year)
                val gm = json.optInt("gm", month)
                val gd = json.optInt("gd", day)
                val hy = json.optInt("hy", year + 3760)
                val hm = json.optString("hm", "Tishrei")
                val hd = json.optInt("hd", 1)
                val hebrewStr = json.optString("hebrew", "$hd de $hm $hy")

                val eventsList = mutableListOf<String>()
                val eventsArr = json.optJSONArray("events")
                if (eventsArr != null) {
                    for (i in 0 until eventsArr.length()) {
                        eventsList.add(eventsArr.getString(i))
                    }
                }

                val isRc = eventsList.any { it.contains("Rosh Chodesh", ignoreCase = true) || it.contains("Rosh Jodesh", ignoreCase = true) }

                val result = HebcalDateResult(
                    gregorianYear = gy,
                    gregorianMonth = gm,
                    gregorianDay = gd,
                    hebrewYear = hy,
                    hebrewMonthName = hm,
                    hebrewDay = hd,
                    hebrewFormatted = hebrewStr,
                    events = eventsList,
                    isRoshChodesh = isRc
                )
                cache[cacheKey] = result
                return@withContext result
            }
        } catch (e: Exception) {
            Log.w("HebcalApiService", "Error querying Hebcal API: ${e.message}")
        }

        // Fallback local si no hay conexión
        val fallback = getFallbackHebrewDate(year, month, day)
        cache[cacheKey] = fallback
        fallback
    }

    /**
     * Convierte una fecha hebrea a fecha gregoriana usando Hebcal API
     */
    suspend fun convertHebrewToGregorian(hebrewYear: Int, hebrewMonth: String, hebrewDay: Int): HebcalDateResult = withContext(Dispatchers.IO) {
        val encodedMonth = URLEncoder.encode(hebrewMonth, "UTF-8")
        val cacheKey = "h2g_${hebrewYear}_${hebrewMonth}_$hebrewDay"
        cache[cacheKey]?.let { return@withContext it }

        try {
            val url = "https://www.hebcal.com/converter?cfg=json&hy=$hebrewYear&hm=$encodedMonth&hd=$hebrewDay&h2g=1"
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val bodyStr = response.body?.string() ?: ""
                val json = JSONObject(bodyStr)

                val gy = json.optInt("gy", hebrewYear - 3760)
                val gm = json.optInt("gm", 1)
                val gd = json.optInt("gd", 1)
                val hy = json.optInt("hy", hebrewYear)
                val hm = json.optString("hm", hebrewMonth)
                val hd = json.optInt("hd", hebrewDay)
                val hebrewStr = json.optString("hebrew", "$hd de $hm $hy")

                val eventsList = mutableListOf<String>()
                val eventsArr = json.optJSONArray("events")
                if (eventsArr != null) {
                    for (i in 0 until eventsArr.length()) {
                        eventsList.add(eventsArr.getString(i))
                    }
                }

                val result = HebcalDateResult(
                    gregorianYear = gy,
                    gregorianMonth = gm,
                    gregorianDay = gd,
                    hebrewYear = hy,
                    hebrewMonthName = hm,
                    hebrewDay = hd,
                    hebrewFormatted = hebrewStr,
                    events = eventsList,
                    isRoshChodesh = eventsList.any { it.contains("Rosh Chodesh", ignoreCase = true) }
                )
                cache[cacheKey] = result
                return@withContext result
            }
        } catch (e: Exception) {
            Log.w("HebcalApiService", "Error converting Hebrew date with Hebcal: ${e.message}")
        }

        val approxGy = hebrewYear - 3760
        HebcalDateResult(
            gregorianYear = approxGy,
            gregorianMonth = 1,
            gregorianDay = 1,
            hebrewYear = hebrewYear,
            hebrewMonthName = hebrewMonth,
            hebrewDay = hebrewDay,
            hebrewFormatted = "$hebrewDay de $hebrewMonth $hebrewYear",
            events = emptyList()
        )
    }

    suspend fun getTodayHebrewDate(): HebcalDateResult {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1
        val day = cal.get(Calendar.DAY_OF_MONTH)
        return convertGregorianToHebrew(year, month, day)
    }

    private fun getFallbackHebrewDate(gy: Int, gm: Int, gd: Int): HebcalDateResult {
        val hy = if (gm >= 9) gy + 3761 else gy + 3760
        val monthNames = listOf(
            "Tishrei", "Cheshvan", "Kislev", "Tevet", "Shevat", "Adar",
            "Nisan", "Iyyar", "Sivan", "Tamuz", "Av", "Elul"
        )
        val approxMonthIndex = (gm + 3) % 12
        val hm = monthNames[approxMonthIndex]
        return HebcalDateResult(
            gregorianYear = gy,
            gregorianMonth = gm,
            gregorianDay = gd,
            hebrewYear = hy,
            hebrewMonthName = hm,
            hebrewDay = gd.coerceIn(1, 29),
            hebrewFormatted = "$gd de $hm $hy (תשפ״ו)",
            events = emptyList()
        )
    }
}
