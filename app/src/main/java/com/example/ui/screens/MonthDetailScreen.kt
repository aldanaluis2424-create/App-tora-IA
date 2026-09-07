package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.HebrewMonth
import com.example.ui.components.*
import com.example.ui.viewmodel.TorahViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthDetailScreen(
    monthId: String,
    viewModel: TorahViewModel,
    onNavigateBack: () -> Unit
) {
    val allMonths = viewModel.months
    var currentMonthId by remember { mutableStateOf(monthId) }
    val currentMonthIndex = remember(currentMonthId, allMonths) {
        val idx = allMonths.indexOfFirst { it.id.equals(currentMonthId, ignoreCase = true) }
        if (idx >= 0) idx else 0
    }
    val currentMonth = allMonths.getOrNull(currentMonthIndex) ?: allMonths.first()

    val isFavState by viewModel.isFavorite("month_${currentMonth.id}").collectAsState()
    var activeModalSection by remember { mutableStateOf<String?>(null) }
    var showMonthDropdown by remember { mutableStateOf(false) }

    // Lista de módulos del mes
    val moduleList = listOf(
        MonthModuleItem(
            id = "info",
            title = "Atributos y Resumen",
            subtitle = "Tribu, letra canalizadora y significado",
            badgeText = "General",
            type = MonthModuleType.ATTRIBUTES
        ),
        MonthModuleItem(
            id = "history",
            title = "Historia y Contexto",
            subtitle = "Acontecimientos históricos bíblicos",
            badgeText = "Histórico",
            type = MonthModuleType.HISTORY
        ),
        MonthModuleItem(
            id = "festivals",
            title = "Festividades del Mes",
            subtitle = "Celebraciones, convocaciones y shabbatot",
            badgeText = "Fiestas",
            type = MonthModuleType.FESTIVALS
        ),
        MonthModuleItem(
            id = "harvest",
            title = "Cosechas y Agricultura",
            subtitle = "Ciclo agrícola, lluvias y clima en Israel",
            badgeText = "Agrícola",
            type = MonthModuleType.HARVEST
        ),
        MonthModuleItem(
            id = "rabbinic",
            title = "Comentarios Rabínicos",
            subtitle = "Explicaciones de sabios y fuentes tradicionales",
            badgeText = "Sabios",
            type = MonthModuleType.RABBINIC
        ),
        MonthModuleItem(
            id = "midrash",
            title = "Midrash y Tradición",
            subtitle = "Relatos alegóricos y secretos del Midrash",
            badgeText = "Midrash",
            type = MonthModuleType.MIDRASH
        ),
        MonthModuleItem(
            id = "eschatology",
            title = "Escatología y Profecía",
            subtitle = "Conexiones mesiánicas y cumplimiento futuro",
            badgeText = "Profético",
            type = MonthModuleType.ESCHATOLOGY
        ),
        MonthModuleItem(
            id = "quotes",
            title = "Citas Bíblicas",
            subtitle = "Pasajes de las Escrituras donde se menciona",
            badgeText = "Escrituras",
            type = MonthModuleType.QUOTES
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F5EC))
    ) {
        // Fondo sutil de pergamino
        ParchmentWatermarkBackground()

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFF9F5EC)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = onNavigateBack) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Volver",
                                    tint = Color(0xFF6B4815)
                                )
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Column {
                                Text(
                                    text = "Meses Hebreos",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF261A07),
                                    fontFamily = FontFamily.Serif
                                )
                                Text(
                                    text = "${currentMonth.nameSpanish} (${currentMonth.nameHebrew})",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF8A6C3A)
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Selector de mes desplegable
                            Box {
                                IconButton(onClick = { showMonthDropdown = true }) {
                                    Icon(
                                        imageVector = Icons.Default.SwapHoriz,
                                        contentDescription = "Cambiar Mes",
                                        tint = Color(0xFF9E6B15),
                                        modifier = Modifier.size(26.dp)
                                    )
                                }

                                DropdownMenu(
                                    expanded = showMonthDropdown,
                                    onDismissRequest = { showMonthDropdown = false },
                                    modifier = Modifier.background(Color(0xFFFAF7F0))
                                ) {
                                    allMonths.forEachIndexed { idx, m ->
                                        DropdownMenuItem(
                                            text = {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = "${idx + 1}. ${m.nameSpanish}",
                                                        fontWeight = if (m.id == currentMonth.id) FontWeight.Bold else FontWeight.Normal,
                                                        color = if (m.id == currentMonth.id) Color(0xFF9E6B15) else Color(0xFF261A07)
                                                    )
                                                    Spacer(modifier = Modifier.width(12.dp))
                                                    Text(
                                                        text = m.nameHebrew,
                                                        color = Color(0xFF8A6C3A),
                                                        fontFamily = FontFamily.Serif
                                                    )
                                                }
                                            },
                                            onClick = {
                                                currentMonthId = m.id
                                                showMonthDropdown = false
                                            }
                                        )
                                    }
                                }
                            }

                            // Botón de Favorito
                            IconButton(
                                onClick = {
                                    viewModel.toggleFavorite(
                                        itemId = "month_${currentMonth.id}",
                                        itemType = "MONTH",
                                        title = "Mes de ${currentMonth.nameSpanish} (${currentMonth.nameHebrew})",
                                        subtitle = currentMonth.gregorianApprox,
                                        snippet = "Tribu: ${currentMonth.associatedTribe}",
                                        currentlyFav = isFavState
                                    )
                                },
                                modifier = Modifier.testTag("fav_month_${currentMonth.id}")
                            ) {
                                Icon(
                                    imageVector = if (isFavState) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = "Guardar",
                                    tint = Color(0xFF9E6B15),
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                    }
                }
            }
        ) { paddingValues ->
            SelectionContainer {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 90.dp, top = 6.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // 1. Tarjeta Header Superior del Mes (Estilo Marfil con Borde Dorado Suave y Selector)
                    item {
                        MonthHeroIvoryCard(
                            month = currentMonth,
                            onPreviousMonth = {
                                val prevIdx = if (currentMonthIndex > 0) currentMonthIndex - 1 else allMonths.size - 1
                                currentMonthId = allMonths[prevIdx].id
                            },
                            onNextMonth = {
                                val nextIdx = if (currentMonthIndex < allMonths.size - 1) currentMonthIndex + 1 else 0
                                currentMonthId = allMonths[nextIdx].id
                            },
                            onOpenSelector = { showMonthDropdown = true }
                        )
                    }

                    // 2. Encabezado de Sección: "MÓDULOS TEMÁTICOS"
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp, bottom = 2.dp, start = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "MÓDULOS DE ESTUDIO DEL MES",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF8C6527),
                                letterSpacing = 1.sp,
                                fontFamily = FontFamily.Serif
                            )
                        }
                    }

                    // 3. Tarjetas de Módulos Limpias, Nítidas con Icono 3D a la Izquierda y Badges Sólidos
                    items(moduleList, key = { it.id }) { item ->
                        MonthModuleCrispCard(
                            item = item,
                            currentMonth = currentMonth,
                            onClick = { activeModalSection = item.id }
                        )
                    }
                }
            }
        }

        // Modal a Pantalla Completa para el módulo activo
        activeModalSection?.let { sectionKey ->
            val activeItem = moduleList.find { it.id == sectionKey } ?: moduleList.first()

            IosFullscreenModal(
                title = activeItem.title,
                subtitle = "Mes de ${currentMonth.nameSpanish} (${currentMonth.nameHebrew})",
                icon = Icons.Default.CalendarMonth,
                accentColor = Color(0xFF9E6B15),
                onDismiss = { activeModalSection = null }
            ) {
                SelectionContainer {
                    when (sectionKey) {
                        "info" -> TabMonthInfo(currentMonth, Color(0xFF9E6B15))
                        "history" -> TabMonthHistory(currentMonth, Color(0xFF9E6B15))
                        "festivals" -> TabMonthFestivals(currentMonth, Color(0xFF9E6B15))
                        "harvest" -> TabMonthHarvest(currentMonth, Color(0xFF9E6B15))
                        "rabbinic" -> TabMonthRabbinic(currentMonth, Color(0xFF9E6B15))
                        "midrash" -> TabMonthMidrash(currentMonth, Color(0xFF9E6B15))
                        "eschatology" -> TabMonthEschatology(currentMonth, Color(0xFF9E6B15))
                        "quotes" -> TabMonthQuotes(currentMonth, Color(0xFF9E6B15))
                    }
                }
            }
        }
    }
}

