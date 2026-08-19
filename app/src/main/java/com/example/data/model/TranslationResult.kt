package com.example.data.model

data class TranslationResult(
    val id: String = System.currentTimeMillis().toString(),
    val queryText: String,
    val isHebrewInput: Boolean = true,
    val mainTranslation: String,
    val pronunciationPhonetic: String,
    val hebrewSquareScript: String,
    val gematriaTotalValue: Int,
    val gematriaBreakdown: List<LetterBreakdown>,
    val spiritualMeaning: String,
    val relatedWords: List<String>,
    val biblicalQuotes: List<BiblicalQuote>,
    val rabbinicComments: List<QuoteComment>,
    val midrashInsight: String,
    val kabbalahInsight: String,
    val pardesPeshat: String,
    val pardesRemez: String,
    val pardesDerash: String,
    val pardesSod: String,
    val isFromAi: Boolean = true
)

data class LetterBreakdown(
    val letterSymbol: String,
    val letterName: String,
    val value: Int,
    val meaningSummary: String
)
