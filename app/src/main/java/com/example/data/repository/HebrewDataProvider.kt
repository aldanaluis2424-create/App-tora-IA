package com.example.data.repository

import com.example.data.model.*

object HebrewDataProvider {

    val allHebrewLetters: List<HebrewLetter> get() = HebrewLetterData.letters

    val allJewishFeasts: List<JewishFeast> get() = JewishFeastData.feasts

    val allHebrewMonths: List<HebrewMonth> get() = HebrewMonthData.months

    fun getHebrewLetterById(id: String): HebrewLetter? {
        return allHebrewLetters.find { it.id.equals(id, ignoreCase = true) } ?: allHebrewLetters.firstOrNull()
    }

    fun getJewishFeastById(id: String): JewishFeast? {
        return allJewishFeasts.find { it.id.equals(id, ignoreCase = true) } ?: allJewishFeasts.firstOrNull()
    }

    fun getHebrewMonthById(id: String): HebrewMonth? {
        return allHebrewMonths.find { it.id.equals(id, ignoreCase = true) } ?: allHebrewMonths.firstOrNull()
    }

    fun getWordOfTheDay(): ImportantWord {
        val words = listOf(
            ImportantWord("שָׁלוֹם", "Shalom", "Paz, Plenitud, Integridad Divina", 376, "Estado armónico completo donde nada falta"),
            ImportantWord("אֱמֶת", "Emet", "Verdad", 441, "Sello del Creador compuesto por primera, media y última letra"),
            ImportantWord("אַהֲבָה", "Ahavah", "Amor de Pacto", 13, "Mismo valor numérico que Echad (Uno)"),
            ImportantWord("תּוֹרָה", "Torah", "Instrucción de Vida", 611, "Guía divina entregada en el Sinaí"),
            ImportantWord("חֶסֶד", "Chesed", "Gracia / Misericordia Activa", 72, "Bondad inmerecida que sostiene el universo"),
            ImportantWord("קָדוֹשׁ", "Kadosh", "Santo / Apartado", 410, "Trascendencia y pureza divina")
        )
        val dayIndex = (System.currentTimeMillis() / (1000 * 60 * 60 * 24) % words.size).toInt()
        return words[dayIndex]
    }
}
