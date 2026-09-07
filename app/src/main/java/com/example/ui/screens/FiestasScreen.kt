package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.JewishFeast
import com.example.ui.viewmodel.TorahViewModel

fun getFeastDrawableRes(id: String): Int {
    return when (id.lowercase()) {
        "shabbat" -> R.drawable.img_feast_shabbat_1786388353651
        "pesach", "chag_hamatzot", "bikkurim" -> R.drawable.img_feast_pesach_1786388369335
        "shavuot" -> R.drawable.img_feasts_banner_1785860004945
        "rosh_hashanah", "yom_kippur", "tisha_bav" -> R.drawable.img_feast_shofar_1786388381458
        "sukkot", "shemini_atzeret" -> R.drawable.img_feast_shofar_1786388381458
        "chanukah", "purim", "tu_bishvat" -> R.drawable.img_feast_menorah_1786388394476
        else -> R.drawable.img_feasts_banner_1785860004945
    }
}

fun getFeastImageUrl(id: String): String {
    return when (id.lowercase()) {
        "shabbat" -> "https://images.unsplash.com/photo-1543269865-cbf427effbad?w=800"
        "pesach" -> "https://images.unsplash.com/photo-1509440159596-0249088772ff?w=800"
        "chag_hamatzot" -> "https://images.unsplash.com/photo-1574943320219-553eb213f72d?w=800"
        "bikkurim" -> "https://images.unsplash.com/photo-1541344999736-83eca272f6fc?w=800"
        "shavuot" -> "https://images.unsplash.com/photo-1507842217343-583bb7270b66?w=800"
        "rosh_hashanah" -> "https://images.unsplash.com/photo-1601004890684-d8cbf643f5f2?w=800"
        "yom_kippur" -> "https://images.unsplash.com/photo-1544971587-b842c27f8c14?w=800"
        "sukkot" -> "https://images.unsplash.com/photo-1513836279014-a89f7a76ae86?w=800"
        "shemini_atzeret" -> "https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?w=800"
        "chanukah" -> "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=800"
        "tu_bishvat" -> "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800"
        "purim" -> "https://images.unsplash.com/photo-1461360370896-922624d12aa1?w=800"
        "tisha_bav" -> "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=800"
        else -> "https://images.unsplash.com/photo-1507842217343-583bb7270b66?w=800"
    }
}

