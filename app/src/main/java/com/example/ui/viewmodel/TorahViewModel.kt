package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.FavoriteEntity
import com.example.data.local.SearchHistoryEntity
import com.example.data.local.UserNoteEntity
import com.example.data.model.*
import com.example.data.repository.TorahRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class TranslationUiState {
    object Idle : TranslationUiState()
    object Loading : TranslationUiState()
    data class Success(val result: TranslationResult) : TranslationUiState()
    data class Error(val message: String) : TranslationUiState()
}

class TorahViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TorahRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = TorahRepository(database.torahDao())
    }

    val letters: List<HebrewLetter> = repository.letters
    val feasts: List<JewishFeast> = repository.feasts
    val months: List<HebrewMonth> = repository.months
    val bibleBooks: List<BibleBook> = repository.bibleBooks
    val wordOfTheDay: ImportantWord = repository.getWordOfTheDay()

    // Reader Settings State
    private val _showReaderSettings = MutableStateFlow(false)
    val showReaderSettings: StateFlow<Boolean> = _showReaderSettings.asStateFlow()

    private val _fontSizeSp = MutableStateFlow(16f)
    val fontSizeSp: StateFlow<Float> = _fontSizeSp.asStateFlow()

    private val _fontFamily = MutableStateFlow("SansSerif") // "SansSerif", "Serif", "Monospace"
    val fontFamily: StateFlow<String> = _fontFamily.asStateFlow()

    private val _readerTheme = MutableStateFlow("Light") // "Light", "Sepia", "Dark"
    val readerTheme: StateFlow<String> = _readerTheme.asStateFlow()

    fun openReaderSettings() { _showReaderSettings.value = true }
    fun closeReaderSettings() { _showReaderSettings.value = false }
    fun setFontSize(size: Float) { _fontSizeSp.value = size }
    fun setFontFamily(family: String) { _fontFamily.value = family }
    fun setReaderTheme(theme: String) { _readerTheme.value = theme }

    // AI Contextual Study Chat
    private val _studyChatHistory = MutableStateFlow<List<StudyChatMessage>>(emptyList())
    val studyChatHistory: StateFlow<List<StudyChatMessage>> = _studyChatHistory.asStateFlow()

    fun askAiStudyQuestion(topicId: String, topicTitle: String, userQuestion: String) {
        if (userQuestion.isBlank()) return
        val msgId = java.util.UUID.randomUUID().toString()
        val newMsg = StudyChatMessage(
            id = msgId,
            topicId = topicId,
            topicTitle = topicTitle,
            userQuestion = userQuestion,
            isLoading = true
        )
        _studyChatHistory.value = _studyChatHistory.value + newMsg

        viewModelScope.launch {
            try {
                val response = repository.askStudyQuestion(topicTitle, userQuestion)
                _studyChatHistory.value = _studyChatHistory.value.map { item ->
                    if (item.id == msgId) {
                        item.copy(aiResponse = response, isLoading = false)
                    } else item
                }
            } catch (e: Exception) {
                _studyChatHistory.value = _studyChatHistory.value.map { item ->
                    if (item.id == msgId) {
                        item.copy(
                            aiResponse = "Ocurrió un inconveniente al consultar a la IA. Intenta nuevamente.",
                            isLoading = false
                        )
                    } else item
                }
            }
        }
    }

    fun clearStudyChatHistoryForTopic(topicId: String) {
        _studyChatHistory.value = _studyChatHistory.value.filterNot { it.topicId == topicId }
    }

    fun regenerateStudyResponse(msgId: String) {
        val message = _studyChatHistory.value.find { it.id == msgId } ?: return
        _studyChatHistory.value = _studyChatHistory.value.map { item ->
            if (item.id == msgId) {
                item.copy(isLoading = true, aiResponse = null)
            } else item
        }

        viewModelScope.launch {
            try {
                val response = repository.askStudyQuestion(message.topicTitle, message.userQuestion)
                _studyChatHistory.value = _studyChatHistory.value.map { item ->
                    if (item.id == msgId) {
                        item.copy(aiResponse = response, isLoading = false)
                    } else item
                }
            } catch (e: Exception) {
                _studyChatHistory.value = _studyChatHistory.value.map { item ->
                    if (item.id == msgId) {
                        item.copy(
                            aiResponse = "Ocurrió un inconveniente al consultar a la IA. Intenta nuevamente.",
                            isLoading = false
                        )
                    } else item
                }
            }
        }
    }

    fun refreshLastStudyResponse(topicId: String) {
        val lastMsg = _studyChatHistory.value.filter { it.topicId == topicId }.lastOrNull()
        if (lastMsg != null) {
            regenerateStudyResponse(lastMsg.id)
        }
    }

    val favorites: StateFlow<List<FavoriteEntity>> = repository.favorites.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val userNotes: StateFlow<List<UserNoteEntity>> = repository.allNotes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val searchHistory: StateFlow<List<SearchHistoryEntity>> = repository.searchHistory.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Translation State
    private val _translationQuery = MutableStateFlow("")
    val translationQuery: StateFlow<String> = _translationQuery.asStateFlow()

    private val _translationState = MutableStateFlow<TranslationUiState>(TranslationUiState.Idle)
    val translationState: StateFlow<TranslationUiState> = _translationState.asStateFlow()

    fun updateTranslationQuery(query: String) {
        _translationQuery.value = query
    }

    fun performTranslation(customQuery: String? = null) {
        val q = customQuery ?: _translationQuery.value
        if (q.isBlank()) return

        viewModelScope.launch {
            _translationState.value = TranslationUiState.Loading
            try {
                val result = repository.translateAndAnalyze(q)
                _translationState.value = TranslationUiState.Success(result)
            } catch (e: Exception) {
                _translationState.value = TranslationUiState.Error(e.message ?: "Error al traducir")
            }
        }
    }

    fun isFavorite(itemId: String): StateFlow<Boolean> {
        return repository.isFavorite(itemId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
    }

    fun toggleFavorite(
        itemId: String,
        itemType: String,
        title: String,
        subtitle: String,
        snippet: String,
        currentlyFav: Boolean
    ) {
        viewModelScope.launch {
            repository.toggleFavorite(itemId, itemType, title, subtitle, snippet, currentlyFav)
        }
    }

    fun addNote(topicId: String, topicTitle: String, category: String, content: String) {
        if (content.isBlank()) return
        viewModelScope.launch {
            repository.addNote(topicId, topicTitle, category, content)
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            repository.deleteNote(noteId)
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }

    fun getLetter(letterId: String): HebrewLetter? {
        return repository.getLetterById(letterId)
    }

    fun getFeast(feastId: String): JewishFeast? {
        return repository.getFeastById(feastId)
    }

    fun getMonth(monthId: String): HebrewMonth? {
        return repository.getMonthById(monthId)
    }

    fun getBibleBook(bookId: String): BibleBook? {
        return repository.getBibleBookById(bookId)
    }

    fun getBibleChapterDetail(bookId: String, chapterNumber: Int): BibleChapterDetail {
        return repository.getBibleChapterDetail(bookId, chapterNumber)
    }
}
