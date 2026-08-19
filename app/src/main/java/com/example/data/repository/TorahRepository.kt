package com.example.data.repository

import com.example.data.local.FavoriteEntity
import com.example.data.local.SearchHistoryEntity
import com.example.data.local.TorahDao
import com.example.data.local.UserNoteEntity
import com.example.data.model.*
import com.example.data.remote.GeminiApiService
import kotlinx.coroutines.flow.Flow

class TorahRepository(
    private val torahDao: TorahDao,
    private val geminiApiService: GeminiApiService = GeminiApiService()
) {
    // Static lists
    val letters: List<HebrewLetter> get() = HebrewDataProvider.allHebrewLetters
    val feasts: List<JewishFeast> get() = HebrewDataProvider.allJewishFeasts
    val months: List<HebrewMonth> get() = HebrewDataProvider.allHebrewMonths
    val bibleBooks: List<BibleBook> get() = BibleData.books

    fun getLetterById(id: String): HebrewLetter? = HebrewDataProvider.getHebrewLetterById(id)
    fun getFeastById(id: String): JewishFeast? = HebrewDataProvider.getJewishFeastById(id)
    fun getMonthById(id: String): HebrewMonth? = HebrewDataProvider.getHebrewMonthById(id)
    fun getBibleBookById(id: String): BibleBook? = BibleData.books.find { it.id.equals(id, ignoreCase = true) }
    fun getBibleChapterDetail(bookId: String, chapter: Int): BibleChapterDetail = BibleData.getChapterDetail(bookId, chapter)
    fun getWordOfTheDay(): ImportantWord = HebrewDataProvider.getWordOfTheDay()

    // Gemini Translation
    suspend fun askStudyQuestion(topicTitle: String, userQuestion: String): String {
        return geminiApiService.generateStudyResponse(topicTitle, userQuestion)
    }

    suspend fun translateAndAnalyze(query: String): TranslationResult {
        val result = geminiApiService.analyzeHebrewTerm(query)
        // Record search history
        try {
            torahDao.insertSearchHistory(
                SearchHistoryEntity(
                    query = query,
                    translationResultJson = result.mainTranslation,
                    gematriaValue = result.gematriaTotalValue
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    // Favorites
    val favorites: Flow<List<FavoriteEntity>> = torahDao.getAllFavorites()

    fun isFavorite(itemId: String): Flow<Boolean> = torahDao.isFavorite(itemId)

    suspend fun toggleFavorite(
        itemId: String,
        itemType: String,
        title: String,
        subtitle: String,
        snippet: String,
        currentlyFav: Boolean
    ) {
        if (currentlyFav) {
            torahDao.deleteFavoriteById(itemId)
        } else {
            torahDao.insertFavorite(
                FavoriteEntity(
                    itemId = itemId,
                    itemType = itemType,
                    title = title,
                    subtitle = subtitle,
                    snippet = snippet
                )
            )
        }
    }

    // Notes
    val allNotes: Flow<List<UserNoteEntity>> = torahDao.getAllNotes()

    fun getNotesForTopic(topicId: String): Flow<List<UserNoteEntity>> = torahDao.getNotesForTopic(topicId)

    suspend fun addNote(topicId: String, topicTitle: String, category: String, content: String) {
        torahDao.insertNote(
            UserNoteEntity(
                topicId = topicId,
                topicTitle = topicTitle,
                category = category,
                content = content
            )
        )
    }

    suspend fun deleteNote(noteId: Long) {
        torahDao.deleteNoteById(noteId)
    }

    // Search History
    val searchHistory: Flow<List<SearchHistoryEntity>> = torahDao.getSearchHistory()

    suspend fun clearHistory() {
        torahDao.clearSearchHistory()
    }
}
