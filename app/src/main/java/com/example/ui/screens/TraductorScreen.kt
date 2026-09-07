package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BiblicalQuote
import com.example.data.model.LetterBreakdown
import com.example.data.model.QuoteComment
import com.example.data.model.StudyChatMessage
import com.example.data.model.TranslationResult
import com.example.ui.viewmodel.TorahViewModel
import com.example.ui.viewmodel.TranslationUiState

enum class RabiIaTabMode {
    CHAT_RABI,
    TRANSLATOR_GEMATRIA,
    GEMATRIA_JS_CALCULATOR
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TraductorScreen(
    viewModel: TorahViewModel,
    onNavigateBack: () -> Unit
) {
    var selectedMode by remember { mutableStateOf(RabiIaTabMode.CHAT_RABI) }

    val query by viewModel.translationQuery.collectAsState()
    val translationState by viewModel.translationState.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()
    val customApiKey by viewModel.customApiKey.collectAsState()
    val studyChatHistory by viewModel.studyChatHistory.collectAsState()

    val primaryColor = MaterialTheme.colorScheme.primary
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    var showApiKeyDialog by remember { mutableStateOf(false) }
    var tempApiKey by remember { mutableStateOf(customApiKey) }

    // Dialog para configurar Clave de Gemini API
    if (showApiKeyDialog) {
        AlertDialog(
            onDismissRequest = { showApiKeyDialog = false },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Key,
                        contentDescription = "Clave API",
                        tint = Color(0xFFC59B27)
                    )
                    Text("Configuración de Clave API", fontWeight = FontWeight.Bold)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "El Módulo Rabí utiliza inteligencia artificial para análisis hermenéutico y las fuentes de Sefaria y Wikipedia. Puedes ingresar tu propia API Key de Google AI Studio si deseas usar tu cuota personal.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    OutlinedTextField(
                        value = tempApiKey,
                        onValueChange = { tempApiKey = it },
                        label = { Text("Clave API de Gemini") },
                        placeholder = { Text("AIzaSy...") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        trailingIcon = {
                            if (tempApiKey.isNotEmpty()) {
                                IconButton(onClick = { tempApiKey = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Limpiar")
                                }
                            }
                        }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.saveCustomApiKey(tempApiKey)
                        showApiKeyDialog = false
                        Toast.makeText(context, "Configuración de API guardada", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC59B27))
                ) {
                    Text("Guardar", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showApiKeyDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7F2))
            .imePadding(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Banner Informativo del Motor Rabí-IA
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        tempApiKey = customApiKey
                        showApiKeyDialog = true
                    },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B2A38)),
                border = BorderStroke(1.2.dp, Color(0xFFD4AF37)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFD4AF37),
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "ר",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B2A38)
                            )
                        }
                    }
                    Text(
                        text = "Módulo Rabí-IA",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFFFFDF7A),
                        fontFamily = FontFamily.Serif
                    )
                }
            }
        }

        // =========================================================================
        // TRES PESTAÑAS ADAPTABLES Y ELEGANTES PARA PANTALLAS COMPACTAS Y MODERNAS
        // =========================================================================
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFEDE5D8),
                border = BorderStroke(1.dp, Color(0xFFD8C7B0)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Pestaña 1: Diálogo Rabí-IA
                    val isTab1 = selectedMode == RabiIaTabMode.CHAT_RABI
                    Surface(
                        onClick = { selectedMode = RabiIaTabMode.CHAT_RABI },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("mode_chat_rabi"),
                        shape = RoundedCornerShape(10.dp),
                        color = if (isTab1) Color(0xFF1B2A38) else Color.Transparent
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.QuestionAnswer,
                                contentDescription = null,
                                tint = if (isTab1) Color(0xFFFFDF7A) else Color(0xFF5A4620),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Diálogo",
                                fontSize = 12.sp,
                                fontWeight = if (isTab1) FontWeight.Bold else FontWeight.Medium,
                                color = if (isTab1) Color.White else Color(0xFF5A4620)
                            )
                        }
                    }

                    // Pestaña 2: Traductor PaRDeS
                    val isTab2 = selectedMode == RabiIaTabMode.TRANSLATOR_GEMATRIA
                    Surface(
                        onClick = { selectedMode = RabiIaTabMode.TRANSLATOR_GEMATRIA },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("mode_translator_gematria"),
                        shape = RoundedCornerShape(10.dp),
                        color = if (isTab2) Color(0xFF1B2A38) else Color.Transparent
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = null,
                                tint = if (isTab2) Color(0xFFFFDF7A) else Color(0xFF5A4620),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Traductor",
                                fontSize = 12.sp,
                                fontWeight = if (isTab2) FontWeight.Bold else FontWeight.Medium,
                                color = if (isTab2) Color.White else Color(0xFF5A4620)
                            )
                        }
                    }

                    // Pestaña 3: Gematría & Biblia
                    val isTab3 = selectedMode == RabiIaTabMode.GEMATRIA_JS_CALCULATOR
                    Surface(
                        onClick = { selectedMode = RabiIaTabMode.GEMATRIA_JS_CALCULATOR },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("mode_gematria_js"),
                        shape = RoundedCornerShape(10.dp),
                        color = if (isTab3) Color(0xFF1B2A38) else Color.Transparent
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Calculate,
                                contentDescription = null,
                                tint = if (isTab3) Color(0xFFFFDF7A) else Color(0xFF5A4620),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Gematría",
                                fontSize = 12.sp,
                                fontWeight = if (isTab3) FontWeight.Bold else FontWeight.Medium,
                                color = if (isTab3) Color.White else Color(0xFF5A4620)
                            )
                        }
                    }
                }
            }
        }

        // =========================================================================
        // CONTENIDO SEGÚN LA TARJETA SELECCIONADA
        // =========================================================================
        when (selectedMode) {
            RabiIaTabMode.CHAT_RABI -> {
                item {
                    RabiChatInteractiveSection(
                        viewModel = viewModel,
                        studyChatHistory = studyChatHistory
                    )
                }
            }
            RabiIaTabMode.TRANSLATOR_GEMATRIA -> {
                // Translator input and results
                item {
                    TranslatorAndGematriaInputCard(
                        query = query,
                        translationState = translationState,
                        onQueryChange = { viewModel.updateTranslationQuery(it) },
                        onSubmit = { viewModel.performTranslation() }
                    )
                }

                when (val state = translationState) {
                    is TranslationUiState.Idle -> {
                        if (searchHistory.isNotEmpty()) {
                            item {
                                SearchHistoryCard(
                                    searchHistory = searchHistory,
                                    onSelect = {
                                        viewModel.updateTranslationQuery(it)
                                        viewModel.performTranslation(it)
                                    },
                                    onClear = { viewModel.clearSearchHistory() }
                                )
                            }
                        }
                    }
                    is TranslationUiState.Loading -> {
                        item {
                            TranslationLoadingCard()
                        }
                    }
                    is TranslationUiState.Error -> {
                        item {
                            TranslationErrorCard(
                                message = state.message,
                                onRetry = { viewModel.performTranslation() }
                            )
                        }
                    }
                    is TranslationUiState.Success -> {
                        item {
                            TranslationResultView(result = state.result, viewModel = viewModel)
                        }
                    }
                }
            }
            RabiIaTabMode.GEMATRIA_JS_CALCULATOR -> {
                item {
                    GematriaJsCalculatorSection(
                        viewModel = viewModel,
                        onInterpretPaRDeS = {
                            selectedMode = RabiIaTabMode.TRANSLATOR_GEMATRIA
                        }
                    )
                }
            }
        }
    }
}

