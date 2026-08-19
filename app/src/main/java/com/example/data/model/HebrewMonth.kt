package com.example.data.model

data class HebrewMonth(
    val id: String,
    val nameSpanish: String,       // e.g. "Tishrei"
    val nameHebrew: String,        // e.g. "תִּשְׁרֵי"
    val monthNumberCivil: Int,     // 1
    val monthNumberReligious: Int, // 7
    val gregorianApprox: String,   // "Septiembre - Octubre"
    val associatedTribe: String,   // "Efraín"
    val associatedLetter: String,  // "Lamed (ל)"
    val season: String,            // "Otoño (Stav)"
    val agriculturalHarvest: String, // "Cosecha de higos, granadas y aceitunas"
    val agriculturalIcons: List<String>,
    val festivalsInMonth: List<String>, // "Rosh Hashaná, Yom Kipur, Sukkot, Shemini Atzeret"
    val historyMeaning: String,    // Deep narrative of creation, divine judgment, renewal
    val biblicalQuotes: List<BiblicalQuote>,
    val rabbinicComments: List<QuoteComment>,
    val midrashText: String,
    val eschatologySignificance: String
)