/**
 * Header del Mes estilo Marfil con Borde Suave Dorado (#E5D3B3) e interactividad total
 */
@Composable
private fun MonthHeroIvoryCard(
    month: HebrewMonth,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onOpenSelector: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("month_hero_card_${month.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE5D3B3)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Fila de Navegación de Meses con Flechas y Selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onPreviousMonth,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "Mes anterior",
                        tint = Color(0xFF9E6B15),
                        modifier = Modifier.size(20.dp)
                    )
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFF9F4EC),
                    border = BorderStroke(1.dp, Color(0xFFE5D3B3)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 6.dp)
                        .clickable { onOpenSelector() }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = month.nameSpanish,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF261A07),
                            fontFamily = FontFamily.Serif,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "(${month.nameHebrew})",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9E6B15),
                            fontFamily = FontFamily.Serif,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Seleccionar",
                            tint = Color(0xFF9E6B15),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                IconButton(
                    onClick = onNextMonth,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Mes siguiente",
                        tint = Color(0xFF9E6B15),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            HorizontalDivider(color = Color(0xFFE5D3B3).copy(alpha = 0.5f))

            // Badges compactos: Año Civil y Año Religioso + Fechas y Estación
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFF6B4815)
                    ) {
                        Text(
                            text = "#${month.monthNumberCivil} Civil",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                            maxLines = 1
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFFC59B27)
                    ) {
                        Text(
                            text = "#${month.monthNumberReligious} Rel.",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp),
                            maxLines = 1
                        )
                    }
                }

                Text(
                    text = "📅 ${month.gregorianApprox}",
                    fontSize = 11.sp,
                    color = Color(0xFF5A4A32),
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Sello de Tribu y Letra Canalizadora en formato barra compacta
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFF9F4EC),
                border = BorderStroke(1.dp, Color(0xFFE8DBCA)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Tribu: ${month.associatedTribe}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF4A3716),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Letra: ${getMonthHebrewLetter(month.id)}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9E6B15),
                            fontFamily = FontFamily.Serif
                        )
                        Text(
                            text = "• ${month.season}",
                            fontSize = 11.sp,
                            color = Color(0xFF7A684C),
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}

