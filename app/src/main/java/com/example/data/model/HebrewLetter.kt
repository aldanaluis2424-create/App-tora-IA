package com.example.data.model

data class HebrewLetter(
    val id: String,
    val symbol: String,             // e.g. "א"
    val name: String,               // e.g. "Alef"
    val nameHebrew: String,         // e.g. "אָלֶף"
    val transliteration: String,    // e.g. "' / Silenciosa (Vocal según niqud)"
    val numericValue: Int,          // e.g. 1
    val colorHex: String,           // e.g. "#9E721D"
    val pictographSymbol: String,   // e.g. "𓃾"
    val pictographMeaning: String,  // e.g. "Cabeza de Buey, Fuerza, Líder, Padre"
    val originEvolution: String,    // Detailed origin story & evolution from Proto-Sinaitic to Phoenician to Square Hebrew
    val visualComparison: String,   // Comparison between ancient pictograph, Paleo-Hebrew, Square Script & Cursive
    val gematriaExplanation: String, // Deep explanation of number 1, 1000 (Elef), Unicidad Divina
    val importantWords: List<ImportantWord>, // Words containing this letter
    val letterRelations: String,    // Relationship with Bet, Yod, Vav
    val pardesPeshat: String,       // Literal meaning
    val pardesRemez: String,        // Allegorical / Hinted meaning
    val pardesDerash: String,       // Rabbinic / Homiletical meaning
    val pardesSod: String,          // Mystical / Kabbalistic secret
    val rabbinicComments: List<QuoteComment>, // Rashi, Rambam, Baal Shem Tov
    val talmudReferences: String,   // Talmudic citations (e.g. Shabbat 104a)
    val midrashReferences: String,  // Midrash Rabbah citations (e.g. Bereshit Rabbah 1:10)
    val kabbalahMeaning: String,    // Sefer Yetzirah / Zohar teachings
    val spiritualApplication: String, // Personal spiritual growth
    val practicalApplication: String, // Daily life application
    val biblicalQuotes: List<BiblicalQuote>, // Full Bible verses with reference
    val bibliography: List<String>  // Primary sources
)

data class ImportantWord(
    val hebrew: String,
    val transliteration: String,
    val translation: String,
    val gematriaValue: Int,
    val significance: String
)

data class QuoteComment(
    val author: String,
    val source: String,
    val text: String
)

data class BiblicalQuote(
    val reference: String,          // e.g. "Génesis 1:1"
    val hebrewText: String,
    val translation: String,
    val commentaryNote: String
)