/**
 * Tarjeta de selección de modalidad (Chat Rabí-IA vs Traductor y Gematría)
 */
@Composable
private fun RabiModeSelectionCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    badgeText: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    val borderColor = if (isSelected) Color(0xFFC59B27) else Color(0xFFE2D6C0)
    val bgColor = if (isSelected) Color(0xFFFFF9EE) else Color(0xFFFFFFFF)
    val iconBgColor = if (isSelected) Color(0xFFC59B27) else Color(0xFFF3EDE2)
    val iconTint = if (isSelected) Color.White else Color(0xFF7A5D20)

    Card(
        modifier = modifier
            .testTag(testTag)
            .clickable { onClick() }
            .shadow(if (isSelected) 4.dp else 1.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor)
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
                Surface(
                    shape = CircleShape,
                    color = iconBgColor,
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = title,
                            tint = iconTint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isSelected) Color(0xFFC59B27) else Color(0xFFEDE2CE)
                ) {
                    Text(
                        text = badgeText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else Color(0xFF5A4620),
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                    )
                }
            }

            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color(0xFF382504) else Color(0xFF261C08),
                fontFamily = FontFamily.Serif
            )

            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = if (isSelected) Color(0xFF7A5D20) else Color(0xFF756A58),
                lineHeight = 14.sp
            )
        }
    }
}

