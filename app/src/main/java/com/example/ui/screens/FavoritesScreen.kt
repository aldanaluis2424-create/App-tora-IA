package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.navigation.Screen
import com.example.ui.viewmodel.TorahViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: TorahViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToRoute: (String) -> Unit
) {
    val favorites by viewModel.favorites.collectAsState()
    val notes by viewModel.userNotes.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }

    val primaryColor = MaterialTheme.colorScheme.primary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Favoritos (${favorites.size})", fontWeight = FontWeight.Bold) },
                    icon = { Icon(imageVector = Icons.Default.Bookmark, contentDescription = null) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Notas (${notes.size})", fontWeight = FontWeight.Bold) },
                    icon = { Icon(imageVector = Icons.Default.EditNote, contentDescription = null) }
                )
            }

            if (selectedTab == 0) {
                if (favorites.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Aún no has guardado elementos en favoritos. Toca el icono de marcador en cualquier letra, fiesta o mes para guardarlo aquí.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 14.sp
                        )
                    }
                } else {
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
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
                                            color = primaryColor
                                        ) {
                                            Text(
                                                text = fav.itemType,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = fav.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                        Text(text = fav.subtitle, fontSize = 12.sp, color = primaryColor)
                                        Text(text = fav.snippet, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
                                        }
                                    ) {
                                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                if (notes.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Aún no has escrito notas personales. Puedes agregar notas dentro del estudio de cada letra, fiesta o mes.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 14.sp
                        )
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(notes) { note ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = note.topicTitle, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = primaryColor)
                                        IconButton(onClick = { viewModel.deleteNote(note.noteId) }) {
                                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar nota", tint = MaterialTheme.colorScheme.error)
                                        }
                                    }
                                    Text(text = note.content, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
