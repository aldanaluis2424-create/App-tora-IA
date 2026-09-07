package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BibleChapterDetail
import com.example.data.model.BibleVerse
import com.example.ui.components.ReaderSettingsDialog
import com.example.ui.viewmodel.TorahViewModel

private data class BibleModuleItem(
    val key: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val badge: String
)

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

    var activeModalSection by remember { mutableStateOf<String?>(null) }
    var showModulesTray by remember { mutableStateOf(false) }

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

    val moduleItems = listOf(
        BibleModuleItem("analysis", "Análisis con IA", "Exégesis, contexto histórico y síntesis teológica", Icons.Default.AutoAwesome, "IA"),
        BibleModuleItem("hebrew", "Texto Hebreo Original", "Texto Masorético y transliteración", Icons.Default.MenuBook, "Hebreo"),
        BibleModuleItem("greek", "Griego Septuaginta (LXX)", "Traducción griega alejandrina", Icons.Default.AccountBalance, "LXX"),
        BibleModuleItem("cross_refs", "Referencias Cruzadas", "Conexiones y citas de todo el Tanaj", Icons.Default.Link, "${chapterDetail.crossReferences.size} Ref."),
        BibleModuleItem("rabbinic", "Comentarios Rabínicos", "Explicaciones de sabios y exégesis tradicional", Icons.Default.Person, "Sabios"),
        BibleModuleItem("notes", "Mis Notas del Capítulo", "Anotaciones y reflexiones personales", Icons.Default.EditNote, "Notas"),
        BibleModuleItem("favorites", "Versos Guardados", "Marcadores y favoritos de este capítulo", Icons.Default.Bookmark, "Guardados")
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
            // Barra de Botones de Módulos estilo iOS (Acceso Rápido a Ventanas Emergentes)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
                shadowElevation = 1.dp
            ) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(moduleItems) { item ->
                        FilledTonalButton(
                            onClick = { activeModalSection = item.key },
                            shape = RoundedCornerShape(16.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = item.title,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Lectura Bíblica Principal en Pantalla Completa
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                TabSpanishVerses(chapterDetail, fontSizeSp, fontFamily, themeTextColor)
            }
        }

        // Ventana emergente a pantalla completa estilo iOS para el módulo seleccionado
        activeModalSection?.let { sectionKey ->
            val modalTitle: String
            val modalIcon: ImageVector
            when (sectionKey) {
                "analysis" -> {
                    modalTitle = "Análisis con IA"
                    modalIcon = Icons.Default.AutoAwesome
                }
                "hebrew" -> {
                    modalTitle = "Texto Hebreo Original"
                    modalIcon = Icons.Default.MenuBook
                }
                "greek" -> {
                    modalTitle = "Griego Septuaginta (LXX)"
                    modalIcon = Icons.Default.AccountBalance
                }
                "cross_refs" -> {
                    modalTitle = "Referencias Cruzadas"
                    modalIcon = Icons.Default.Link
                }
                "rabbinic" -> {
                    modalTitle = "Comentarios Rabínicos"
                    modalIcon = Icons.Default.Person
                }
                "notes" -> {
                    modalTitle = "Mis Notas del Capítulo"
                    modalIcon = Icons.Default.EditNote
                }
                "favorites" -> {
                    modalTitle = "Versos Guardados"
                    modalIcon = Icons.Default.Bookmark
                }
                "study" -> {
                    modalTitle = "Modo Estudio Interactivo"
                    modalIcon = Icons.Default.Psychology
                }
                else -> {
                    modalTitle = "Módulo de Estudio"
                    modalIcon = Icons.Default.MenuBook
                }
            }

            IosFullscreenModal(
                title = modalTitle,
                subtitle = "${chapterDetail.bookName} Capítulo $chapterNumber",
                icon = modalIcon,
                accentColor = MaterialTheme.colorScheme.primary,
                onDismiss = { activeModalSection = null }
            ) {
                SelectionContainer {
                    when (sectionKey) {
                        "analysis" -> TabAiAnalysis(chapterDetail, themeTextColor)
                        "hebrew" -> TabHebrewVerses(chapterDetail, fontSizeSp, themeTextColor)
                        "greek" -> TabGreekVerses(chapterDetail, fontSizeSp, themeTextColor)
                        "cross_refs" -> TabCrossReferences(chapterDetail, themeTextColor)
                        "rabbinic" -> TabRabbinicComments(chapterDetail, themeTextColor)
                        "notes" -> TabUserNotes(chapterDetail, viewModel)
                        "favorites" -> TabFavoritesList(chapterDetail, viewModel)
                    }
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
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

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
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f)
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
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1
                            )
                        }

                        // Copiar verso
                        IconButton(
                            onClick = {
                                val formatted = "${detail.bookName} ${detail.chapterNumber}:${verse.number} — ${verse.textSpanish}"
                                clipboardManager.setText(AnnotatedString(formatted))
                                Toast.makeText(context, "Versículo copiado", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copiar versículo",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    SelectionContainer {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
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
    }
}

@Composable
private fun TabAiAnalysis(
    detail: BibleChapterDetail,
    textColor: Color
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
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

                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(detail.aiAnalysis))
                                Toast.makeText(context, "Análisis copiado al portapapeles", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copiar análisis",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    HorizontalDivider()

                    SelectionContainer {
                        Text(
                            text = detail.aiAnalysis,
                            style = MaterialTheme.typography.bodyMedium,
                            color = textColor,
                            lineHeight = 22.sp
                        )
                    }
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Contexto Histórico Cultural",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(detail.historicalContext))
                                Toast.makeText(context, "Contexto histórico copiado", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copiar contexto",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    SelectionContainer {
                        Text(
                            text = detail.historicalContext,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
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
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Versículo ${verse.number}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        IconButton(
                            onClick = {
                                val toCopy = "${verse.textHebrew}\nTransliteración: ${verse.transliteration}"
                                clipboardManager.setText(AnnotatedString(toCopy))
                                Toast.makeText(context, "Texto en hebreo copiado", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copiar hebreo",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    SelectionContainer {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
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
    }
}

@Composable
private fun TabGreekVerses(
    detail: BibleChapterDetail,
    fontSizeSp: Float,
    textColor: Color
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Septuaginta / Texto Griego — Verso ${verse.number}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        IconButton(
                            onClick = {
                                val greek = if (verse.textGreek.isNotBlank()) verse.textGreek else "Traducción griega no requerida para este capítulo."
                                clipboardManager.setText(AnnotatedString(greek))
                                Toast.makeText(context, "Texto griego copiado", Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copiar griego",
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    SelectionContainer {
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
}

@Composable
private fun TabCrossReferences(
    detail: BibleChapterDetail,
    textColor: Color
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

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
                    modifier = Modifier
                        .padding(14.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Link,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        SelectionContainer {
                            Text(
                                text = ref,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = textColor
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(ref))
                            Toast.makeText(context, "Referencia copiada", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copiar referencia",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
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
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Escribir Nota de Estudio",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        FilledTonalButton(
                            onClick = {
                                val clip = clipboardManager.getText()?.text
                                if (!clip.isNullOrBlank()) {
                                    viewModel.addNote(
                                        topicId = "bible_${detail.bookId}_${detail.chapterNumber}",
                                        topicTitle = "${detail.bookName} Cap. ${detail.chapterNumber}",
                                        category = "Biblia",
                                        content = clip.trim()
                                    )
                                    Toast.makeText(context, "Texto copiado guardado en Notas", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "No hay texto copiado en el portapapeles", Toast.LENGTH_SHORT).show()
                                }
                            },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Icon(Icons.Default.ContentPasteGo, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Guardar copiado", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    OutlinedTextField(
                        value = noteText,
                        onValueChange = { noteText = it },
                        placeholder = { Text("Añade tus reflexiones sobre ${detail.bookName} Cap. ${detail.chapterNumber}...") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 5,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    val clip = clipboardManager.getText()?.text
                                    if (!clip.isNullOrBlank()) {
                                        noteText = if (noteText.isNotBlank()) "$noteText\n$clip" else clip
                                        Toast.makeText(context, "Texto pegado en el campo", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Portapapeles vacío", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentPaste,
                                    contentDescription = "Pegar texto copiado",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    )

                    Button(
                        onClick = {
                            if (noteText.isNotBlank()) {
                                viewModel.addNote(
                                    topicId = "bible_${detail.bookId}_${detail.chapterNumber}",
                                    topicTitle = "${detail.bookName} Cap. ${detail.chapterNumber}",
                                    category = "Biblia",
                                    content = noteText.trim()
                                )
                                noteText = ""
                                Toast.makeText(context, "Nota guardada", Toast.LENGTH_SHORT).show()
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
                    SelectionContainer(modifier = Modifier.weight(1f)) {
                        Text(text = note.content, style = MaterialTheme.typography.bodyMedium)
                    }
                    Row {
                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(note.content))
                                Toast.makeText(context, "Nota copiada", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copiar", tint = MaterialTheme.colorScheme.primary)
                        }
                        IconButton(onClick = { viewModel.deleteNote(note.noteId) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                        }
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
                        SelectionContainer {
                            Column {
                                Text(text = fav.title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                                Text(text = fav.snippet, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}