/**
 * Sección interactiva de Chat con Rabí-IA
 */
@Composable
private fun RabiChatInteractiveSection(
    viewModel: TorahViewModel,
    studyChatHistory: List<StudyChatMessage>
) {
    var userQuestionInput by remember { mutableStateOf("") }
    var selectedTopicCategory by remember { mutableStateOf("Torá y Mitzvot") }
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val currentChatForModule = studyChatHistory.filter { it.topicId.startsWith("rabi_ia") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE5D3B3)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header del Chat
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFF9F4EC),
                    border = BorderStroke(1.dp, Color(0xFFD4AF37)),
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "ר",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF8B672B)
                        )
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Diálogo con Rabí-IA",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E1C05),
                        fontFamily = FontFamily.Serif
                    )
                }

                if (currentChatForModule.isNotEmpty()) {
                    IconButton(
                        onClick = { viewModel.clearStudyChatHistoryForTopic("rabi_ia") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Limpiar Chat",
                            tint = Color(0xFF9E8B6E)
                        )
                    }
                }
            }

            // Sugerencias Rápidas
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                val suggestions = listOf(
                    "¿Qué misterio revela Bereshit 1:1 en el plano del Sod?",
                    "Explica el método PaRDeS y sus 4 niveles de estudio",
                    "¿Cuál es el significado místico de la letra Álef?",
                    "¿Por qué el valor de Shalom en guematría es 376?",
                    "¿Qué enseñan Rashi y Rambam sobre el Shemá Israel?",
                    "¿Cómo se conectan las fiestas bíblicas con la redención?"
                )

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(suggestions) { question ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFF9F4EC),
                            border = BorderStroke(1.dp, Color(0xFFE5D3B3)),
                            modifier = Modifier.clickable {
                                userQuestionInput = question
                                viewModel.askAiStudyQuestion(
                                    topicId = "rabi_ia",
                                    topicTitle = "Consulta Rabínica General",
                                    userQuestion = question
                                )
                                userQuestionInput = ""
                            }
                        ) {
                            Text(
                                text = question,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF4A3716),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            // Lista de Mensajes del Chat
            if (currentChatForModule.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFFCFAF6), RoundedCornerShape(12.dp))
                        .padding(18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChatBubbleOutline,
                            contentDescription = null,
                            tint = Color(0xFFC59B27),
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            text = "Hazle cualquier pregunta al Rabí-IA",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color(0xFF4A3716)
                        )
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    currentChatForModule.forEach { msg ->
                        RabiChatMessageItem(
                            message = msg,
                            onRegenerate = { viewModel.regenerateStudyResponse(msg.id) },
                            onSaveNote = { content ->
                                viewModel.addNote(
                                    topicId = "rabi_ia",
                                    topicTitle = "Pregunta: ${msg.userQuestion.take(30)}...",
                                    category = "RABI_IA",
                                    content = content
                                )
                                Toast.makeText(context, "Respuesta guardada en tus notas", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }

            // Input Field y Botón Enviar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = userQuestionInput,
                    onValueChange = { userQuestionInput = it },
                    placeholder = { Text("Escribe tu pregunta o versículo bíblico...", fontSize = 13.sp) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("rabi_chat_input"),
                    shape = RoundedCornerShape(14.dp),
                    maxLines = 3
                )

                FloatingActionButton(
                    onClick = {
                        if (userQuestionInput.isNotBlank()) {
                            val q = userQuestionInput.trim()
                            userQuestionInput = ""
                            viewModel.askAiStudyQuestion(
                                topicId = "rabi_ia",
                                topicTitle = "Consulta Rabínica General",
                                userQuestion = q
                            )
                        }
                    },
                    containerColor = Color(0xFFC59B27),
                    contentColor = Color.White,
                    modifier = Modifier
                        .size(48.dp)
                        .testTag("rabi_chat_send_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Enviar pregunta"
                    )
                }
            }
        }
    }
}

