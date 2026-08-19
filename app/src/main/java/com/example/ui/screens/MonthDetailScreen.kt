package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.HebrewMonth
import com.example.ui.components.FullscreenImageViewer
import com.example.ui.components.StudyModeTabContent
import com.example.ui.viewmodel.TorahViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthDetailScreen(
    monthId: String,
    viewModel: TorahViewModel,
    onNavigateBack: () -> Unit
) {
    val month = viewModel.getMonth(monthId) ?: return
    val isFavState by viewModel.isFavorite("month_${month.id}").collectAsState()
    val primaryColor = MaterialTheme.colorScheme.primary

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var selectedFullscreenImageUrl by remember { mutableStateOf<String?>(null) }

    val tabs = listOf(
        "📖 Info",
        "🏛️ Historia",
        "🎉 Fiestas",
        "🌾 Cosechas",
        "👨🏻‍🏫 Rabínicos",
        "📚 Midrash",
        "🔮 Escatología",
        "🔗 Citas",
        "🛠️ Modo Estudio"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Mes: ${month.nameSpanish}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "${month.nameHebrew} • ${month.gregorianApprox}",
                            fontSize = 11.sp,
                            color = primaryColor
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
                                itemId = "month_${month.id}",
                                itemType = "MONTH",
                                title = "Mes de ${month.nameSpanish} (${month.nameHebrew})",
                                subtitle = month.gregorianApprox,
                                snippet = "Tribu: ${month.associatedTribe}",
                                currentlyFav = isFavState
                            )
                        },
                        modifier = Modifier.testTag("fav_month_${month.id}")
                    ) {
                        Icon(
                            imageVector = if (isFavState) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Favorito",
                            tint = primaryColor
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
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Hero Header
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = primaryColor.copy(alpha = 0.12f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = month.nameSpanish, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            BadgeChip(label = "Civil: #${month.monthNumberCivil}", color = primaryColor)
                            BadgeChip(label = "Religioso: #${month.monthNumberReligious}", color = MaterialTheme.colorScheme.secondary)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "📅 ${month.gregorianApprox} • Estación: ${month.season}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }

                    Text(
                        text = month.nameHebrew,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
            }

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
                    0 -> TabMonthInfo(month, primaryColor)
                    1 -> TabMonthHistory(month, primaryColor)
                    2 -> TabMonthFestivals(month, primaryColor)
                    3 -> TabMonthHarvest(month, primaryColor)
                    4 -> TabMonthRabbinic(month, primaryColor)
                    5 -> TabMonthMidrash(month, primaryColor)
                    6 -> TabMonthEschatology(month, primaryColor)
                    7 -> TabMonthQuotes(month, primaryColor)
                    8 -> StudyModeTabContent(
                        topicId = "month_${month.id}",
                        topicTitle = "Estudio sobre Mes de ${month.nameSpanish}",
                        category = "Calendario Hebreo",
                        viewModel = viewModel
                    )
                }
            }
        }

        FullscreenImageViewer(
            imageUrl = selectedFullscreenImageUrl,
            title = month.nameSpanish,
            onDismiss = { selectedFullscreenImageUrl = null }
        )
    }
}

@Composable
private fun TabMonthInfo(month: HebrewMonth, primaryColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Atributos Bíblicos y Espirituales", icon = Icons.Default.Stars, accentColor = primaryColor) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Tribu Asociada:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = primaryColor)
                        Text(text = month.associatedTribe, fontSize = 13.sp)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Letra Canalizadora:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = primaryColor)
                        Text(text = month.associatedLetter, fontSize = 13.sp)
                    }
                }
            }
        }
        item {
            SectionCard(title = "Resumen del Mes", icon = Icons.Default.Info, accentColor = primaryColor) {
                Text(text = month.historyMeaning, style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp)
            }
        }
    }
}

@Composable
private fun TabMonthHistory(month: HebrewMonth, primaryColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Acontecimientos Históricos", icon = Icons.Default.History, accentColor = primaryColor) {
                Text(text = month.historyMeaning, style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp)
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
                    Text(text = "Mes reservado para meditación, estudio y preparación espiritual.", style = MaterialTheme.typography.bodyMedium)
                } else {
                    month.festivalsInMonth.forEach { fest ->
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(vertical = 4.dp)) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = primaryColor, modifier = Modifier.size(16.dp))
                            Text(text = fest, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
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
                Text(text = month.agriculturalHarvest, style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp)
                if (month.agriculturalIcons.isNotEmpty()) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.padding(top = 8.dp)) {
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
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Comentarios de los Sabios (Sefer Yetzirah & Rashi)", icon = Icons.Default.Psychology, accentColor = primaryColor) {
                Text(text = "El mes de ${month.nameSpanish} representa un portal espiritual para la renovación de la mente y la rectificación del carácter.", style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp)
            }
        }
    }
}

@Composable
private fun TabMonthMidrash(month: HebrewMonth, primaryColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Alegorías Midrásicas", icon = Icons.Default.AutoStories, accentColor = primaryColor) {
                Text(text = month.midrashText, style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp)
            }
        }
    }
}

@Composable
private fun TabMonthEschatology(month: HebrewMonth, primaryColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Significado Escatológico y Mesiánico", icon = Icons.Default.AutoAwesome, accentColor = primaryColor) {
                Text(text = month.eschatologySignificance, style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp)
            }
        }
    }
}

@Composable
private fun TabMonthGallery(month: HebrewMonth, primaryColor: Color, onImageClick: (String) -> Unit) {
    val monthImages = remember(month.id) {
        listOf(
            "https://images.unsplash.com/photo-1507842217343-583bb7270b66?w=600",
            "https://images.unsplash.com/photo-1519817650390-64a93db51149?w=600",
            "https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?w=600"
        )
    }

    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text(text = "Galería del Mes de ${month.nameSpanish}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(monthImages) { url ->
                    Card(
                        modifier = Modifier
                            .width(220.dp)
                            .height(280.dp)
                            .clickable { onImageClick(url) },
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            AsyncImage(model = url, contentDescription = month.nameSpanish, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                            Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f)))
                            Text(
                                text = "${month.nameHebrew}\n${month.nameSpanish}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
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
