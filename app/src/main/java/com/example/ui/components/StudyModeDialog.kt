package com.example.ui.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.UserNoteEntity
import com.example.ui.viewmodel.TorahViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyModeTabContent(
    topicId: String,
    topicTitle: String,
    category: String,
    viewModel: TorahViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    var userNoteText by remember { mutableStateOf("") }
    var userQuestionText by remember { mutableStateOf("") }
    var editingNote by remember { mutableStateOf<UserNoteEntity?>(null) }
    var editingNoteText by remember { mutableStateOf("") }

    val userNotes by viewModel.userNotes.collectAsState()
    val allChatHistory by viewModel.studyChatHistory.collectAsState()

    val topicChatHistory = remember(allChatHistory, topicId) {
        allChatHistory.filter { it.topicId == topicId }
    }

    val topicNotes = remember(userNotes, topicId) {
        userNotes.filter { it.topicId == topicId }
    }

    val suggestedQuestions = remember(topicTitle) {
        listOf(
            "¿Cuál es el significado místico según el Zóhar y la Cábala?",
            "¿Qué enseñan los sabios del Midrash y el Talmud sobre esto?",
            "¿Cuál es el desglose de su raíz bíblica y guematría?",
            "¿Qué relación tiene con el ciclo agrícola y espiritual?",
            "¿Cuál es su significado profético y mesiánico?"
        )
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Topic Header Badge
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFFF9F5EC),
            border = BorderStroke(1.dp, Color(0xFFE5D3B3)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "MODO DE ESTUDIO INTERACTIVO",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9E6B15),
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = topicTitle,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF261A07),
                        fontFamily = FontFamily.Serif
                    )
                }
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0xFFC59B27)
                ) {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }
        }

        // Section 1: Chat Interactivo con Gemini AI & Motor Teológico
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, Color(0xFFE5D3B3)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFF9F4EC),
                            border = BorderStroke(1.dp, Color(0xFFE8DBCA)),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = "Torah IA",
                                    tint = Color(0xFFC59B27),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Column {
                            Text(
                                text = "Consulta Rabínica & Teológica",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF261A07)
                            )
                            Text(
                                text = "Gemini 3.5 Flash + PaRDeS Exegético",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF9E6B15),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    if (topicChatHistory.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                userQuestionText = ""
                                viewModel.clearStudyChatHistoryForTopic(topicId)
                                Toast.makeText(context, "Historial limpiado", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.DeleteSweep,
                                contentDescription = "Limpiar historial",
                                tint = Color(0xFF8A6C3A)
                            )
                        }
                    }
                }

                Text(
                    text = "Selecciona una pregunta rápida o escribe tu consulta sobre etimología, raíces bíblicas, Talmud, Midrash, Zóhar o simbolismo:",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF5A4A32),
                    lineHeight = 18.sp
                )

                // Chips de preguntas sugeridas
                val chipsScroll = rememberScrollState()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(chipsScroll),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    suggestedQuestions.forEach { suggestion ->
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFF9F4EC),
                            border = BorderStroke(1.dp, Color(0xFFE0CEAC)),
                            modifier = Modifier.clickable {
                                userQuestionText = ""
                                viewModel.askAiStudyQuestion(topicId, topicTitle, suggestion)
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Psychology,
                                    contentDescription = null,
                                    tint = Color(0xFFC59B27),
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = suggestion,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF4A3410)
                                )
                            }
                        }
                    }
                }

                // Input box
                OutlinedTextField(
                    value = userQuestionText,
                    onValueChange = { userQuestionText = it },
                    placeholder = { Text("Escribe tu pregunta sobre $topicTitle...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("study_chat_input"),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            if (userQuestionText.isNotBlank()) {
                                val q = userQuestionText.trim()
                                userQuestionText = ""
                                viewModel.askAiStudyQuestion(topicId, topicTitle, q)
                            }
                        }
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFC59B27),
                        unfocusedBorderColor = Color(0xFFE5D3B3),
                        focusedContainerColor = Color(0xFFFAF8F5),
                        unfocusedContainerColor = Color(0xFFFAF8F5)
                    ),
                    trailingIcon = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (userQuestionText.isNotEmpty()) {
                                IconButton(
                                    onClick = { userQuestionText = "" }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Borrar texto",
                                        tint = Color(0xFF8A6C3A)
                                    )
                                }
                            }
                            IconButton(
                                onClick = {
                                    if (userQuestionText.isNotBlank()) {
                                        val q = userQuestionText.trim()
                                        userQuestionText = ""
                                        viewModel.askAiStudyQuestion(topicId, topicTitle, q)
                                    }
                                },
                                modifier = Modifier.testTag("study_chat_send_btn")
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.Send,
                                    contentDescription = "Enviar",
                                    tint = Color(0xFFC59B27)
                                )
                            }
                        }
                    }
                )

                // Render questions & responses for this topic
                if (topicChatHistory.isNotEmpty()) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        topicChatHistory.forEach { chat ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF7F0)),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, Color(0xFFE8DBCA)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    // User question
                                    Row(
                                        verticalAlignment = Alignment.Top,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Surface(
                                            shape = CircleShape,
                                            color = Color(0xFFC59B27),
                                            modifier = Modifier.size(26.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                Icon(
                                                    Icons.Default.Person,
                                                    contentDescription = null,
                                                    modifier = Modifier.size(15.dp),
                                                    tint = Color.White
                                                )
                                            }
                                        }
                                        SelectionContainer(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = chat.userQuestion,
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF261A07)
                                            )
                                        }
                                    }

                                    HorizontalDivider(color = Color(0xFFE5D3B3).copy(alpha = 0.6f))

                                    // AI response or loader
                                    if (chat.isLoading) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                            modifier = Modifier.padding(vertical = 8.dp)
                                        ) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(20.dp),
                                                strokeWidth = 2.5.dp,
                                                color = Color(0xFFC59B27)
                                            )
                                            Text(
                                                text = "Generando análisis teológico y exegético...",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color(0xFF9E6B15),
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    } else {
                                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                                ) {
                                                    Surface(
                                                        shape = CircleShape,
                                                        color = Color(0xFFF3E8D0),
                                                        modifier = Modifier.size(22.dp)
                                                    ) {
                                                        Box(contentAlignment = Alignment.Center) {
                                                            Icon(
                                                                Icons.Default.AutoAwesome,
                                                                contentDescription = null,
                                                                modifier = Modifier.size(13.dp),
                                                                tint = Color(0xFF9E6B15)
                                                            )
                                                        }
                                                    }
                                                    Text(
                                                        text = "Respuesta Rabínica",
                                                        fontSize = 12.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color(0xFF9E6B15)
                                                    )
                                                }

                                                // Acciones de copia, guardar como nota y reintentar
                                                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                                    IconButton(
                                                        onClick = {
                                                            val resp = chat.aiResponse ?: ""
                                                            viewModel.addNote(
                                                                topicId = topicId,
                                                                topicTitle = topicTitle,
                                                                category = category,
                                                                content = "Pregunta: ${chat.userQuestion}\n\n$resp"
                                                            )
                                                            Toast.makeText(context, "Guardado en Mis Notas", Toast.LENGTH_SHORT).show()
                                                        },
                                                        modifier = Modifier.size(30.dp)
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Default.BookmarkAdd,
                                                            contentDescription = "Guardar como nota",
                                                            tint = Color(0xFFC59B27),
                                                            modifier = Modifier.size(17.dp)
                                                        )
                                                    }

                                                    IconButton(
                                                        onClick = {
                                                            val resp = chat.aiResponse ?: ""
                                                            clipboardManager.setText(AnnotatedString(resp))
                                                            Toast.makeText(context, "Respuesta copiada", Toast.LENGTH_SHORT).show()
                                                        },
                                                        modifier = Modifier.size(30.dp).testTag("copy_ai_response_btn")
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Default.ContentCopy,
                                                            contentDescription = "Copiar respuesta",
                                                            tint = Color(0xFF8A6C3A),
                                                            modifier = Modifier.size(17.dp)
                                                        )
                                                    }

                                                    IconButton(
                                                        onClick = {
                                                            viewModel.regenerateStudyResponse(chat.id)
                                                        },
                                                        modifier = Modifier.size(30.dp)
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Default.Refresh,
                                                            contentDescription = "Reintentar",
                                                            tint = Color(0xFF8A6C3A),
                                                            modifier = Modifier.size(17.dp)
                                                        )
                                                    }
                                                }
                                            }

                                            // Selección y copia de texto de las respuestas
                                            SelectionContainer {
                                                Text(
                                                    text = chat.aiResponse ?: "Sin respuesta disponible.",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    color = Color(0xFF261A07),
                                                    lineHeight = 22.sp,
                                                    modifier = Modifier.fillMaxWidth()
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Vista previa inicial cuando no hay preguntas en el historial
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFAF8F5),
                        border = BorderStroke(1.dp, Color(0xFFEAE0CF)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.MenuBook,
                                    contentDescription = null,
                                    tint = Color(0xFFC59B27),
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "Inicia tu sesión de estudio",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = Color(0xFF261A07)
                                )
                            }
                            Text(
                                text = "Toca cualquiera de las preguntas sugeridas arriba o formula tu propia duda teológica sobre '$topicTitle' para recibir una exégesis estructurada en los 4 niveles de PaRDeS.",
                                fontSize = 12.sp,
                                color = Color(0xFF6B5535),
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }

        // Section 2: Mis Notas Personales
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE5D3B3)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.EditNote,
                        contentDescription = "Notas",
                        tint = Color(0xFFC59B27),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Notas y Reflexiones Personales",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF261A07)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = userNoteText,
                        onValueChange = { userNoteText = it },
                        placeholder = { Text("Escribe una nota personal...") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFC59B27),
                            unfocusedBorderColor = Color(0xFFE5D3B3),
                            focusedContainerColor = Color(0xFFFAF8F5),
                            unfocusedContainerColor = Color(0xFFFAF8F5)
                        )
                    )

                    Button(
                        onClick = {
                            if (userNoteText.isNotBlank()) {
                                viewModel.addNote(
                                    topicId = topicId,
                                    topicTitle = topicTitle,
                                    category = category,
                                    content = userNoteText.trim()
                                )
                                userNoteText = ""
                                Toast.makeText(context, "Nota guardada", Toast.LENGTH_SHORT).show()
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFC59B27),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Text("Guardar", fontWeight = FontWeight.Bold)
                    }
                }

                if (topicNotes.isNotEmpty()) {
                    Text(
                        text = "Notas guardadas (${topicNotes.size}):",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8A6C3A),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    topicNotes.forEach { note ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF7F0)),
                            border = BorderStroke(1.dp, Color(0xFFE8DBCA)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    SelectionContainer(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = note.content,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF261A07),
                                            lineHeight = 18.sp
                                        )
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        // Editar nota
                                        IconButton(
                                            onClick = {
                                                editingNote = note
                                                editingNoteText = note.content
                                            },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Edit,
                                                contentDescription = "Editar nota",
                                                tint = Color(0xFF9E6B15),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }

                                        // Eliminar nota
                                        IconButton(
                                            onClick = {
                                                viewModel.deleteNote(note.noteId)
                                                Toast.makeText(context, "Nota eliminada", Toast.LENGTH_SHORT).show()
                                            },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                Icons.Default.Delete,
                                                contentDescription = "Eliminar",
                                                tint = Color(0xFFBA1A1A),
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Diálogo para editar nota
    editingNote?.let { noteToEdit ->
        AlertDialog(
            onDismissRequest = { editingNote = null },
            title = { Text("Editar Nota", fontWeight = FontWeight.Bold, color = Color(0xFF261A07)) },
            text = {
                OutlinedTextField(
                    value = editingNoteText,
                    onValueChange = { editingNoteText = it },
                    label = { Text("Contenido de la nota") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 4,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFC59B27),
                        unfocusedBorderColor = Color(0xFFE5D3B3)
                    )
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editingNoteText.isNotBlank()) {
                            viewModel.updateNote(noteToEdit.copy(content = editingNoteText.trim()))
                            editingNote = null
                            Toast.makeText(context, "Nota actualizada", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC59B27),
                        contentColor = Color.White
                    )
                ) {
                    Text("Guardar Cambios", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { editingNote = null }) {
                    Text("Cancelar", color = Color(0xFF8A6C3A))
                }
            }
        )
    }
}
