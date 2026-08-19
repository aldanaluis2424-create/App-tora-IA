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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.BiblicalQuote
import com.example.data.model.HebrewLetter
import com.example.data.model.QuoteComment
import com.example.ui.components.FullscreenImageViewer
import com.example.ui.components.StudyModeTabContent
import com.example.ui.viewmodel.TorahViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LetterDetailScreen(
    letterId: String,
    viewModel: TorahViewModel,
    onNavigateBack: () -> Unit
) {
    val letter = viewModel.getLetter(letterId) ?: return
    val isFavState by viewModel.isFavorite("letter_${letter.id}").collectAsState()
    val accentColor = parseColor(letter.colorHex)

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var selectedFullscreenImageUrl by remember { mutableStateOf<String?>(null) }

    val tabs = listOf(
        "📖 Info",
        "🔢 Gematría",
        "🌳 Pardés",
        "📜 Rabínicos",
        "📚 Citas",
        "🕎 Midrash",
        "✨ Kabbalah",
        "⭐ Curiosidades",
        "🛠️ Modo Estudio"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Letra ${letter.name} (${letter.symbol})",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "Valor: ${letter.numericValue} • Transliteración: ${letter.transliteration}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.primary
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
                                itemId = "letter_${letter.id}",
                                itemType = "LETTER",
                                title = "Letra ${letter.name} (${letter.symbol})",
                                subtitle = "Valor Numérico: ${letter.numericValue}",
                                snippet = letter.pictographMeaning,
                                currentlyFav = isFavState
                            )
                        },
                        modifier = Modifier.testTag("fav_letter_${letter.id}")
                    ) {
                        Icon(
                            imageVector = if (isFavState) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Favorito",
                            tint = accentColor
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
            // Hero Banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = accentColor.copy(alpha = 0.12f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${letter.name} (${letter.nameHebrew})",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = letter.pictographMeaning,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            BadgeChip(label = "Gematría: ${letter.numericValue}", color = accentColor)
                            BadgeChip(label = "Símbolo: ${letter.pictographSymbol}", color = MaterialTheme.colorScheme.secondary)
                        }
                    }

                    Text(
                        text = letter.symbol,
                        fontSize = 54.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor,
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
                    0 -> TabInfoGeneral(letter, accentColor)
                    1 -> TabGematria(letter, accentColor)
                    2 -> TabPardes(letter, accentColor)
                    3 -> TabRabbinicComments(letter, accentColor)
                    4 -> TabBiblicalQuotes(letter, accentColor)
                    5 -> TabMidrashTalmud(letter, accentColor)
                    6 -> TabKabbalah(letter, accentColor)
                    7 -> TabCuriosities(letter, accentColor)
                    8 -> StudyModeTabContent(
                        topicId = "letter_${letter.id}",
                        topicTitle = "Estudio sobre ${letter.name} (${letter.symbol})",
                        category = "Alefato",
                        viewModel = viewModel
                    )
                }
            }
        }

        // Fullscreen Image Viewer Modal
        FullscreenImageViewer(
            imageUrl = selectedFullscreenImageUrl,
            title = "Letra ${letter.name}",
            onDismiss = { selectedFullscreenImageUrl = null }
        )
    }
}