/**
 * Item individual del Chat con Rabí-IA
 */
@Composable
private fun RabiChatMessageItem(
    message: StudyChatMessage,
    onRegenerate: () -> Unit,
    onSaveNote: (String) -> Unit
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Burbuja del Usuario
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Surface(
                shape = RoundedCornerShape(14.dp).copy(bottomEnd = androidx.compose.foundation.shape.CornerSize(2.dp)),
                color = Color(0xFF1B2A38),
                modifier = Modifier.fillMaxWidth(0.88f)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Estudiante",
                        tint = Color(0xFFFFDF7A),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = message.userQuestion,
                        fontSize = 13.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Burbuja de Rabí-IA
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Surface(
                shape = RoundedCornerShape(14.dp).copy(bottomStart = androidx.compose.foundation.shape.CornerSize(2.dp)),
                color = Color(0xFFFFFBF3),
                border = BorderStroke(1.dp, Color(0xFFE5D3B3)),
                modifier = Modifier.fillMaxWidth(0.95f),
                shadowElevation = 1.dp
            ) {
                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Surface(
                                shape = CircleShape,
                                color = Color(0xFFC59B27),
                                modifier = Modifier.size(20.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "ר",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                            Text(
                                text = "Rabí-IA",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = Color(0xFF5A4620),
                                fontFamily = FontFamily.Serif
                            )
                        }

                        if (!message.isLoading && !message.aiResponse.isNullOrBlank()) {
                            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                IconButton(
                                    onClick = {
                                        clipboardManager.setText(AnnotatedString(message.aiResponse))
                                        Toast.makeText(context, "Respuesta copiada", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copiar",
                                        tint = Color(0xFF8B672B),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                                IconButton(
                                    onClick = { onSaveNote(message.aiResponse) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.BookmarkAdd,
                                        contentDescription = "Guardar en notas",
                                        tint = Color(0xFF8B672B),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }

                                IconButton(
                                    onClick = onRegenerate,
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = "Regenerar",
                                        tint = Color(0xFF8B672B),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }

                    if (message.isLoading) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                color = Color(0xFFC59B27),
                                strokeWidth = 2.dp
                            )
                            Text(
                                text = "Consultando las fuentes sagradas...",
                                fontSize = 12.sp,
                                color = Color(0xFF7A684C)
                            )
                        }
                    } else {
                        SelectionContainer {
                            Text(
                                text = message.aiResponse ?: "Sin respuesta disponible.",
                                fontSize = 13.sp,
                                color = Color(0xFF261C08),
                                lineHeight = 19.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Tarjeta de entrada para el Traductor y Gematría
 */
@Composable
private fun TranslatorAndGematriaInputCard(
    query: String,
    translationState: TranslationUiState,
    onQueryChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE5D3B3)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("translator_input_field"),
                shape = RoundedCornerShape(14.dp),
                placeholder = {
                    Text(
                        text = "escribe una palabra",
                        color = Color(0xFF6B5E48).copy(alpha = 0.4f),
                        fontSize = 14.sp
                    )
                },
                singleLine = true,
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { onQueryChange("") }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Limpiar")
                        }
                    }
                }
            )

            // Chips de Sugerencias Rápidas
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val samples = listOf(
                    "Shalom (שָׁלוֹם)",
                    "Emet (אֱמֶת)",
                    "Ahavah (אַהֲבָה)",
                    "Torah (תּוֹרָה)",
                    "Ruach (רוּחַ)",
                    "Chesed (חֶסֶד)",
                    "Kadosh (קָדוֹשׁ)",
                    "Bereshit 1:1",
                    "Shemot 3:14"
                )
                items(samples) { sample ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF9F4EC),
                        border = BorderStroke(1.dp, Color(0xFFE5D3B3)),
                        modifier = Modifier.clickable {
                            val raw = sample.split(" ")[0]
                            onQueryChange(raw)
                            onSubmit()
                        }
                    ) {
                        Text(
                            text = sample,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF5A4620),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Button(
                onClick = onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("translator_action_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC59B27)),
                enabled = query.isNotBlank() && translationState !is TranslationUiState.Loading
            ) {
                if (translationState is TranslationUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Analizando...", color = Color.White)
                } else {
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Analizar Gematría & PaRDeS", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/**
 * Card de Historial de Búsquedas
 */
@Composable
private fun SearchHistoryCard(
    searchHistory: List<com.example.data.local.SearchHistoryEntity>,
    onSelect: (String) -> Unit,
    onClear: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE5D3B3))
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Historial Reciente de Búsquedas",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Color(0xFF382504),
                    fontFamily = FontFamily.Serif
                )
                TextButton(onClick = onClear) {
                    Text("Borrar", fontSize = 12.sp, color = MaterialTheme.colorScheme.error)
                }
            }
            searchHistory.take(5).forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(item.query) }
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = item.query, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF261C08))
                    Text(text = "Guematría: ${item.gematriaValue}", fontSize = 12.sp, color = Color(0xFF8B672B), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

/**
 * Card de Carga para Traductor
 */
@Composable
private fun TranslationLoadingCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE5D3B3))
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CircularProgressIndicator(color = Color(0xFFC59B27))
            Text(
                text = "Consultando exégesis PaRDeS...",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color(0xFF382504),
                fontFamily = FontFamily.Serif
            )
        }
    }
}

