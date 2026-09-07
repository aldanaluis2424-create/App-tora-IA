package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
    val scrollState = rememberScrollState()

    val resolvedFontFamily = when(fontFamily) {
        "Serif" -> FontFamily.Serif
        "Monospace" -> FontFamily.Monospace
        else -> FontFamily.SansSerif
    }

    val previewBg = when (readerTheme) {
        "Dark" -> Color(0xFF161D28)
        "Sepia" -> Color(0xFFF5ECD8)
        else -> Color(0xFFFFFDF9)
    }
    val previewTextColor = when (readerTheme) {
        "Dark" -> Color(0xFFEBE6DC)
        "Sepia" -> Color(0xFF2E2316)
        else -> Color(0xFF1C1B18)
    }
    val previewHebrewColor = when (readerTheme) {
        "Dark" -> Color(0xFFE8C580)
        "Sepia" -> Color(0xFF9E721D)
        else -> Color(0xFF9E721D)
    }

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
                    Text(
                        text = "Ajustes de Lectura",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar")
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Live Reading Preview Box
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = previewBg,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
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
                                text = "Vista Previa en Vivo",
                                style = MaterialTheme.typography.labelSmall,
                                color = previewTextColor.copy(alpha = 0.6f)
                            )
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = previewHebrewColor.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = "${fontSizeSp.toInt()} sp • ${when(fontFamily){ "Serif" -> "Académica" "Monospace" -> "Hebrea" else -> "Moderna" }}",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = previewHebrewColor,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Text(
                            text = "בְּרֵאשִׁית בָּרָא אֱלֹהִים אֵת הַשָּׁמַיִם וְאֵת הָאָרֶץ",
                            fontSize = (fontSizeSp + 3).sp,
                            fontWeight = FontWeight.Bold,
                            color = previewHebrewColor,
                            fontFamily = resolvedFontFamily,
                            lineHeight = ((fontSizeSp + 3) * 1.3f).sp
                        )
                        Text(
                            text = "«En el principio creó Dios los cielos y la tierra. La sabiduría de la Torá es árbol de vida.»",
                            fontSize = fontSizeSp.sp,
                            color = previewTextColor,
                            fontFamily = resolvedFontFamily,
                            lineHeight = (fontSizeSp * 1.4f).sp
                        )
                    }
                }

                // Theme Mode (Claro, Sepia, Oscuro)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Fondo y Modo de Lectura",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val themes = listOf(
                            Triple("Light", "Claro", Color(0xFFFDFBF7)),
                            Triple("Sepia", "Sepia", Color(0xFFF5ECD8)),
                            Triple("Dark", "Noche", Color(0xFF161D28))
                        )

                        themes.forEach { (key, label, color) ->
                            val isSelected = readerTheme == key
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = color,
                                border = BorderStroke(
                                    width = if (isSelected) 2.5.dp else 1.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray.copy(alpha = 0.3f)
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp)
                                    .clickable { viewModel.setReaderTheme(key) }
                                    .testTag("theme_btn_$key")
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = if (key == "Dark") Color(0xFFE8C580) else Color(0xFF9E721D),
                                            modifier = Modifier.size(16.dp).padding(end = 4.dp)
                                        )
                                    }
                                    Text(
                                        text = label,
                                        color = if (key == "Dark") Color.White else Color(0xFF2C2216),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }

                HorizontalDivider()

                // Font Family Selection
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Estilo de Tipografía",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val families = listOf(
                            Pair("SansSerif", "Moderna"),
                            Pair("Serif", "Académica"),
                            Pair("Monospace", "Hebrea")
                        )
                        families.forEach { (key, label) ->
                            val isSelected = fontFamily == key
                            FilterChip(
                                selected = isSelected,
                                onClick = { viewModel.setFontFamily(key) },
                                label = {
                                    Text(
                                        text = label,
                                        fontFamily = when(key) {
                                            "Serif" -> FontFamily.Serif
                                            "Monospace" -> FontFamily.Monospace
                                            else -> FontFamily.SansSerif
                                        },
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                modifier = Modifier.weight(1f).testTag("font_family_$key")
                            )
                        }
                    }
                }

                HorizontalDivider()

                // Font Size Slider & Quick Presets
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tamaño de Fuente",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${fontSizeSp.toInt()} sp",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Quick Size Preset Chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val sizes = listOf(14f to "Chica", 16f to "Normal", 19f to "Grande", 23f to "Gigante")
                        sizes.forEach { (size, label) ->
                            val isSelected = fontSizeSp.toInt() == size.toInt()
                            SuggestionChip(
                                onClick = { viewModel.setFontSize(size) },
                                label = {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                                ),
                                border = SuggestionChipDefaults.suggestionChipBorder(
                                    enabled = true,
                                    borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                                ),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Precision Slider
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("A", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Slider(
                            value = fontSizeSp,
                            onValueChange = { viewModel.setFontSize(it) },
                            valueRange = 12f..28f,
                            steps = 7,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("font_size_slider")
                        )
                        Text("A", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = { viewModel.resetReaderSettings() },
                modifier = Modifier.testTag("reset_reader_settings")
            ) {
                Icon(
                    imageVector = Icons.Default.RestartAlt,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp).padding(end = 4.dp)
                )
                Text("Restablecer")
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                modifier = Modifier.testTag("confirm_reader_settings")
            ) {
                Text("Aplicar y Cerrar")
            }
        }
    )
}