@Composable
private fun TabInfoGeneral(letter: HebrewLetter, accentColor: Color) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionCard(title = "Significado Pictográfico y Origen", icon = Icons.Default.Info, accentColor = accentColor) {
                Text(
                    text = letter.pictographMeaning,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp
                )
            }
        }
        item {
            SectionCard(title = "Evolución e Historia Visual", icon = Icons.Default.History, accentColor = accentColor) {
                Text(
                    text = letter.visualComparison,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = accentColor,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Text(
                    text = letter.originEvolution,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp
                )
            }
        }
        item {
            SectionCard(title = "Aplicación Práctica", icon = Icons.Default.Psychology, accentColor = accentColor) {
                Text(text = "Enseñanza Espiritual:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelLarge)
                Text(text = letter.spiritualApplication, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(bottom = 8.dp))
                Text(text = "Instrucción Práctica:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelLarge)
                Text(text = letter.practicalApplication, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun TabGematria(letter: HebrewLetter, accentColor: Color) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionCard(title = "Valor Numérico (${letter.numericValue})", icon = Icons.Default.Functions, accentColor = accentColor) {
                Text(
                    text = letter.gematriaExplanation,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp
                )
            }
        }
        item {
            SectionCard(title = "Palabras Clave con el Mismo Valor", icon = Icons.Default.Translate, accentColor = accentColor) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    letter.importantWords.forEach { word ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${word.hebrew} (${word.transliteration})",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = word.translation, style = MaterialTheme.typography.bodySmall)
                                Text(text = word.significance, style = MaterialTheme.typography.labelSmall, color = accentColor)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            BadgeChip(label = "Val: ${word.gematriaValue}", color = accentColor)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TabPardes(letter: HebrewLetter, accentColor: Color) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            PardesItem(level = "Peshat (Literal)", text = letter.pardesPeshat, color = Color(0xFF2D6A4F))
        }
        item {
            PardesItem(level = "Remez (Alegórico / Insinuado)", text = letter.pardesRemez, color = Color(0xFF1E2A38))
        }
        item {
            PardesItem(level = "Derash (Rabínico / Homilético)", text = letter.pardesDerash, color = Color(0xFF9E721D))
        }
        item {
            PardesItem(level = "Sod (Místico / Secreto)", text = letter.pardesSod, color = Color(0xFF9B2226))
        }
    }
}

@Composable
private fun TabRabbinicComments(letter: HebrewLetter, accentColor: Color) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(letter.rabbinicComments) { comment ->
            CommentCard(comment = comment)
        }
    }
}

@Composable
private fun TabBiblicalQuotes(letter: HebrewLetter, accentColor: Color) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(letter.biblicalQuotes) { quote ->
            BiblicalQuoteCard(quote = quote, accentColor = accentColor)
        }
    }
}

@Composable
private fun TabMidrashTalmud(letter: HebrewLetter, accentColor: Color) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionCard(title = "Talmud Babilónico", icon = Icons.Default.MenuBook, accentColor = accentColor) {
                Text(text = letter.talmudReferences, style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp)
            }
        }
        item {
            SectionCard(title = "Midrash Rabbah", icon = Icons.Default.AutoStories, accentColor = accentColor) {
                Text(text = letter.midrashReferences, style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp)
            }
        }
    }
}

@Composable
private fun TabKabbalah(letter: HebrewLetter, accentColor: Color) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionCard(title = "Misterios de la Kabbalah & Sefer Yetzirah", icon = Icons.Default.AutoAwesome, accentColor = accentColor) {
                Text(text = letter.kabbalahMeaning, style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp)
            }
        }
    }
}

@Composable
private fun TabGallery(
    letter: HebrewLetter,
    accentColor: Color,
    onImageClick: (String) -> Unit
) {
    // Generate 3 decorative calligraphy/ancient images based on letter
    val galleryImages = remember(letter.id) {
        listOf(
            "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=600",
            "https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?w=600",
            "https://images.unsplash.com/photo-1507842217343-583bb7270b66?w=600"
        )
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Galería de Caligrafía y Representación Artística (Pulsa para ampliar)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(galleryImages) { url ->
                    Card(
                        modifier = Modifier
                            .width(220.dp)
                            .height(280.dp)
                            .clickable { onImageClick(url) },
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            AsyncImage(
                                model = url,
                                contentDescription = "Arte de ${letter.name}",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black.copy(alpha = 0.3f))
                            )
                            Text(
                                text = "${letter.symbol}\n${letter.name}",
                                fontSize = 32.sp,
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
private fun TabCuriosities(letter: HebrewLetter, accentColor: Color) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionCard(title = "Curiosidades y Secretos Gramaticales", icon = Icons.Default.Lightbulb, accentColor = accentColor) {
                Text(
                    text = "• Forma Sofit: Algunas letras hebreas cambian su forma cuando aparecen al final de una palabra.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "• Coronetas (Tagim): En los rollos de la Torá manuscritos por escribas (Sofrym), letras especiales llevan pequeñas coronas dibujadas que contienen misterios celestiales.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "• Sonido Gramatical: ${letter.transliteration} posee una vibración articular única dentro del sistema fonético semítico.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
