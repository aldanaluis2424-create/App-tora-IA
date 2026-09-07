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

    private val apiKeyManager = com.example.data.local.ApiKeyManager(application)
    private val repository: TorahRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = TorahRepository(
            torahDao = database.torahDao(),
            apiKeyManager = apiKeyManager
        )
    }

    private val _customApiKey = MutableStateFlow(apiKeyManager.getCustomApiKey())
    val customApiKey: StateFlow<String> = _customApiKey.asStateFlow()

    fun saveCustomApiKey(key: String) {
        apiKeyManager.setCustomApiKey(key)
        _customApiKey.value = key.trim()
    }

    fun hasGeminiKey(): Boolean = apiKeyManager.hasKey()

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
    fun resetReaderSettings() {
        _fontSizeSp.value = 16f
        _fontFamily.value = "SansSerif"
        _readerTheme.value = "Light"
    }

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
            repository.addNote(topicId, topicTitle, category, content.trim())
        }
    }

    fun updateNote(note: UserNoteEntity) {
        viewModelScope.launch {
            repository.saveNoteEntity(note)
        }
    }

    fun updateNoteContent(noteId: Long, newContent: String, topicTitle: String? = null, category: String? = null, topicId: String? = null) {
        if (newContent.isBlank()) return
        viewModelScope.launch {
            repository.updateNote(noteId, newContent.trim(), topicTitle, category, topicId)
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            repository.deleteNote(noteId)
        }
    }

    fun deleteNotes(noteIds: Collection<Long>) {
        if (noteIds.isEmpty()) return
        viewModelScope.launch {
            repository.deleteNotes(noteIds.toList())
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

    private val _favoritesSelectedTab = MutableStateFlow<Int>(0)
    val favoritesSelectedTab: StateFlow<Int> = _favoritesSelectedTab.asStateFlow()

    private val _pendingNoteContentToCreate = MutableStateFlow<String?>(null)
    val pendingNoteContentToCreate: StateFlow<String?> = _pendingNoteContentToCreate.asStateFlow()

    fun setFavoritesSelectedTab(tabIndex: Int) {
        _favoritesSelectedTab.value = tabIndex
    }

    fun openNotesModule(pendingText: String? = null) {
        _favoritesSelectedTab.value = 1
        if (!pendingText.isNullOrBlank()) {
            _pendingNoteContentToCreate.value = pendingText.trim()
        }
    }

    fun clearPendingNoteContent() {
        _pendingNoteContentToCreate.value = null
    }

    // HEBCAL INTERACTIVE API STATE
    private val _todayHebcalDate = MutableStateFlow<com.example.data.remote.HebcalDateResult?>(null)
    val todayHebcalDate: StateFlow<com.example.data.remote.HebcalDateResult?> = _todayHebcalDate.asStateFlow()

    private val _convertedHebcalDate = MutableStateFlow<com.example.data.remote.HebcalDateResult?>(null)
    val convertedHebcalDate: StateFlow<com.example.data.remote.HebcalDateResult?> = _convertedHebcalDate.asStateFlow()

    private val _isConvertingHebcal = MutableStateFlow(false)
    val isConvertingHebcal: StateFlow<Boolean> = _isConvertingHebcal.asStateFlow()

    init {
        loadTodayHebcalDate()
    }

    fun loadTodayHebcalDate() {
        viewModelScope.launch {
            try {
                val res = repository.getTodayHebrewDate()
                _todayHebcalDate.value = res
                if (_convertedHebcalDate.value == null) {
                    _convertedHebcalDate.value = res
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun convertGregorianDate(year: Int, month: Int, day: Int) {
        viewModelScope.launch {
            _isConvertingHebcal.value = true
            try {
                val res = repository.convertGregorianToHebrew(year, month, day)
                _convertedHebcalDate.value = res
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isConvertingHebcal.value = false
            }
        }
    }

    // REAL-TIME LOCAL GEMATRIA STATE (JS STANDARD FUNCTION)
    private val _liveGematriaText = MutableStateFlow("שָׁלוֹם")
    val liveGematriaText: StateFlow<String> = _liveGematriaText.asStateFlow()

    private val _liveGematriaSum = MutableStateFlow(com.example.data.util.GematriaEngine.calcularGematriaEstandar("שָׁלוֹם"))
    val liveGematriaSum: StateFlow<Int> = _liveGematriaSum.asStateFlow()

    private val _liveGematriaBreakdown = MutableStateFlow(com.example.data.util.GematriaEngine.obtenerDesglose("שָׁלוֹם"))
    val liveGematriaBreakdown: StateFlow<List<LetterBreakdown>> = _liveGematriaBreakdown.asStateFlow()

    fun updateLiveGematriaText(text: String) {
        _liveGematriaText.value = text
        val sum = com.example.data.util.GematriaEngine.calcularGematriaEstandar(text)
        _liveGematriaSum.value = sum
        _liveGematriaBreakdown.value = com.example.data.util.GematriaEngine.obtenerDesglose(text)
    }

    fun appendHebrewLetterToLive(letterChar: Char) {
        val updated = _liveGematriaText.value + letterChar
        updateLiveGematriaText(updated)
    }

    fun interpretGematriaWithPaRDeS(hebrewText: String, gematriaVal: Int? = null) {
        val textToAnalyze = hebrewText.ifBlank { _liveGematriaText.value }
        if (textToAnalyze.isBlank()) return
        val calculatedSum = gematriaVal ?: com.example.data.util.GematriaEngine.calcularGematriaEstandar(textToAnalyze)
        _translationQuery.value = textToAnalyze
        performTranslation("$textToAnalyze (Guematría estándar calculada: $calculatedSum)")
    }

    fun getBibleBook(bookId: String): BibleBook? {
        return repository.getBibleBookById(bookId)
    }

    fun getBibleChapterDetail(bookId: String, chapterNumber: Int): BibleChapterDetail {
        return repository.getBibleChapterDetail(bookId, chapterNumber)
    }
}
