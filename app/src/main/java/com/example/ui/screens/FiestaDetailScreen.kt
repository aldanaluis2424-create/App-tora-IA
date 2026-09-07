package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.JewishFeast
import com.example.ui.components.FullscreenImageViewer
import com.example.ui.viewmodel.TorahViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiestaDetailScreen(
    feastId: String,
    viewModel: TorahViewModel,
    onNavigateBack: () -> Unit
) {
    val feast = viewModel.getFeast(feastId) ?: return
    val isFavState by viewModel.isFavorite("feast_${feast.id}").collectAsState()
    val accentColor = parseColor(feast.themeColorHex)

    var activeModalSection by remember { mutableStateOf<String?>(null) }
    var selectedFullscreenImageUrl by remember { mutableStateOf<String?>(null) }

    val moduleItems = listOf(
        Triple("history", "Historia y Significado", "Contexto histórico, origen y propósito") to (Icons.Default.History to "Histórico"),
        Triple("biblical", "Base Bíblica y Mandamiento", "Mitzvot y pasajes fundacionales") to (Icons.Default.MenuBook to "Escrituras"),
        Triple("customs", "Costumbres y Rituales", "Tradiciones, observancias y símbolos") to (Icons.Default.Festival to "Rituales"),
        Triple("foods", "Comidas y Simbolismo", "Platillos tradicionales y su significado") to (Icons.Default.Restaurant to "Comidas"),
        Triple("prayers", "Oraciones y Bendiciones", "Liturgia, brajot y cánticos") to (Icons.Default.VolunteerActivism to "Brajot"),
        Triple("rabbinic", "Comentarios Rabínicos", "Explicaciones de sabios y exégesis tradicional") to (Icons.Default.Person to "Sabios"),
        Triple("midrash", "Midrash y Cuentos", "Relatos y alegorías de la fiesta") to (Icons.Default.HistoryEdu to "Tradición"),
        Triple("kabbalah", "Kabbalah y Mística", "Secretos espirituales y dimensiones místicas") to (Icons.Default.AutoAwesome to "Mística"),
        Triple("eschatology", "Escatología y Profecía", "Cumplimiento mesiánico y tiempos finales") to (Icons.Default.Visibility to "Profético"),
        Triple("bibliography", "Fuentes y Bibliografía", "Textos originales y referencias académicas") to (Icons.Default.LibraryBooks to "Fuentes")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = feast.nameSpanish,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "${feast.nameHebrew} • ${feast.hebrewDate}",
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
                                itemId = "feast_${feast.id}",
                                itemType = "FEAST",
                                title = feast.nameSpanish,
                                subtitle = feast.hebrewDate,
                                snippet = feast.category,
                                currentlyFav = isFavState
                            )
                        },
                        modifier = Modifier.testTag("fav_feast_${feast.id}")
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
        SelectionContainer {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Hero Card with Image Backdrop estilo iOS
                item {
                    val heroImageUrl = remember(feast.id) { getFeastImageUrl(feast.id) }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(22.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                        ) {
                            Image(
                                painter = painterResource(id = getFeastDrawableRes(feast.id)),
                                contentDescription = feast.nameSpanish,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        androidx.compose.ui.graphics.Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Black.copy(alpha = 0.3f),
                                                Color.Black.copy(alpha = 0.9f)
                                            )
                                        )
                                    )
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = accentColor.copy(alpha = 0.9f),
                                        modifier = Modifier.weight(1f, fill = false)
                                    ) {
                                        Text(
                                            text = feast.category,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                            maxLines = 1,
                                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = feast.nameHebrew,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }

                                Column {
                                    Text(
                                        text = feast.nameSpanish,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        maxLines = 1,
                                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "📅 ${feast.hebrewDate} • (${feast.gregorianApprox})",
                                        fontSize = 11.sp,
                                        color = Color.White.copy(alpha = 0.9f),
                                        maxLines = 1,
                                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }

                // Encabezado de Sección estilo iOS
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 2.dp, start = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "MÓDULOS DE LA CELEBRACIÓN",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 1.sp
                        )
                    }
                }

                // Botones estilo iOS para abrir ventanas emergentes a pantalla completa
                items(moduleItems, key = { it.first.first }) { (info, iconAndBadge) ->
                    val (key, title, subtitle) = info
                    val (icon, badge) = iconAndBadge
                    IosModuleActionTile(
                        title = title,
                        subtitle = subtitle,
                        icon = icon,
                        accentColor = accentColor,
                        badgeText = badge,
                        onClick = { activeModalSection = key }
                    )
                }
            }
        }

        // Ventanas emergentes a pantalla completa estilo iOS
        activeModalSection?.let { sectionKey ->
            val modalTitle: String
            val modalIcon: ImageVector
            when (sectionKey) {
                "history" -> {
                    modalTitle = "Historia y Significado"
                    modalIcon = Icons.Default.History
                }
                "biblical" -> {
                    modalTitle = "Base Bíblica y Mandamiento"
                    modalIcon = Icons.Default.MenuBook
                }
                "customs" -> {
                    modalTitle = "Costumbres y Rituales"
                    modalIcon = Icons.Default.Festival
                }
                "foods" -> {
                    modalTitle = "Comidas y Simbolismo"
                    modalIcon = Icons.Default.Restaurant
                }
                "prayers" -> {
                    modalTitle = "Oraciones y Bendiciones"
                    modalIcon = Icons.Default.VolunteerActivism
                }
                "rabbinic" -> {
                    modalTitle = "Comentarios Rabínicos"
                    modalIcon = Icons.Default.Person
                }
                "midrash" -> {
                    modalTitle = "Midrash y Tradición"
                    modalIcon = Icons.Default.HistoryEdu
                }
                "kabbalah" -> {
                    modalTitle = "Kabbalah y Mística"
                    modalIcon = Icons.Default.AutoAwesome
                }
                "eschatology" -> {
                    modalTitle = "Escatología y Profecía"
                    modalIcon = Icons.Default.Visibility
                }
                "bibliography" -> {
                    modalTitle = "Fuentes y Bibliografía"
                    modalIcon = Icons.Default.LibraryBooks
                }
                "study" -> {
                    modalTitle = "Modo Estudio Interactivo"
                    modalIcon = Icons.Default.Psychology
                }
                else -> {
                    modalTitle = "Estudio"
                    modalIcon = Icons.Default.Celebration
                }
            }

            IosFullscreenModal(
                title = modalTitle,
                subtitle = "${feast.nameSpanish} (${feast.nameHebrew})",
                icon = modalIcon,
                accentColor = accentColor,
                onDismiss = { activeModalSection = null }
            ) {
                SelectionContainer {
                    when (sectionKey) {
                        "history" -> TabFeastHistory(feast, accentColor)
                        "biblical" -> TabFeastBiblicalBasis(feast, accentColor)
                        "customs" -> TabFeastCustoms(feast, accentColor)
                        "foods" -> TabFeastFoods(feast, accentColor)
                        "prayers" -> TabFeastPrayers(feast, accentColor)
                        "rabbinic" -> TabFeastRabbinic(feast, accentColor)
                        "midrash" -> TabFeastMidrash(feast, accentColor)
                        "kabbalah" -> TabFeastKabbalah(feast, accentColor)
                        "eschatology" -> TabFeastEschatology(feast, accentColor)
                        "bibliography" -> TabFeastBibliography(feast, accentColor)
                    }
                }
            }
        }


        FullscreenImageViewer(
            imageUrl = selectedFullscreenImageUrl,
            title = feast.nameSpanish,
            onDismiss = { selectedFullscreenImageUrl = null }
        )
    }
}

