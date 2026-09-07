package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BiblicalQuote
import com.example.data.model.QuoteComment

fun parseColor(hex: String): Color {
    return try {
        Color(android.graphics.Color.parseColor(hex))
    } catch (e: Exception) {
        Color(0xFF2D6A4F)
    }
}

/**
 * Ícono vectorial dibujado de la Menorá Dorada de 7 brazos (símbolo sagrado de la Torá)
 */
@Composable
fun GoldenMenorahIcon(
    modifier: Modifier = Modifier,
    size: Dp = 28.dp,
    tint: Color = Color(0xFFC59B27)
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val goldBrush = Brush.verticalGradient(
            listOf(Color(0xFFFFDF7A), tint, Color(0xFF8B6714))
        )
        val flameColor = Color(0xFFFF9E1B)

        // Base y pedestal
        val basePath = Path().apply {
            moveTo(w * 0.25f, h * 0.95f)
            lineTo(w * 0.75f, h * 0.95f)
            lineTo(w * 0.65f, h * 0.85f)
            lineTo(w * 0.35f, h * 0.85f)
            close()
        }
        drawPath(basePath, brush = goldBrush)

        // Tronco central
        drawLine(
            brush = goldBrush,
            start = Offset(w * 0.5f, h * 0.85f),
            end = Offset(w * 0.5f, h * 0.22f),
            strokeWidth = w * 0.08f,
            cap = StrokeCap.Round
        )

        // 3 brazos curvados a cada lado
        val radii = listOf(0.16f, 0.28f, 0.40f)
        radii.forEach { r ->
            val strokeW = w * 0.055f
            val arcPath = Path().apply {
                val left = w * 0.5f - w * r
                val right = w * 0.5f + w * r
                val top = h * 0.22f + h * (r * 0.6f)
                val bottom = top + h * (r * 1.1f)
                
                moveTo(left, h * 0.22f)
                cubicTo(
                    left, bottom,
                    right, bottom,
                    right, h * 0.22f
                )
            }
            drawPath(
                path = arcPath,
                brush = goldBrush,
                style = Stroke(width = strokeW, cap = StrokeCap.Round)
            )
        }

        // 7 Llamas en la parte superior
        val flameXPositions = listOf(
            w * (0.5f - 0.40f),
            w * (0.5f - 0.28f),
            w * (0.5f - 0.16f),
            w * 0.5f,
            w * (0.5f + 0.16f),
            w * (0.5f + 0.28f),
            w * (0.5f + 0.40f)
        )
        flameXPositions.forEach { x ->
            drawCircle(
                color = flameColor,
                radius = w * 0.038f,
                center = Offset(x, h * 0.15f)
            )
            drawCircle(
                color = Color(0xFFFFF4B8),
                radius = w * 0.018f,
                center = Offset(x, h * 0.15f)
            )
        }
    }
}

@Composable
fun BadgeChip(label: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = color.copy(alpha = 0.15f)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = color,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

@Composable
fun SectionCard(
    title: String,
    icon: ImageVector,
    accentColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false)
                )
            }
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
            SelectionContainer {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    content()
                }
            }
        }
    }
}