/**
 * Card de Error para Traductor
 */
@Composable
private fun TranslationErrorCard(
    message: String,
    onRetry: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Error al consultar: $message",
                color = MaterialTheme.colorScheme.onErrorContainer,
                fontSize = 13.sp
            )
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Reintentar Consulta", color = Color.White)
            }
        }
    }
}

/**
 * Vista de Resultados del Traductor y Gematría con PaRDeS Completo
 */
@Composable
private fun TranslationResultView(
    result: TranslationResult,
    viewModel: TorahViewModel
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val isFavState by viewModel.isFavorite("trans_${result.queryText}").collectAsState()

    SelectionContainer {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            // Main Summary Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBF3)),
                border = BorderStroke(1.2.dp, Color(0xFFD4AF37)),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFF0E5D0)
                        ) {
                            Text(
                                text = "✨ Exégesis PaRDeS",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF5A4620),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            // Copiar análisis
                            IconButton(
                                onClick = {
                                    val formatted = buildString {
                                        appendLine("Hebreo: ${result.hebrewSquareScript} (${result.queryText})")
                                        appendLine("Traducción: ${result.mainTranslation}")
                                        appendLine("Pronunciación: ${result.pronunciationPhonetic}")
                                        appendLine("Guematría Total: ${result.gematriaTotalValue}")
                                        if (result.rootWord.isNotBlank()) appendLine("Raíz (Shoresh): ${result.rootWord}")
                                        if (result.spiritualMeaning.isNotBlank()) appendLine("\nSignificado Espiritual:\n${result.spiritualMeaning}")
                                        if (result.pardesPeshat.isNotBlank()) appendLine("\nPaRDeS Peshat: ${result.pardesPeshat}")
                                        if (result.pardesRemez.isNotBlank()) appendLine("PaRDeS Remez: ${result.pardesRemez}")
                                        if (result.pardesDerash.isNotBlank()) appendLine("PaRDeS Derash: ${result.pardesDerash}")
                                        if (result.pardesSod.isNotBlank()) appendLine("PaRDeS Sod: ${result.pardesSod}")
                                    }
                                    clipboardManager.setText(AnnotatedString(formatted))
                                    Toast.makeText(context, "Análisis copiado al portapapeles", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copiar análisis",
                                    tint = Color(0xFF8B672B)
                                )
                            }

                            IconButton(
                                onClick = {
                                    viewModel.toggleFavorite(
                                        itemId = "trans_${result.queryText}",
                                        itemType = "TRANSLATION",
                                        title = "${result.hebrewSquareScript} (${result.queryText})",
                                        subtitle = "Traducción: ${result.mainTranslation}",
                                        snippet = "Guematría: ${result.gematriaTotalValue}",
                                        currentlyFav = isFavState
                                    )
                                },
                                modifier = Modifier.testTag("fav_trans_${result.queryText}")
                            ) {
                                Icon(
                                    imageVector = if (isFavState) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = "Guardar en Favoritos",
                                    tint = Color(0xFF8B672B)
                                )
                            }
                        }
                    }

                    Text(
                        text = result.hebrewSquareScript,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF261C08)
                    )

                    if (result.rootWord.isNotBlank()) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFEAD8B2)
                        ) {
                            Text(
                                text = "Raíz (Shoresh): ${result.rootWord}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF382705),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Text(
                        text = result.mainTranslation,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF382504),
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Pronunciación: ${result.pronunciationPhonetic}",
                        fontSize = 13.sp,
                        color = Color(0xFF7A684C)
                    )
                }
            }

            // Gematria Total Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE5D3B3))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Guematría (Mispar Hejrashí)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFF382504),
                            fontFamily = FontFamily.Serif
                        )
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFC59B27)
                        ) {
                            Text(
                                text = "Valor: ${result.gematriaTotalValue}",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    if (result.gematriaBreakdown.isNotEmpty()) {
                        Text(
                            text = "Desglose Letra por Letra:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF5A4620)
                        )
                        result.gematriaBreakdown.forEach { letter ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFF9F4EC), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = letter.letterSymbol, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8B672B))
                                    Text(text = letter.letterName, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF261C08))
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                                    if (letter.meaningSummary.isNotBlank()) {
                                        Text(text = letter.meaningSummary, fontSize = 11.sp, color = Color(0xFF7A684C))
                                    }
                                    Surface(shape = CircleShape, color = Color(0xFFE5D3B3)) {
                                        Text(
                                            text = "${letter.value}",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF382705),
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Exégesis PaRDeS
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE5D3B3))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Exégesis en los 4 Planos (PaRDeS)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color(0xFF382504),
                        fontFamily = FontFamily.Serif
                    )

                    PardesCardItem(title = "Peshat (Literal)", text = result.pardesPeshat, color = Color(0xFF2E7D32))
                    PardesCardItem(title = "Remez (Alusivo / Gematría)", text = result.pardesRemez, color = Color(0xFF1565C0))
                    PardesCardItem(title = "Derash (Homilético / Ético)", text = result.pardesDerash, color = Color(0xFFE65100))
                    PardesCardItem(title = "Sod (Místico / Zóhar)", text = result.pardesSod, color = Color(0xFF6A1B9A))
                }
            }

            // Significado Espiritual
            if (result.spiritualMeaning.isNotBlank()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFE5D3B3))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "Revelación Teológica & Espiritual",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF382504),
                            fontFamily = FontFamily.Serif
                        )
                        Text(
                            text = result.spiritualMeaning,
                            fontSize = 13.sp,
                            color = Color(0xFF261C08),
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // Citas Bíblicas
            if (result.biblicalQuotes.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFE5D3B3))
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Citas Bíblicas Relacionadas",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color(0xFF382504),
                            fontFamily = FontFamily.Serif
                        )
                        result.biblicalQuotes.forEach { quote ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFF9F4EC), RoundedCornerShape(10.dp))
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(text = quote.reference, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color(0xFF8B672B))
                                if (quote.hebrewText.isNotBlank()) {
                                    Text(text = quote.hebrewText, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF261C08))
                                }
                                Text(text = quote.translation, fontSize = 12.sp, color = Color(0xFF4A3716))
                                if (quote.commentaryNote.isNotBlank()) {
                                    Text(text = quote.commentaryNote, fontSize = 11.sp, color = Color(0xFF7A684C))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PardesCardItem(
    title: String,
    text: String,
    color: Color
) {
    if (text.isBlank()) return
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF9F4EC), RoundedCornerShape(10.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
        Text(text = text, fontSize = 12.sp, color = Color(0xFF261C08), lineHeight = 16.sp)
    }
}