@Composable
private fun TabFeastHistory(feast: JewishFeast, accentColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Origen Histórico y Contexto", icon = Icons.Default.History, accentColor = accentColor) {
                Text(text = feast.history, style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp)
            }
        }
        item {
            SectionCard(title = "Cronología del Período Festivo", icon = Icons.Default.Timeline, accentColor = accentColor) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    feast.timelineEvents.forEach { event ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BadgeChip(label = event.dayOrPeriod, color = accentColor)
                            Column {
                                Text(text = event.eventDescription, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                                Text(text = event.spiritualSignificance, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TabFeastBiblicalBasis(feast: JewishFeast, accentColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Fundamento Bíblico", icon = Icons.Default.Book, accentColor = accentColor) {
                Text(text = "Citas Clave: ${feast.biblicalBasis}", style = MaterialTheme.typography.titleSmall, color = accentColor)
            }
        }
        items(feast.scriptureQuotes) { quote ->
            BiblicalQuoteCard(quote = quote, accentColor = accentColor)
        }
    }
}

@Composable
private fun TabFeastCustoms(feast: JewishFeast, accentColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Preceptos (Mitzvot)", icon = Icons.Default.CheckCircle, accentColor = accentColor) {
                feast.mitzvot.forEach { mitzva ->
                    Text(text = "• $mitzva", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        item {
            SectionCard(title = "Costumbres Tradicionales", icon = Icons.Default.Groups, accentColor = accentColor) {
                feast.customs.forEach { custom ->
                    Text(text = "• $custom", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
private fun TabFeastFoods(feast: JewishFeast, accentColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Alimentos y Platillos Simbólicos", icon = Icons.Default.Restaurant, accentColor = accentColor) {
                feast.traditionalFoods.forEach { food ->
                    Text(text = "🍷 $food", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
private fun TabFeastPrayers(feast: JewishFeast, accentColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Oraciones y Liturgia", icon = Icons.Default.SelfImprovement, accentColor = accentColor) {
                Text(text = "Kiddush, Shehecheyanu y Hallel de ${feast.nameSpanish}.", style = MaterialTheme.typography.titleSmall, color = accentColor)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "“Baruch Atah Adonai Eloheinu Melech HaOlam asher kideshanu bemitzvotav...”",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Bendito eres Tú, Señor nuestro Dios, Rey del Universo, que nos has santificado con tus mandamientos y nos has ordenado celebrar este tiempo santo.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun TabFeastRabbinic(feast: JewishFeast, accentColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(feast.rabbinicComments) { comment ->
            CommentCard(comment = comment)
        }
    }
}

@Composable
private fun TabFeastMidrash(feast: JewishFeast, accentColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Alegorías Midrásicas", icon = Icons.Default.AutoStories, accentColor = accentColor) {
                Text(text = feast.midrashText, style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp)
            }
        }
    }
}

@Composable
private fun TabFeastKabbalah(feast: JewishFeast, accentColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Dimensiones Cabalísticas & Sephirot", icon = Icons.Default.AutoAwesome, accentColor = accentColor) {
                Text(text = feast.kabbalahText, style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp)
            }
        }
    }
}

@Composable
private fun TabFeastEschatology(feast: JewishFeast, accentColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Cumplimiento Profético y Escatología", icon = Icons.Default.Psychology, accentColor = accentColor) {
                Text(text = "Dimensión Mesiánica:", style = MaterialTheme.typography.titleSmall, color = accentColor)
                Text(text = feast.eschatology, style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Aplicación Actual:", style = MaterialTheme.typography.titleSmall, color = accentColor)
                Text(text = feast.modernApplication, style = MaterialTheme.typography.bodyMedium, lineHeight = 20.sp)
            }
        }
    }
}

@Composable
private fun TabFeastGallery(feast: JewishFeast, accentColor: Color, onImageClick: (String) -> Unit) {
    val feastImages = remember(feast.id) {
        getFeastGalleryImages(feast.id)
    }

    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text(text = "Galería Ilustrativa de ${feast.nameSpanish}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(feastImages) { url ->
                    Card(
                        modifier = Modifier
                            .width(220.dp)
                            .height(280.dp)
                            .clickable { onImageClick(url) },
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            AsyncImage(model = url, contentDescription = feast.nameSpanish, contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
                            Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f)))
                            Text(
                                text = "${feast.nameHebrew}\n${feast.nameSpanish}",
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
private fun TabFeastBibliography(feast: JewishFeast, accentColor: Color) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            SectionCard(title = "Fuentes Bíblicas y Rabínicas", icon = Icons.Default.Source, accentColor = accentColor) {
                Text(text = "• Mishna Traité Pesachim / Yoma / Sukka", style = MaterialTheme.typography.bodyMedium)
                Text(text = "• Talmud Babilónico", style = MaterialTheme.typography.bodyMedium)
                Text(text = "• Shulchan Aruch Oraj Chayim", style = MaterialTheme.typography.bodyMedium)
                Text(text = "• Sefer HaChinuch", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
