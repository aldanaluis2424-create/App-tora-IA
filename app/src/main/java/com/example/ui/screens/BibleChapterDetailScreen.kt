package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BibleChapterDetail
import com.example.data.model.BibleVerse
import com.example.ui.components.ReaderSettingsDialog
import com.example.ui.components.StudyModeTabContent
import com.example.ui.viewmodel.TorahViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibleChapterDetailScreen(
    bookId: String,
    chapterNumber: Int,
    viewModel: TorahViewModel,
    onNavigateBack: () -> Unit
) {
    val chapterDetail = viewModel.getBibleChapterDetail(bookId, chapterNumber)
    val isFavState by viewModel.isFavorite("bible_${bookId}_$chapterNumber").collectAsState()

    val fontSizeSp by viewModel.fontSizeSp.collectAsState()
    val fontFamilyName by viewModel.fontFamily.collectAsState()
    val readerTheme by viewModel.readerTheme.collectAsState()

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val fontFamily = remember(fontFamilyName) {
        when(fontFamilyName) {
            "Serif" -> FontFamily.Serif
            "Monospace" -> FontFamily.Monospace
            else -> FontFamily.SansSerif
        }
    }

    val themeBgColor = remember(readerTheme) {
        when(readerTheme) {
            "Sepia" -> Color(0xFFFBF0D9)
            "Dark" -> Color(0xFF1E2A38)
            else -> Color(0xFFFAFAFA)
        }
    }

    val themeTextColor = remember(readerTheme) {
        if (readerTheme == "Dark") Color.White else Color.Black
    }

    val tabs = listOf(
        "📖 Texto Bíblico",
        "🤖 Análisis IA",
        "📜 Hebreo Original",
        "🏛️ Griego (LXX)",
        "🔗 Referencias",
        "💬 Comentarios",
        "📝 Mis Notas",
        "⭐ Favoritos",
        "🛠️ Modo Estudio"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "${chapterDetail.bookName} — Cap. $chapterNumber",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "Lectura & Exégesis Interlineal",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.toggleFavorite(
                                itemId = "bible_${bookId}_$chapterNumber",
                                itemType = "BIBLE_CHAPTER",
                                title = "${chapterDetail.bookName} Capítulo $chapterNumber",
                                subtitle = "Pasaje Bíblico",
                                snippet = chapterDetail.verses.firstOrNull()?.textSpanish ?: "",
                                currentlyFav = isFavState
                            )
                        }
                    ) {
                        Icon(
                            imageVector = if (isFavState) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Favorito",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(themeBgColor)
        ) {
            // Scrollable Tab Row
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                edgePadding = 16.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            // Tab Content
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                when (selectedTabIndex) {
                    0 -> TabSpanishVerses(chapterDetail, fontSizeSp, fontFamily, themeTextColor)
                    1 -> TabAiAnalysis(chapterDetail, themeTextColor)
                    2 -> TabHebrewVerses(chapterDetail, fontSizeSp, themeTextColor)
                    3 -> TabGreekVerses(chapterDetail, fontSizeSp, themeTextColor)
                    4 -> TabCrossReferences(chapterDetail, themeTextColor)
                    5 -> TabRabbinicComments(chapterDetail, themeTextColor)
                    6 -> TabUserNotes(chapterDetail, viewModel)
                    7 -> TabFavoritesList(chapterDetail, viewModel)
                    8 -> StudyModeTabContent(
                        topicId = "bible_${bookId}_$chapterNumber",
                        topicTitle = "${chapterDetail.bookName} Cap. $chapterNumber",
                        category = "Biblia & Tanaj",
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
private fun TabSpanishVerses(
    detail: BibleChapterDetail,
    fontSizeSp: Float,
    fontFamily: FontFamily,
    textColor: Color
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(detail.verses) { verse ->
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.primary
                        ) {
                            Text(
                                text = "Verso ${verse.number}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = verse.transliteration,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Text(
                        text = verse.textSpanish,
                        fontSize = fontSizeSp.sp,
                        fontFamily = fontFamily,
                        color = textColor,
                        lineHeight = (fontSizeSp * 1.4f).sp
                    )

                    if (verse.notes.isNotBlank()) {
                        Text(
                            text = "💡 ${verse.notes}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TabAiAnalysis(
    detail: BibleChapterDetail,
    textColor: Color
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = "IA Exegesis",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = "Análisis Teológico Exegético de Torah IA",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    HorizontalDivider()

                    Text(
                        text = detail.aiAnalysis,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Contexto Histórico Cultural",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = detail.historicalContext,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun TabHebrewVerses(
    detail: BibleChapterDetail,
    fontSizeSp: Float,
    textColor: Color
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(detail.verses) { verse ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Versículo ${verse.number}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = verse.textHebrew,
                        fontSize = (fontSizeSp + 4).sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        lineHeight = (fontSizeSp * 1.5f).sp
                    )
                    Text(
                        text = "Transliteración: ${verse.transliteration}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun TabGreekVerses(
    detail: BibleChapterDetail,
    fontSizeSp: Float,
    textColor: Color
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        items(detail.verses) { verse ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Septuaginta / Texto Griego — Verso ${verse.number}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = if (verse.textGreek.isNotBlank()) verse.textGreek else "Traducción griega no requerida para este capítulo.",
                        fontSize = fontSizeSp.sp,
                        color = textColor,
                        lineHeight = (fontSizeSp * 1.4f).sp
                    )
                }
            }
        }
    }
}

@Composable
private fun TabCrossReferences(
    detail: BibleChapterDetail,
    textColor: Color
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Conexiones Proféticas y Referencias Cruzadas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(detail.crossReferences) { ref ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Link,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = ref,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = textColor
                    )
                }
            }
        }
    }
}

@Composable
private fun TabRabbinicComments(
    detail: BibleChapterDetail,
    textColor: Color
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(detail.rabbinicComments) { comment ->
            CommentCard(comment = comment)
        }
    }
}