fun getFeastGalleryImages(id: String): List<String> {
    val main = getFeastImageUrl(id)
    return when (id.lowercase()) {
        "shabbat" -> listOf(
            main,
            "https://images.unsplash.com/photo-1519817650390-64a93db51149?w=600",
            "https://images.unsplash.com/photo-1576013551627-0cc20b96c2a7?w=600"
        )
        "pesach" -> listOf(
            main,
            "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=600",
            "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=600"
        )
        "chanukah" -> listOf(
            main,
            "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=600",
            "https://images.unsplash.com/photo-1519817650390-64a93db51149?w=600"
        )
        else -> listOf(
            main,
            "https://images.unsplash.com/photo-1507842217343-583bb7270b66?w=600",
            "https://images.unsplash.com/photo-1544971587-b842c27f8c14?w=600"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiestasScreen(
    viewModel: TorahViewModel,
    onNavigateBack: () -> Unit,
    onSelectFeast: (String) -> Unit
) {
    val feasts = viewModel.feasts
    var selectedCategory by remember { mutableStateOf("Todas") }
    var searchQuery by remember { mutableStateOf("") }

    val categories = listOf("Todas", "Peregrinaje", "Altas Fiestas", "Festividades", "Semanal")

    val filteredFeasts = remember(selectedCategory, searchQuery, feasts) {
        feasts.filter { feast ->
            val matchesCategory = when (selectedCategory) {
                "Peregrinaje" -> feast.category.contains("Peregrinaje", ignoreCase = true)
                "Altas Fiestas" -> feast.category.contains("Altas", ignoreCase = true) || feast.id in listOf("rosh_hashanah", "yom_kippur")
                "Festividades" -> feast.category.contains("Histórica", ignoreCase = true) || feast.category.contains("Festividad", ignoreCase = true) || feast.id in listOf("chanukah", "purim", "tu_bishvat")
                "Semanal" -> feast.id == "shabbat"
                else -> true
            }
            val matchesSearch = searchQuery.isBlank() ||
                    feast.nameSpanish.contains(searchQuery, ignoreCase = true) ||
                    feast.nameHebrew.contains(searchQuery, ignoreCase = true) ||
                    feast.hebrewDate.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAF7F0)),
        contentPadding = PaddingValues(top = 12.dp, bottom = 90.dp, start = 14.dp, end = 14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Immersive Header Hero
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                border = BorderStroke(1.dp, Color(0xFFC79E3E)),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_feasts_banner_1785860004945),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.25f),
                                        Color(0xEE160E04)
                                    )
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = "MOADIM • TIEMPOS SEÑALADOS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD700),
                            letterSpacing = 1.5.sp
                        )
                        Text(
                            text = "Las Santas Convocaciones de la Torá",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = FontFamily.Serif
                        )
                        Text(
                            text = "Ciclos del calendario bíblico, historia redentora y su profundo significado espiritual.",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.85f),
                            maxLines = 2
                        )
                    }
                }
            }
        }

        // Search Bar estilo Pergamino
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = Color(0xFFF1EAE0),
                border = BorderStroke(1.dp, Color(0xFFDCC8A0)),
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color(0xFF9E721D),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text(
                                text = "Buscar fiesta, fecha o término...",
                                fontSize = 13.sp,
                                color = Color(0xFF8A7F6E)
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true
                    )
                }
            }
        }

        // Category Filter Chips
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isSelected) Color(0xFF9E721D) else Color(0xFFEFE8DA),
                        border = BorderStroke(1.dp, if (isSelected) Color(0xFFC79E3E) else Color(0xFFD8C8A8)),
                        modifier = Modifier.clickable { selectedCategory = cat }
                    ) {
                        Text(
                            text = cat,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else Color(0xFF3B2E1C),
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

        // Feasts Count Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredFeasts.size} Festividades encontradas",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6B5D45),
                    fontFamily = FontFamily.Serif
                )
            }
        }

        // Feasts List
        items(filteredFeasts, key = { it.id }) { feast ->
            ImmersiveFeastCard(
                feast = feast,
                onClick = { onSelectFeast(feast.id) }
            )
        }
    }
}

@Composable
private fun ImmersiveFeastCard(
    feast: JewishFeast,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accentColor = parseColor(feast.themeColorHex)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("feast_card_${feast.id}")
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF9)),
        border = BorderStroke(1.dp, Color(0xFFE8DCCB)),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column {
            // Visual Image Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(138.dp)
            ) {
                Image(
                    painter = painterResource(id = getFeastDrawableRes(feast.id)),
                    contentDescription = feast.nameSpanish,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Scrim Overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.65f)
                                )
                            )
                        )
                )

                // Header Overlay Content
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top Row: Category Chip + Hebrew Name
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = accentColor.copy(alpha = 0.9f),
                            border = BorderStroke(1.dp, Color(0xFFE5C884).copy(alpha = 0.6f)),
                            modifier = Modifier.weight(1f, fill = false)
                        ) {
                            Text(
                                text = feast.category,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = feast.nameHebrew,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Bottom Row: Spanish Name
                    Text(
                        text = feast.nameSpanish,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontFamily = FontFamily.Serif,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            // Card Body Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Date Chip
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFF1EAE0),
                        border = BorderStroke(1.dp, Color(0xFFDCC8A0)),
                        modifier = Modifier.weight(1f, fill = false)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Celebration,
                                contentDescription = null,
                                tint = Color(0xFF9E721D),
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = feast.hebrewDate,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF382705),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    // Gregorian Approx
                    Text(
                        text = "📅 ${feast.gregorianApprox}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF7A6F5D)
                    )
                }

                // Short Description / Excerpt
                Text(
                    text = feast.history,
                    fontSize = 12.sp,
                    color = Color(0xFF5A4F3E),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )

                HorizontalDivider(color = Color(0xFFE8DCCB))

                // Bottom CTA Link
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Explorar historia, mitzvot y profecía",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9E721D)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Ver detalle",
                        tint = Color(0xFF9E721D),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}