/**
 * Tarjeta de Módulo 100% Limpia, Nítida e Interactiva:
 * - Fondo blanco puro (#FFFFFF)
 * - Borde suave dorado (#E5D3B3)
 * - Sombra sutil
 * - Iconos temáticos 3D a la izquierda en contenedor circular dorado claro (#F9F4EC)
 * - Badges en cápsulas doradas sólidas (#C59B27) con texto blanco ultra nítido
 * - NO imágenes de fondo que opaquen el texto
 */
@Composable
private fun MonthModuleCrispCard(
    item: MonthModuleItem,
    currentMonth: HebrewMonth,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("month_module_${item.id}")
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE5D3B3)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Contenedor circular dorado claro (#F9F4EC) con icono temático
            Surface(
                shape = CircleShape,
                color = Color(0xFFF9F4EC),
                border = BorderStroke(1.dp, Color(0xFFE8DBCA)),
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    when (item.type) {
                        MonthModuleType.ATTRIBUTES -> Golden3DStarIcon()
                        MonthModuleType.HISTORY -> ScrollAndHourglassIcon()
                        MonthModuleType.FESTIVALS -> MonthFestivalDynamicIcon(monthId = currentMonth.id)
                        MonthModuleType.HARVEST -> MonthHarvestDynamicIcon(monthId = currentMonth.id)
                        MonthModuleType.RABBINIC -> RabbinicBookIcon()
                        MonthModuleType.MIDRASH -> MidrashScrollIcon()
                        MonthModuleType.ESCHATOLOGY -> PropheticCrownIcon()
                        MonthModuleType.QUOTES -> TabletsOfTorahIcon()
                        MonthModuleType.STUDY -> StudyAiSparkleIcon()
                    }
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Contenido de Textos Central
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
                        text = item.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF261A07),
                        fontFamily = FontFamily.Serif,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    // Badge (Cápsula dorada sólida #C59B27 con texto blanco ultra nítido)
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFC59B27)
                    ) {
                        Text(
                            text = item.badgeText,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            maxLines = 1
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = item.subtitle,
                    fontSize = 11.sp,
                    color = Color(0xFF6B583E),
                    lineHeight = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Flecha indicadora de navegación en tono dorado
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "Abrir módulo",
                tint = Color(0xFFC59B27),
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
private fun ParchmentWatermarkBackground() {
    // Fondo estático y ligero para máximo rendimiento y fluidez
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7F2))
    )
}

// -------------------------------------------------------------
// HELPERS TEMÁTICOS PARA CADA MES
// -------------------------------------------------------------

private fun getMonthHebrewLetter(monthId: String): String {
    return when (monthId.lowercase()) {
        "tishrei" -> "ת"
        "cheshvan" -> "נ"
        "kislev" -> "ס"
        "tevet" -> "ע"
        "shevat" -> "צ"
        "adar", "adar1", "adar2" -> "ק"
        "nisan" -> "ה"
        "iyyar" -> "ו"
        "sivan" -> "ז"
        "tammuz" -> "ח"
        "av" -> "ט"
        "elul" -> "י"
        else -> "ת"
    }
}

