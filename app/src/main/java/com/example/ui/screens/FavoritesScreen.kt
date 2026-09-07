package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.FavoriteEntity
import com.example.data.local.UserNoteEntity
import com.example.ui.navigation.Screen
import com.example.ui.viewmodel.TorahViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: TorahViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToRoute: (String) -> Unit
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val favorites by viewModel.favorites.collectAsState()
    val notes by viewModel.userNotes.collectAsState()
    val viewModelSelectedTab by viewModel.favoritesSelectedTab.collectAsState()
    val pendingNoteText by viewModel.pendingNoteContentToCreate.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    // Multi-selection state for notes
    val selectedNoteIds = remember { mutableStateListOf<Long>() }
    val isSelectionMode = selectedNoteIds.isNotEmpty()

    // Note editing state
    var noteToEdit by remember { mutableStateOf<UserNoteEntity?>(null) }
    var editTitleText by remember { mutableStateOf("") }
    var editContentText by remember { mutableStateOf("") }
    var editCategoryText by remember { mutableStateOf("") }

    // Note creation state
    var showCreateNoteDialog by remember { mutableStateOf(false) }
    var newNoteTitle by remember { mutableStateOf("") }
    var newNoteContent by remember { mutableStateOf("") }
    var newNoteCategory by remember { mutableStateOf("General") }

    LaunchedEffect(viewModelSelectedTab) {
        selectedTab = viewModelSelectedTab
    }

    LaunchedEffect(pendingNoteText) {
        pendingNoteText?.let { text ->
            selectedTab = 1
            newNoteTitle = if (text.length <= 30) text.take(30) else text.take(30) + "..."
            newNoteContent = text
            newNoteCategory = "General"
            showCreateNoteDialog = true
            viewModel.clearPendingNoteContent()
        }
    }

    val primaryColor = MaterialTheme.colorScheme.primary

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSelectionMode && selectedTab == 1) {
                        Text(
                            text = "${selectedNoteIds.size} seleccionada(s)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    } else {
                        Text(
                            text = "Mis Favoritos y Notas",
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (isSelectionMode) {
                                selectedNoteIds.clear()
                            } else {
                                onNavigateBack()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isSelectionMode) Icons.Default.Close else Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
                },
                actions = {
                    if (selectedTab == 1) {
                        if (isSelectionMode) {
                            // Copiar seleccionadas
                            IconButton(
                                onClick = {
                                    val selectedText = notes.filter { it.noteId in selectedNoteIds }
                                        .joinToString("\n\n---\n\n") { "${it.topicTitle} (${it.category}):\n${it.content}" }
                                    clipboardManager.setText(AnnotatedString(selectedText))
                                    Toast.makeText(context, "${selectedNoteIds.size} notas copiadas al portapapeles", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "Copiar seleccionadas",
                                    tint = primaryColor
                                )
                            }

                            // Seleccionar todo / deseleccionar
                            IconButton(
                                onClick = {
                                    if (selectedNoteIds.size == notes.size) {
                                        selectedNoteIds.clear()
                                    } else {
                                        selectedNoteIds.clear()
                                        selectedNoteIds.addAll(notes.map { it.noteId })
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SelectAll,
                                    contentDescription = "Seleccionar todo",
                                    tint = primaryColor
                                )
                            }

                            // Eliminar seleccionadas
                            IconButton(
                                onClick = {
                                    val count = selectedNoteIds.size
                                    viewModel.deleteNotes(selectedNoteIds.toList())
                                    selectedNoteIds.clear()
                                    Toast.makeText(context, "$count nota(s) eliminada(s)", Toast.LENGTH_SHORT).show()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Eliminar seleccionadas",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        } else if (notes.isNotEmpty()) {
                            // Iniciar modo de selección
                            IconButton(
                                onClick = {
                                    selectedNoteIds.addAll(notes.map { it.noteId })
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Checklist,
                                    contentDescription = "Seleccionar notas",
                                    tint = primaryColor
                                )
                            }
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (selectedTab == 1 && !isSelectionMode) {
                FloatingActionButton(
                    onClick = {
                        newNoteTitle = ""
                        newNoteContent = ""
                        newNoteCategory = "General"
                        showCreateNoteDialog = true
                    },
                    containerColor = primaryColor,
                    contentColor = Color.White
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Nueva Nota")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                IosSegmentedControl(
                    items = listOf(
                        "Marcadores (${favorites.size})" to Icons.Default.Bookmark,
                        "Mis Notas (${notes.size})" to Icons.Default.EditNote
                    ),
                    selectedIndex = selectedTab,
                    onItemSelected = { index ->
                        selectedTab = index
                        viewModel.setFavoritesSelectedTab(index)
                        if (index == 0) selectedNoteIds.clear()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (selectedTab == 0) {
                // FAVORITOS / MARCADORES
                if (favorites.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.BookmarkBorder,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = "Sin elementos guardados",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Toca el icono de marcador en cualquier letra, fiesta o mes para guardarlo aquí.",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 13.sp,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                } else {
                    SelectionContainer {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(favorites) { fav ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            when (fav.itemType) {
                                                "LETTER" -> onNavigateToRoute(Screen.LetterDetail.createRoute(fav.itemId.replace("letter_", "")))
                                                "FEAST" -> onNavigateToRoute(Screen.FeastDetail.createRoute(fav.itemId.replace("feast_", "")))
                                                "MONTH" -> onNavigateToRoute(Screen.MonthDetail.createRoute(fav.itemId.replace("month_", "")))
                                                "TRANSLATION" -> {
                                                    val query = fav.itemId.replace("trans_", "")
                                                    viewModel.updateTranslationQuery(query)
                                                    viewModel.performTranslation(query)
                                                    onNavigateToRoute(Screen.Translator.route)
                                                }
                                            }
                                        },
                                    shape = RoundedCornerShape(14.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(14.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Surface(
                                                shape = RoundedCornerShape(6.dp),
                                                color = primaryColor.copy(alpha = 0.15f)
                                            ) {
                                                Text(
                                                    text = fav.itemType,
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = primaryColor,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(text = fav.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                            Text(text = fav.subtitle, fontSize = 12.sp, color = primaryColor)
                                            if (fav.snippet.isNotBlank()) {
                                                Text(text = fav.snippet, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                            }
                                        }

                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            IconButton(
                                                onClick = {
                                                    val shareText = "${fav.title}\n${fav.subtitle}\n${fav.snippet}"
                                                    clipboardManager.setText(AnnotatedString(shareText))
                                                    Toast.makeText(context, "Copiado al portapapeles", Toast.LENGTH_SHORT).show()
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.ContentCopy,
                                                    contentDescription = "Copiar",
                                                    tint = primaryColor,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }

                                            IconButton(
                                                onClick = {
                                                    viewModel.toggleFavorite(
                                                        itemId = fav.itemId,
                                                        itemType = fav.itemType,
                                                        title = fav.title,
                                                        subtitle = fav.subtitle,
                                                        snippet = fav.snippet,
                                                        currentlyFav = true
                                                    )
                                                    Toast.makeText(context, "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Eliminar",
                                                    tint = MaterialTheme.colorScheme.error,
                                                    modifier = Modifier.size(20.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // NOTAS PERSONALES
                if (notes.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.EditNote,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = "No tienes notas personales",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Puedes crear notas desde aquí o desde el Modo Estudio y Traductor en cualquier sección.",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 13.sp,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = {
                                        newNoteTitle = ""
                                        newNoteContent = ""
                                        newNoteCategory = "General"
                                        showCreateNoteDialog = true
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Crear Primera Nota")
                                }

                                FilledTonalButton(
                                    onClick = {
                                        val clip = clipboardManager.getText()?.text
                                        if (!clip.isNullOrBlank()) {
                                            newNoteTitle = "Nota Copiada"
                                            newNoteContent = clip
                                            newNoteCategory = "General"
                                            showCreateNoteDialog = true
                                        } else {
                                            Toast.makeText(context, "No hay texto en el portapapeles", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    Icon(Icons.Default.ContentPasteGo, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Pegar Copiado")
                                }
                            }
                        }
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Quick Action Header for Notes
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = {
                                        newNoteTitle = ""
                                        newNoteContent = ""
                                        newNoteCategory = "General"
                                        showCreateNoteDialog = true
                                    },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Nueva Nota")
                                }

                                FilledTonalButton(
                                    onClick = {
                                        val clip = clipboardManager.getText()?.text
                                        if (!clip.isNullOrBlank()) {
                                            newNoteTitle = "Nota Copiada"
                                            newNoteContent = clip
                                            newNoteCategory = "General"
                                            showCreateNoteDialog = true
                                        } else {
                                            Toast.makeText(context, "No hay texto copiado en el portapapeles", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Default.ContentPasteGo, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Pegar a Nota")
                                }
                            }
                        }

                        items(notes, key = { it.noteId }) { note ->
                            val isSelected = note.noteId in selectedNoteIds

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (isSelectionMode) {
                                            if (isSelected) {
                                                selectedNoteIds.remove(note.noteId)
                                            } else {
                                                selectedNoteIds.add(note.noteId)
                                            }
                                        }
                                    },
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) primaryColor.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surface
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
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
                                            if (isSelectionMode) {
                                                Checkbox(
                                                    checked = isSelected,
                                                    onCheckedChange = { checked ->
                                                        if (checked) selectedNoteIds.add(note.noteId)
                                                        else selectedNoteIds.remove(note.noteId)
                                                    }
                                                )
                                            }

                                            Column {
                                                Text(
                                                    text = note.topicTitle,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 15.sp,
                                                    color = primaryColor
                                                )
                                                Text(
                                                    text = note.category,
                                                    fontSize = 11.sp,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        }

                                        // Actions row
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            // Copiar nota
                                            IconButton(
                                                onClick = {
                                                    clipboardManager.setText(AnnotatedString(note.content))
                                                    Toast.makeText(context, "Nota copiada al portapapeles", Toast.LENGTH_SHORT).show()
                                                },
                                                modifier = Modifier.size(32.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.ContentCopy,
                                                    contentDescription = "Copiar nota",
                                                    tint = primaryColor,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }

                                            // Editar nota
                                            IconButton(
                                                onClick = {
                                                    noteToEdit = note
                                                    editTitleText = note.topicTitle
                                                    editContentText = note.content
                                                    editCategoryText = note.category
                                                },
                                                modifier = Modifier.size(32.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Edit,
                                                    contentDescription = "Editar nota",
                                                    tint = MaterialTheme.colorScheme.secondary,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }

                                            // Eliminar nota individual
                                            IconButton(
                                                onClick = {
                                                    viewModel.deleteNote(note.noteId)
                                                    Toast.makeText(context, "Nota eliminada", Toast.LENGTH_SHORT).show()
                                                },
                                                modifier = Modifier.size(32.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Eliminar",
                                                    tint = MaterialTheme.colorScheme.error,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                        }
                                    }

                                    HorizontalDivider(
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                                    )

                                    // Contenido seleccionable y legible
                                    SelectionContainer {
                                        Text(
                                            text = note.content,
                                            fontSize = 13.sp,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            lineHeight = 19.sp,
                                            modifier = Modifier.fillMaxWidth()
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

    // Diálogo de Edición de Nota a Pantalla Completa
    noteToEdit?.let { note ->
        Dialog(
            onDismissRequest = { noteToEdit = null },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = false
            )
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Column {
                                Text(
                                    text = "Editar Nota",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = if (editCategoryText.isNotBlank()) editCategoryText else note.category,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { noteToEdit = null }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Cerrar"
                                )
                            }
                        },
                        actions = {
                            Button(
                                onClick = {
                                    val finalContent = editContentText.trim()
                                    if (finalContent.isNotBlank()) {
                                        viewModel.updateNote(
                                            note.copy(
                                                topicTitle = if (editTitleText.isNotBlank()) editTitleText.trim() else note.topicTitle,
                                                category = if (editCategoryText.isNotBlank()) editCategoryText else note.category,
                                                content = finalContent
                                            )
                                        )
                                        noteToEdit = null
                                        Toast.makeText(context, "Nota actualizada correctamente", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "El contenido de la nota no puede estar vacío", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Save,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Guardar", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Selector de categoría
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Categoría:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        val categories = listOf("General", "Biblia", "Hebreo", "Estudio", "Reflexión", "Vocabulario", "Texto Copiado", "Texto Seleccionado")
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(categories) { cat ->
                                FilterChip(
                                    selected = (editCategoryText == cat),
                                    onClick = { editCategoryText = cat },
                                    label = { Text(cat, fontSize = 12.sp) },
                                    shape = RoundedCornerShape(8.dp)
                                )
                            }
                        }
                    }

                    // Título / Referencia
                    OutlinedTextField(
                        value = editTitleText,
                        onValueChange = { editTitleText = it },
                        label = { Text("Título / Referencia") },
                        placeholder = { Text("Ej: Raíz hebrea, Reflexión...") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        trailingIcon = {
                            if (editTitleText.isNotEmpty()) {
                                IconButton(onClick = { editTitleText = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Limpiar título")
                                }
                            }
                        }
                    )

                    // Contenido de la nota - Amplio a pantalla completa
                    OutlinedTextField(
                        value = editContentText,
                        onValueChange = { editContentText = it },
                        label = { Text("Contenido de la nota") },
                        placeholder = { Text("Escribe o edita el texto de tu nota...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        trailingIcon = {
                            Column(
                                verticalArrangement = Arrangement.Top,
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        val clip = clipboardManager.getText()?.text
                                        if (!clip.isNullOrBlank()) {
                                            editContentText = if (editContentText.isNotBlank()) "$editContentText\n$clip" else clip
                                            Toast.makeText(context, "Texto pegado", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(context, "Portapapeles vacío", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentPaste,
                                        contentDescription = "Pegar texto copiado",
                                        tint = primaryColor
                                    )
                                }
                            }
                        }
                    )

                    // Botones inferiores
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { noteToEdit = null },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) {
                            Text("Cancelar")
                        }

                        Button(
                            onClick = {
                                val finalContent = editContentText.trim()
                                if (finalContent.isNotBlank()) {
                                    viewModel.updateNote(
                                        note.copy(
                                            topicTitle = if (editTitleText.isNotBlank()) editTitleText.trim() else note.topicTitle,
                                            category = if (editCategoryText.isNotBlank()) editCategoryText else note.category,
                                            content = finalContent
                                        )
                                    )
                                    noteToEdit = null
                                    Toast.makeText(context, "Nota actualizada correctamente", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "El contenido de la nota no puede estar vacío", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.weight(1.5f),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Save,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Guardar Cambios", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // Diálogo de Creación de Nota Directa a Pantalla Completa
    if (showCreateNoteDialog) {
        Dialog(
            onDismissRequest = { showCreateNoteDialog = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = false
            )
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Column {
                                Text(
                                    text = "Nueva Nota Personal",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = newNoteCategory,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { showCreateNoteDialog = false }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Cerrar"
                                )
                            }
                        },
                        actions = {
                            Button(
                                onClick = {
                                    val finalContent = newNoteContent.trim()
                                    if (finalContent.isNotBlank()) {
                                        val title = if (newNoteTitle.isNotBlank()) newNoteTitle.trim() else "Nota Personal"
                                        viewModel.addNote(
                                            topicId = "custom_${System.currentTimeMillis()}",
                                            topicTitle = title,
                                            category = newNoteCategory,
                                            content = finalContent
                                        )
                                        showCreateNoteDialog = false
                                        newNoteTitle = ""
                                        newNoteContent = ""
                                        Toast.makeText(context, "Nota guardada con éxito", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "El contenido de la nota no puede estar vacío", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Save,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Guardar", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Selector de categoría
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "Categoría:",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        val categories = listOf("General", "Biblia", "Hebreo", "Estudio", "Reflexión", "Vocabulario", "Texto Copiado", "Texto Seleccionado")
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(categories) { cat ->
                                FilterChip(
                                    selected = (newNoteCategory == cat),
                                    onClick = { newNoteCategory = cat },
                                    label = { Text(cat, fontSize = 12.sp) },
                                    shape = RoundedCornerShape(8.dp)
                                )
                            }
                        }
                    }

                    // Título
                    OutlinedTextField(
                        value = newNoteTitle,
                        onValueChange = { newNoteTitle = it },
                        label = { Text("Título (ej: Reflexión Génesis, Letra Álef)") },
                        placeholder = { Text("Escribe un título para la nota...") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        trailingIcon = {
                            if (newNoteTitle.isNotEmpty()) {
                                IconButton(onClick = { newNoteTitle = "" }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Limpiar título")
                                }
                            }
                        }
                    )

                    // Contenido
                    OutlinedTextField(
                        value = newNoteContent,
                        onValueChange = { newNoteContent = it },
                        label = { Text("Contenido de la nota") },
                        placeholder = { Text("Escribe o pega tu nota aquí...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        trailingIcon = {
                            Column(
                                verticalArrangement = Arrangement.Top,
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        val clip = clipboardManager.getText()?.text
                                        if (!clip.isNullOrBlank()) {
                                            newNoteContent = if (newNoteContent.isNotBlank()) "$newNoteContent\n$clip" else clip
                                            Toast.makeText(context, "Texto pegado", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(context, "Portapapeles vacío", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentPaste,
                                        contentDescription = "Pegar texto copiado",
                                        tint = primaryColor
                                    )
                                }
                            }
                        }
                    )

                    // Botones inferiores
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { showCreateNoteDialog = false },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) {
                            Text("Cancelar")
                        }

                        Button(
                            onClick = {
                                val finalContent = newNoteContent.trim()
                                if (finalContent.isNotBlank()) {
                                    val title = if (newNoteTitle.isNotBlank()) newNoteTitle.trim() else "Nota Personal"
                                    viewModel.addNote(
                                        topicId = "custom_${System.currentTimeMillis()}",
                                        topicTitle = title,
                                        category = newNoteCategory,
                                        content = finalContent
                                    )
                                    showCreateNoteDialog = false
                                    newNoteTitle = ""
                                    newNoteContent = ""
                                    Toast.makeText(context, "Nota guardada con éxito", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, "El contenido de la nota no puede estar vacío", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.weight(1.5f),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Save,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Guardar Nota", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
