package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF140F08),
                        Color(0xFF1F150B),
                        Color(0xFF140F08)
                    )
                )
            )
    ) {
        // Fondo decorativo con puntos estelares dorados tenues
        Canvas(modifier = Modifier.fillMaxSize()) {
            val starColor = Color(0xFFFFE8A3).copy(alpha = 0.35f)
            val stars = listOf(
                Offset(size.width * 0.15f, size.height * 0.08f),
                Offset(size.width * 0.85f, size.height * 0.12f),
                Offset(size.width * 0.70f, size.height * 0.28f),
                Offset(size.width * 0.25f, size.height * 0.45f),
                Offset(size.width * 0.90f, size.height * 0.55f),
                Offset(size.width * 0.10f, size.height * 0.75f),
                Offset(size.width * 0.80f, size.height * 0.88f),
                Offset(size.width * 0.40f, size.height * 0.92f)
            )
            stars.forEach { pos ->
                drawCircle(starColor, 2f, pos)
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp, start = 14.dp, end = 14.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
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

            // Section Header: "Módulos Principales" estilo Dorado Serif
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Módulos Principales",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFE8CA82),
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // 4 Primary Module Cards con diseño iOS Premium (idéntico a la imagen 1)
            item {
                Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                    // Module 1: Alefato Hebreo
                    IosMainModuleCard(
                        pillNumber = "1. Alefato Hebreo",
                        title = "22 Letras Sagradas, Guematria y Pardes",
                        description = "Estudio detallado de cada letra, pictograma ancestral, historia, valor numérico, comentarios de Rashi y revelación mística.",
                        imageResId = R.drawable.img_alefato_banner_1785859989840,
                        pillBgColor = Color(0xFF4A3416),
                        testTag = "module_alefato",
                        onClick = { onNavigate(Screen.AlefatoList.route) }
                    )

                    // Module 2: Fiestas Judías
                    IosMainModuleCard(
                        pillNumber = "2. Fiestas Judías",
                        title = "Mitzvot, Costumbres y Significado Profundo",
                        description = "Explora el Shabat, Pésaj, Shavuot, Rosh Hashaná, Yom Kipur, Sukkot y Janucá con cronología, comidas y exégesis.",
                        imageResId = R.drawable.img_feasts_banner_1785860004945,
                        pillBgColor = Color(0xFF5E1B1B),
                        testTag = "module_feasts",
                        onClick = { onNavigate(Screen.FeastsList.route) }
                    )

                    // Module 3: Calendario Hebreo
                    IosMainModuleCard(
                        pillNumber = "3. Calendario Hebreo",
                        title = "Meses Bíblicos, Luni-Solar y Fases Lunares",
                        description = "Descubre los 12 meses, Rosh Jódesh, cosechas en Israel, tribus correspondientes y orden cronológico divino.",
                        imageResId = R.drawable.img_calendar_banner_1785860017335,
                        pillBgColor = Color(0xFF1E2D3D),
                        testTag = "module_calendar",
                        onClick = { onNavigate(Screen.CalendarList.route) }
                    )

                    // Module 4: Rabí
                    IosMainModuleCard(
                        pillNumber = "4. Módulo Rabí (Estudio & Guematría)",
                        title = "Chat con Rabí, Traductor y Guematría",
                        description = "Consulta a un sabio rabino con IA sobre Torá, Talmud, Zóhar, PaRDeS y análisis lingüístico con Sefaria y Wikipedia.",
                        imageResId = R.drawable.img_hero_banner_1785859976011,
                        pillBgColor = Color(0xFF1B3D2B),
                        testTag = "module_translator",
                        onClick = { onNavigate(Screen.Translator.route) }
                    )
                }
            }

            // Quick Access Chips
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = "Acceso Rápido a Conceptos Clave",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFDCC183),
                        fontFamily = FontFamily.Serif
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
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = Color(0xFF281E12),
                                border = BorderStroke(1.dp, Color(0xFF9E7B35)),
                                modifier = Modifier.clickable {
                                    viewModel.updateTranslationQuery(query)
                                    viewModel.performTranslation(query)
                                    onNavigate(Screen.Translator.route)
                                }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = null,
                                        tint = Color(0xFFFFD978),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = label,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFFF7ECD5)
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

@Composable
private fun HeroBannerCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(1.dp, Color(0xFFC79E3E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
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
                                Color(0x99140E04),
                                Color(0xF2140E04)
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
                    color = Color.White,
                    fontFamily = FontFamily.Serif
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
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF6EC)),
        border = BorderStroke(1.dp, Color(0xFFE2D1A8)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
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
                        tint = Color(0xFF9E721D),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "PALABRA DEL DÍA",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9E721D),
                        letterSpacing = 1.sp
                    )
                }
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFC79E3E)
                ) {
                    Text(
                        text = "Guematría: ${word.gematriaValue}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF241804),
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
                    color = Color(0xFF2C1E03)
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = word.transliteration,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2C1E03),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = word.translation,
                        fontSize = 12.sp,
                        color = Color(0xFF6B5D45),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Text(
                text = word.significance,
                fontSize = 12.sp,
                color = Color(0xFF5A4F3E),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.Transparent,
                    modifier = Modifier.clickable(onClick = onAnalyzeClick)
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color(0xFFB58728), Color(0xFF8B6416))
                                ),
                                RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Analizar con IA",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Tarjeta de Módulo Principal iOS (diseño fiel a imagen 1)
 */
@Composable
private fun IosMainModuleCard(
    pillNumber: String,
    title: String,
    description: String,
    imageResId: Int,
    pillBgColor: Color,
    testTag: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag)
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE2D6C0)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Imagen superior con banner y pill tag
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Overlay oscuro sutil
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.55f)
                                )
                            )
                        )
                )

                // Pill en esquina superior izquierda
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = pillBgColor.copy(alpha = 0.92f),
                    border = BorderStroke(1.dp, Color(0xFFE5C884).copy(alpha = 0.6f)),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        GoldenMenorahIcon(size = 13.dp)
                        Text(
                            text = pillNumber,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFF6DF)
                        )
                    }
                }
            }

            // Cuerpo blanco con tipografía y botón dorado
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF261D0A),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color(0xFF6B6354),
                    lineHeight = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color(0xFFB58728), Color(0xFF8B6416))
                                ),
                                RoundedCornerShape(14.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Entrar al módulo",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

