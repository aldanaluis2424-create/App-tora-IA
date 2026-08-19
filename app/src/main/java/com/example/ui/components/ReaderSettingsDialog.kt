package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.TorahViewModel

@Composable
fun ReaderSettingsDialog(
    viewModel: TorahViewModel,
    onDismiss: () -> Unit
) {
    val fontSizeSp by viewModel.fontSizeSp.collectAsState()
    val fontFamily by viewModel.fontFamily.collectAsState()
    val readerTheme by viewModel.readerTheme.collectAsState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.FormatSize,
                        contentDescription = "Ajustes de Lectura",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text("Ajustes de Lectura", style = MaterialTheme.typography.titleLarge)
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar")
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Font Size Slider
                Text(
                    text = "Tamaño de Fuente: ${fontSizeSp.toInt()} sp",
                    style = MaterialTheme.typography.labelLarge
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("A", fontSize = 12.sp)
                    Slider(
                        value = fontSizeSp,
                        onValueChange = { viewModel.setFontSize(it) },
                        valueRange = 12f..28f,
                        steps = 8,
                        modifier = Modifier.weight(1f)
                    )
                    Text("A", fontSize = 24.sp)
                }

                Divider()

                // Font Family
                Text(
                    text = "Estilo de Tipografía",
                    style = MaterialTheme.typography.labelLarge
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val families = listOf("SansSerif", "Serif", "Monospace")
                    families.forEach { family ->
                        val isSelected = fontFamily == family
                        FilterChip(
                            selected = isSelected,
                            onClick = { viewModel.setFontFamily(family) },
                            label = {
                                Text(
                                    text = when(family) {
                                        "Serif" -> "Académica"
                                        "Monospace" -> "Hebrea"
                                        else -> "Moderna"
                                    },
                                    fontFamily = when(family) {
                                        "Serif" -> FontFamily.Serif
                                        "Monospace" -> FontFamily.Monospace
                                        else -> FontFamily.SansSerif
                                    }
                                )
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Divider()

                // Theme Mode (Claro, Sepia, Oscuro)
                Text(
                    text = "Modo de Fondo de Lectura",
                    style = MaterialTheme.typography.labelLarge
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val themes = listOf(
                        Triple("Light", "Claro", Color(0xFFFAFAFA)),
                        Triple("Sepia", "Sepia", Color(0xFFFBF0D9)),
                        Triple("Dark", "Noche", Color(0xFF1E2A38))
                    )

                    themes.forEach { (key, label, color) ->
                        val isSelected = readerTheme == key
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(color)
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { viewModel.setReaderTheme(key) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                color = if (key == "Dark") Color.White else Color.Black,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Listo")
            }
        }
    )
}
