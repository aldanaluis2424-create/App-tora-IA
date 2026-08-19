package com.example.data.model

data class BibleBook(
    val id: String,
    val nameSpanish: String,
    val nameHebrew: String,
    val category: String, // "Torá", "Nevi'im", "Ketuvim", "Brit Hadashah"
    val chapterCount: Int,
    val summary: String,
    val keyVerse: String,
    val icon: String
)

data class BibleVerse(
    val number: Int,
    val textSpanish: String,
    val textHebrew: String,
    val textGreek: String = "",
    val transliteration: String = "",
    val notes: String = ""
)

data class BibleChapterDetail(
    val bookId: String,
    val bookName: String,
    val chapterNumber: Int,
    val verses: List<BibleVerse>,
    val aiAnalysis: String,
    val crossReferences: List<String>,
    val rabbinicComments: List<QuoteComment>,
    val historicalContext: String
)