// -------------------------------------------------------------
// MODELOS Y CONTENIDOS DE LAS PESTAÑAS (MODALES COMPLETOS)
// -------------------------------------------------------------

enum class MonthModuleType {
    ATTRIBUTES, HISTORY, FESTIVALS, HARVEST, RABBINIC, MIDRASH, ESCHATOLOGY, QUOTES, STUDY
}

data class MonthModuleItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val badgeText: String,
    val type: MonthModuleType
)

@Composable
private fun TabMonthInfo(month: HebrewMonth, primaryColor: Color) {
    LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            SectionCard(title = "Atributos Bíblicos y Espirituales", icon = Icons.Default.Stars, accentColor = primaryColor) {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = primaryColor.copy(alpha = 0.08f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(text = "Tribu Asociada:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = primaryColor)
                            Text(text = month.associatedTribe, fontSize = 13.sp, color = Color(0xFF261A07))
                        }
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = primaryColor.copy(alpha = 0.08f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(text = "Letra Canalizadora:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = primaryColor)
                            Text(text = month.associatedLetter, fontSize = 13.sp, color = Color(0xFF261A07))
                        }
                    }
                }
            }
        }
        item {
            SectionCard(title = "Resumen del Mes", icon = Icons.Default.Info, accentColor = primaryColor) {
                Text(text = month.historyMeaning, style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp, color = Color(0xFF261A07))
            }
        }
    }
}

@Composable
private fun TabMonthHistory(month: HebrewMonth, primaryColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Acontecimientos Históricos Bíblicos", icon = Icons.Default.History, accentColor = primaryColor) {
                Text(text = month.historyMeaning, style = MaterialTheme.typography.bodyMedium, lineHeight = 22.sp, color = Color(0xFF261A07))
            }
        }
    }
}

@Composable
private fun TabMonthFestivals(month: HebrewMonth, primaryColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Convocaciones Santas en este Mes", icon = Icons.Default.Celebration, accentColor = primaryColor) {
                if (month.festivalsInMonth.isEmpty()) {
                    Text(text = "Mes reservado para meditación, estudio y preparación espiritual.", style = MaterialTheme.typography.bodyMedium, color = Color(0xFF261A07))
                } else {
                    month.festivalsInMonth.forEach { fest ->
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(vertical = 4.dp)) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = primaryColor, modifier = Modifier.size(18.dp))
                            Text(text = fest, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Color(0xFF261A07))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TabMonthHarvest(month: HebrewMonth, primaryColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Estación Agrícola y Cosechas en Israel", icon = Icons.Default.Agriculture, accentColor = primaryColor) {
                Text(text = month.agriculturalHarvest, style = MaterialTheme.typography.bodyMedium, lineHeight = 22.sp, color = Color(0xFF261A07))
                if (month.agriculturalIcons.isNotEmpty()) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.padding(top = 10.dp)) {
                        month.agriculturalIcons.forEach { icon ->
                            Text(text = icon, fontSize = 28.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TabMonthRabbinic(month: HebrewMonth, primaryColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            SectionCard(title = "Comentarios de los Sabios (Mishná, Talmud & Zóhar)", icon = Icons.Default.Psychology, accentColor = primaryColor) {
                Text(text = "Enseñanzas rabínicas sobre el mes de ${month.nameSpanish}, sus influencias celestiales y rectificación espiritual.", style = MaterialTheme.typography.bodyMedium, lineHeight = 22.sp, color = Color(0xFF261A07))
            }
        }
        items(month.rabbinicComments) { comment ->
            CommentCard(comment = comment)
        }
    }
}

@Composable
private fun TabMonthMidrash(month: HebrewMonth, primaryColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Alegorías Midrásicas", icon = Icons.Default.AutoStories, accentColor = primaryColor) {
                Text(text = month.midrashText, style = MaterialTheme.typography.bodyMedium, lineHeight = 22.sp, color = Color(0xFF261A07))
            }
        }
    }
}

@Composable
private fun TabMonthEschatology(month: HebrewMonth, primaryColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Significado Escatológico y Mesiánico", icon = Icons.Default.AutoAwesome, accentColor = primaryColor) {
                Text(text = month.eschatologySignificance, style = MaterialTheme.typography.bodyMedium, lineHeight = 22.sp, color = Color(0xFF261A07))
            }
        }
    }
}

@Composable
private fun TabMonthQuotes(month: HebrewMonth, primaryColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(month.biblicalQuotes) { quote ->
            BiblicalQuoteCard(quote = quote, accentColor = primaryColor)
        }
    }
}