/**
 * Sección de Calculadora Local de Gematría Estándar (Función JavaScript)
 * y Consulta de Versículos en Español con Bible-API
 */
@Composable
fun GematriaJsCalculatorSection(
    viewModel: TorahViewModel,
    onInterpretPaRDeS: () -> Unit
) {
    val liveText by viewModel.liveGematriaText.collectAsState()
    val liveSum by viewModel.liveGematriaSum.collectAsState()
    val liveBreakdown by viewModel.liveGematriaBreakdown.collectAsState()

    val context = LocalContext.current

    val goldGradient = Brush.horizontalGradient(
        listOf(Color(0xFFD4AF37), Color(0xFFA67C1E))
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Banner de la Calculadora de Gematría
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1B2A38)),
            border = BorderStroke(1.2.dp, Color(0xFFD4AF37)),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Calculate,
                    contentDescription = null,
                    tint = Color(0xFFFFDF7A),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Calculadora de Gematría",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFDF7A),
                    maxLines = 1,
                    softWrap = false,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Tarjeta de Entrada y Suma en Vivo
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF8)),
            border = BorderStroke(1.dp, Color(0xFFE4D8C4)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Texto Hebreo para el Cálculo:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF382504),
                    fontFamily = FontFamily.Serif
                )

                OutlinedTextField(
                    value = liveText,
                    onValueChange = { viewModel.updateLiveGematriaText(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Escribe o pega texto hebreo...") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF9E6B15),
                        unfocusedBorderColor = Color(0xFFDAC6A2)
                    ),
                    trailingIcon = {
                        if (liveText.isNotEmpty()) {
                            IconButton(onClick = { viewModel.updateLiveGematriaText("") }) {
                                Icon(Icons.Default.Clear, contentDescription = "Borrar", tint = Color(0xFF8A6C3A))
                            }
                        }
                    }
                )

                // Teclado Hebreo Virtual Rápido
                Text(
                    text = "Teclado Hebreo Rápido (Toca para añadir):",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF8A6C3A)
                )

                val hebrewChars = listOf(
                    'א', 'ב', 'ג', 'ד', 'ה', 'ו', 'ז', 'ח', 'ט', 'י', 'כ', 'ך',
                    'ל', 'מ', 'ם', 'נ', 'ן', 'ס', 'ע', 'פ', 'ף', 'צ', 'ץ', 'ק', 'ר', 'ש', 'ת'
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(hebrewChars) { ch ->
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFF5EFE3),
                            border = BorderStroke(1.dp, Color(0xFFDAC6A2)),
                            modifier = Modifier
                                .size(36.dp)
                                .clickable { viewModel.appendHebrewLetterToLive(ch) }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = ch.toString(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF261A07)
                                )
                            }
                        }
                    }
                }

                // Palabras Frecuentes Bíblicas
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val presets = listOf(
                        "שָׁלוֹם" to "Paz (376)",
                        "חַי" to "Vida (18)",
                        "אֱמֶת" to "Verdad (441)",
                        "יְהוָה" to "YHVH (26)",
                        "תּוֹרָה" to "Torá (611)"
                    )
                    items(presets) { (word, label) ->
                        AssistChip(
                            onClick = { viewModel.updateLiveGematriaText(word) },
                            label = { Text(label, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = Color(0xFFF7F2E7),
                                labelColor = Color(0xFF5A3E1B)
                            ),
                            border = BorderStroke(1.dp, Color(0xFFDAC6A2))
                        )
                    }
                }

                // Tarjeta de Valor Total Resultante
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF261A07), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "VALOR TOTAL DE GEMATRÍA",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD4AF37),
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = liveText.ifBlank { "Sin texto" },
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFF7E6),
                                fontFamily = FontFamily.Serif
                            )
                        }

                        Box(
                            modifier = Modifier
                                .background(goldGradient, RoundedCornerShape(12.dp))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "$liveSum",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }
                    }
                }

                // Desglose de Letras
                if (liveBreakdown.isNotEmpty()) {
                    Text(
                        text = "Desglose Letra por Letra:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5A4620),
                        fontFamily = FontFamily.Serif
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        liveBreakdown.forEach { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFFF9F5EC), RoundedCornerShape(10.dp))
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = Color(0xFFD4AF37),
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = item.letterSymbol,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF1B2A38)
                                        )
                                    }
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "${item.letterName} (Valor: ${item.value})",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = Color(0xFF261A07)
                                    )
                                    Text(
                                        text = item.meaningSummary,
                                        fontSize = 11.sp,
                                        color = Color(0xFF6B583E)
                                    )
                                }
                            }
                        }
                    }
                }

                // Botón para interpretar PaRDeS
                Button(
                    onClick = {
                        viewModel.interpretGematriaWithPaRDeS(liveText, liveSum)
                        onInterpretPaRDeS()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF9E6B15)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Interpretar PaRDeS",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