@Composable
private fun TabUserNotes(
    detail: BibleChapterDetail,
    viewModel: TorahViewModel
) {
    val topicNotes by viewModel.userNotes.collectAsState()
    val chapterNotes = topicNotes.filter { it.topicId == "bible_${detail.bookId}_${detail.chapterNumber}" }
    var noteText by remember { mutableStateOf("") }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Escribir Nota de Estudio para este Capítulo",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = noteText,
                        onValueChange = { noteText = it },
                        placeholder = { Text("Añade tus reflexiones sobre ${detail.bookName} Cap. ${detail.chapterNumber}...") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 4
                    )

                    Button(
                        onClick = {
                            if (noteText.isNotBlank()) {
                                viewModel.addNote(
                                    topicId = "bible_${detail.bookId}_${detail.chapterNumber}",
                                    topicTitle = "${detail.bookName} Cap. ${detail.chapterNumber}",
                                    category = "Biblia",
                                    content = noteText
                                )
                                noteText = ""
                            }
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Guardar Nota")
                    }
                }
            }
        }

        items(chapterNotes) { note ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = note.content, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
                    IconButton(onClick = { viewModel.deleteNote(note.noteId) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

@Composable
private fun TabFavoritesList(
    detail: BibleChapterDetail,
    viewModel: TorahViewModel
) {
    val favorites by viewModel.favorites.collectAsState()
    val chapterFavorites = favorites.filter { it.itemId == "bible_${detail.bookId}_${detail.chapterNumber}" }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Marcadores y Favoritos en este Capítulo",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        if (chapterFavorites.isEmpty()) {
            item {
                Text(
                    text = "Aún no has guardado este capítulo en tus marcadores.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            items(chapterFavorites) { fav ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(text = fav.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text(text = fav.snippet, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
