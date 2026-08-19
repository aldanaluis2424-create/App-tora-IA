package com.example.data.model

data class JewishFeast(
    val id: String,
    val nameSpanish: String,       // e.g. "Pésaj (Pascua)"
    val nameHebrew: String,        // e.g. "פֶּסַח"
    val hebrewDate: String,        // e.g. "15-22 de Nisán"
    val gregorianApprox: String,   // e.g. "Marzo - Abril"
    val themeColorHex: String,     // e.g. "#9B2226"
    val category: String,          // e.g. "Fiesta de Peregrinaje (Shalosh Regalim)"
    val history: String,           // Full historical context from Egypt to Redemption
    val biblicalBasis: String,     // Leviticus 23, Exodus 12
    val mitzvot: List<String>,     // Commandments (eating Matzah, recounting Haggadah)
    val customs: List<String>,     // Bedikat Chametz, Seder, Afikoman
    val traditionalFoods: List<String>, // Matzah, Maror, Charoset, Zeroah
    val clothingAttire: String,    // White Kittel, festival dress
    val ritualObjects: List<String>, // Seder Plate, Kiddush Cup, Haggadah
    val scriptureQuotes: List<BiblicalQuote>,
    val rabbinicComments: List<QuoteComment>,
    val midrashText: String,
    val kabbalahText: String,
    val pardesPeshat: String,
    val pardesRemez: String,
    val pardesDerash: String,
    val pardesSod: String,
    val eschatology: String,       // Messianic and future redemption dimension
    val modernApplication: String,  // Application for believers today
    val timelineEvents: List<TimelineEvent>
)

data class TimelineEvent(
    val dayOrPeriod: String,
    val eventDescription: String,
    val spiritualSignificance: String
)