@Composable
fun PardesItem(
    level: String,
    text: String,
    color: Color
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    Surface(
        shape = RoundedCornerShape(10.dp),
        color = color.copy(alpha = 0.1f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = level,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
                IconButton(
                    onClick = {
                        clipboardManager.setText(AnnotatedString("$level:\n$text"))
                        Toast.makeText(context, "Copiado al portapapeles", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.size(26.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copiar",
                        tint = color,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            SelectionContainer {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun CommentCard(comment: QuoteComment) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = comment.author,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f, fill = false),
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = comment.source,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    IconButton(
                        onClick = {
                            clipboardManager.setText(AnnotatedString("${comment.author} (${comment.source}):\n${comment.text}"))
                            Toast.makeText(context, "Comentario copiado", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.size(26.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copiar comentario",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            SelectionContainer {
                Text(
                    text = comment.text,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
fun BiblicalQuoteCard(
    quote: BiblicalQuote,
    accentColor: Color
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
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
                Text(
                    text = quote.reference,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = accentColor
                )
                IconButton(
                    onClick = {
                        val textToCopy = buildString {
                            appendLine(quote.reference)
                            if (quote.hebrewText.isNotBlank()) appendLine(quote.hebrewText)
                            appendLine("“${quote.translation}”")
                            if (quote.commentaryNote.isNotBlank()) appendLine(quote.commentaryNote)
                        }
                        clipboardManager.setText(AnnotatedString(textToCopy))
                        Toast.makeText(context, "Cita bíblica copiada", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.size(26.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copiar cita",
                        tint = accentColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            SelectionContainer {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    if (quote.hebrewText.isNotBlank()) {
                        Text(
                            text = quote.hebrewText,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = accentColor
                        )
                    }
                    Text(
                        text = "“${quote.translation}”",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (quote.commentaryNote.isNotBlank()) {
                        Text(
                            text = quote.commentaryNote,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

/**
 * Componente de Ventana Emergente a Pantalla Completa estilo iOS
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IosFullscreenModal(
    title: String,
    subtitle: String? = null,
    icon: ImageVector? = null,
    accentColor: Color = Color(0xFFA67C1E),
    onDismiss: () -> Unit,
    actions: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    androidx.compose.ui.window.Dialog(
        onDismissRequest = onDismiss,
        properties = androidx.compose.ui.window.DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFFAF8F4)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
            ) {
                // Barra de Navegación Superior estilo Pergamino Dorado Compacta y Espaciosa
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFF7F2E8),
                    shadowElevation = 2.dp,
                    border = BorderStroke(1.dp, Color(0xFFE2D6C0))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Botón Izquierdo: Cerrar estilo iOS
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color(0xFFEADBBE), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar",
                                tint = Color(0xFF42320D),
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        // Título y subtítulo centrado con máxima amplitud horizontal
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                if (icon != null) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        tint = accentColor,
                                        modifier = Modifier.size(17.dp)
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                }
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF24201A),
                                    fontSize = 15.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f, fill = false)
                                )
                            }
                            if (!subtitle.isNullOrBlank()) {
                                Text(
                                    text = subtitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF6E695F),
                                    fontSize = 11.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        // Acciones a la derecha o botón Listo balanceado
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            if (actions != null) {
                                actions()
                            } else {
                                TextButton(
                                    onClick = onDismiss,
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "Listo",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Color(0xFF9E6B15)
                                    )
                                }
                            }
                        }
                    }
                }

                HorizontalDivider(color = Color(0xFFE2D6C0))

                // Contenido del módulo a pantalla completa
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    content()
                }
            }
        }
    }
}

/**
 * Botón / Tarjeta de acción estilo iOS Premium Dorado con opción de ilustración temática
 */
@Composable
fun IosModuleActionTile(
    title: String,
    subtitle: String,
    icon: ImageVector? = null,
    customLeadingIcon: (@Composable () -> Unit)? = null,
    accentColor: Color = Color(0xFFA67C1E),
    badgeText: String? = null,
    decorType: String? = null, // "stars", "map", "sukkah", "harvest", "rabbi"
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val goldBorder = Color(0xFFEBE0CC)
    val goldDark = Color(0xFF946E19)
    val goldBadgeGradient = Brush.horizontalGradient(
        listOf(Color(0xFFE5C884), Color(0xFFC69C3D))
    )
    val iconBoxGradient = Brush.verticalGradient(
        listOf(Color(0xFFFAF6EC), Color(0xFFEBDCBA))
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, goldBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Icono en cajita dorada con relieve y borde
            if (customLeadingIcon != null) {
                customLeadingIcon()
            } else if (icon != null) {
                Surface(
                    shape = RoundedCornerShape(11.dp),
                    color = Color.Transparent,
                    border = BorderStroke(1.dp, Color(0xFFD8BC78)),
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(iconBoxGradient),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = goldDark,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Títulos, Insignia dorada y Subtítulo
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF22201D),
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    if (!badgeText.isNullOrBlank()) {
                        Box(
                            modifier = Modifier
                                .background(goldBadgeGradient, RoundedCornerShape(6.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = badgeText,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF382705),
                                maxLines = 1
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6E695F),
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Chevron indicador dorado
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Abrir",
                tint = Color(0xFFC59B27),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

/**
 * Control Segmentado estilo iOS (Cupertino Segmented Bar)
 */
@Composable
fun IosSegmentedControl(
    items: List<Pair<String, ImageVector?>>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    accentColor: Color = Color(0xFFA67C1E),
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFF0EBE0),
        border = BorderStroke(1.dp, Color(0xFFE2D6C0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items.forEachIndexed { index, (label, icon) ->
                val isSelected = selectedIndex == index
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onItemSelected(index) },
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) Color.White else Color.Transparent,
                    shadowElevation = if (isSelected) 3.dp else 0.dp
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (icon != null) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = if (isSelected) accentColor else Color(0xFF7A7365),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                        Text(
                            text = label,
                            fontSize = 13.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) accentColor else Color(0xFF7A7365),
                            maxLines = 1,
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}


