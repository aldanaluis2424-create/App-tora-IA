package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.data.model.ImportantWord
import com.example.ui.navigation.Screen
import com.example.ui.viewmodel.TorahViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: TorahViewModel,
    onNavigate: (String) -> Unit
) {
    val wordOfTheDay = viewModel.wordOfTheDay

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
            // Hero Banner Section
            item {
                HeroBannerCard()
            }

            // Word of the Day
            item {
                WordOfTheDayCard(
                    word = wordOfTheDay,
                    onAnalyzeClick = {
                        viewModel.updateTranslationQuery(wordOfTheDay.hebrew)
                        viewModel.performTranslation(wordOfTheDay.hebrew)
                        onNavigate(Screen.Translator.route)
                    }
                )
            }

            // Section Header
            item {
                PaddingWrapper {
                    Text(
                        text = "Módulos Principales",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            // 4 Primary Module Cards
            item {
                PaddingWrapper {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        // Module 1: Alefato Hebreo
                        ModuleCard(
                            title = "1. Alefato Hebreo",
                            subtitle = "22 Letras Sagradas, Guematría y Pardes",
                            description = "Estudio detallado de cada letra: pictograma ancestral, historia, valor numérico, comentarios de Rashi y revelación mística.",
                            imageResId = R.drawable.img_alefato_banner_1785859989840,
                            icon = Icons.AutoMirrored.Filled.MenuBook,
                            accentColor = Color(0xFF9E721D),
                            testTag = "module_alefato",
                            onClick = { onNavigate(Screen.AlefatoList.route) }
                        )

                        // Module 2: Fiestas Judías
                        ModuleCard(
                            title = "2. Fiestas Judías",
                            subtitle = "Mitzvot, Costumbres y Significado Profundo",
                            description = "Explora el Shabat, Pésaj, Shavuot, Rosh Hashaná, Yom Kipur, Sukkot y Janucá con cronología, comidas y exégesis.",
                            imageResId = R.drawable.img_feasts_banner_1785860004945,
                            icon = Icons.Default.Celebration,
                            accentColor = Color(0xFF9B2226),
                            testTag = "module_feasts",
                            onClick = { onNavigate(Screen.FeastsList.route) }
                        )

                        // Module 3: Traductor Inteligente IA
                        ModuleCard(
                            title = "3. Traductor Inteligente IA",
                            subtitle = "Análisis Teológico por Gemini API",
                            description = "Escribe cualquier término hebreo o español para recibir traducción, desglose letra por letra, guematría y comentarios rabínicos.",
                            imageResId = R.drawable.img_hero_banner_1785859976011,
                            icon = Icons.Default.AutoAwesome,
                            accentColor = Color(0xFF2D6A4F),
                            testTag = "module_translator",
                            onClick = { onNavigate(Screen.Translator.route) }
                        )

                        // Module 4: Calendario Hebreo
                        ModuleCard(
                            title = "4. Calendario Hebreo",
                            subtitle = "Meses, Estaciones y Fases Lunares",
                            description = "Calculador de años hebreos (5786/5785), detección de años bisiestos y estudio completo de los 13 meses bíblicos.",
                            imageResId = R.drawable.img_calendar_banner_1785860017335,
                            icon = Icons.Default.CalendarMonth,
                            accentColor = Color(0xFF1E2A38),
                            testTag = "module_calendar",
                            onClick = { onNavigate(Screen.CalendarList.route) }
                        )
                    }
                }
            }

            // Quick Access Chips
            item {
                PaddingWrapper {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(
                            text = "Acceso Rápido a Conceptos Clave",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(vertical = 4.dp)
                        ) {
                            val quickItems = listOf(
                                "Shalom (שָׁלוֹם)" to "shalom",
                                "Emet (אֱמֶת)" to "emet",
                                "Ahavah (אַהֲבָה)" to "ahavah",
                                "Torah (תּוֹרָה)" to "torah",
                                "Ruach (רוּחַ)" to "ruach",
                                "Chesed (חֶסֶד)" to "chesed"
                            )
                            items(quickItems) { (label, query) ->
                                FilterChip(
                                    selected = false,
                                    onClick = {
                                        viewModel.updateTranslationQuery(query)
                                        viewModel.performTranslation(query)
                                        onNavigate(Screen.Translator.route)
                                    },
                                    label = { Text(label, fontWeight = FontWeight.Medium) },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    },
                                    colors = FilterChipDefaults.filterChipColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
}

@Composable
private fun PaddingWrapper(content: @Composable () -> Unit) {
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        content()
    }
}

@Composable
private fun HeroBannerCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(180.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.img_hero_banner_1785859976011),
                contentDescription = "Torah Banner",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.85f)
                            )
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Surface(
                    color = Color(0xFFB8860B),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "HERRAMIENTA ACADÉMICA Y ESPIRITUAL",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Descubre las Profundidades de la Torá",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Explora la riqueza lingüística, la guematría y el método Pardes.",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

@Composable
private fun WordOfTheDayCard(
    word: ImportantWord,
    onAnalyzeClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
        )
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.WbSunny,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "PALABRA DEL DÍA",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = 1.sp
                    )
                }
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = "Guematría: ${word.gematriaValue}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = word.hebrew,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = word.transliteration,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = word.translation,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Text(
                text = word.significance,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Button(
                onClick = onAnalyzeClick,
                modifier = Modifier
                    .align(Alignment.End)
                    .testTag("word_of_day_analyze"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Analizar con IA", fontSize = 12.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun ModuleCard(
    title: String,
    subtitle: String,
    description: String,
    imageResId: Int,
    icon: ImageVector,
    accentColor: Color,
    testTag: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                )
                            )
                        )
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = accentColor,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = accentColor
                )
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 16.sp
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Entrar al módulo",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
