package com.example.ui.screens

import androidx.compose.animation.*
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TranslationResult
import com.example.ui.viewmodel.TranslationUiState
import com.example.ui.viewmodel.TorahViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TraductorScreen(
    viewModel: TorahViewModel,
    onNavigateBack: () -> Unit
) {
    val query by viewModel.translationQuery.collectAsState()
    val translationState by viewModel.translationState.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()

    val primaryColor = MaterialTheme.colorScheme.primary

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
            // Input Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Ingresa un término o pasaje en Hebreo o Español:",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        OutlinedTextField(
                            value = query,
                            onValueChange = { viewModel.updateTranslationQuery(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("translator_input_field"),
                            placeholder = { Text("Ejemplo: Shalom, Emet, אמת, תורה, o 'Amor'...") },
                            singleLine = true,
                            trailingIcon = {
                                if (query.isNotEmpty()) {
                                    IconButton(onClick = { viewModel.updateTranslationQuery("") }) {
                                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Limpiar")
                                    }
                                }
                            }
                        )

                        // Sample Chips
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val samples = listOf("Shalom (שָׁלוֹם)", "Emet (אֱמֶת)", "Ahavah (אַהֲבָה)", "Torah (תּוֹרָה)", "Ruach (רוּחַ)", "Chesed (חֶסֶד)")
                            items(samples) { sample ->
                                SuggestionChip(
                                    onClick = {
                                        val rawQuery = sample.split(" ")[0]
                                        viewModel.updateTranslationQuery(rawQuery)
                                        viewModel.performTranslation(rawQuery)
                                    },
                                    label = { Text(sample, fontSize = 11.sp) }
                                )
                            }
                        }

                        Button(
                            onClick = { viewModel.performTranslation() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("translator_action_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                            enabled = query.isNotBlank() && translationState !is TranslationUiState.Loading
                        ) {
                            if (translationState is TranslationUiState.Loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Analizando con Gemini IA...")
                            } else {
                                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Analizar Término Teológico")
                            }
                        }
                    }
                }
            }

            // Results Section
            when (val state = translationState) {
                is TranslationUiState.Idle -> {
                    if (searchHistory.isNotEmpty()) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "Historial Reciente de Búsquedas", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        TextButton(onClick = { viewModel.clearSearchHistory() }) {
                                            Text("Borrar", fontSize = 12.sp, color = MaterialTheme.colorScheme.error)
                                        }
                                    }
                                    searchHistory.take(5).forEach { item ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    viewModel.updateTranslationQuery(item.query)
                                                    viewModel.performTranslation(item.query)
                                                }
                                                .padding(vertical = 6.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(text = item.query, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                            Text(text = "Guematría: ${item.gematriaValue}", fontSize = 12.sp, color = primaryColor)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                is TranslationUiState.Loading -> {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(32.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                CircularProgressIndicator(color = primaryColor)
                                Text(
                                    text = "Consultando el Erudito Bíblico IA...",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Calculando la guematría, raíces hebreas y los cuatro niveles Pardes.",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                is TranslationUiState.Error -> {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Error al consultar: ${state.message}",
                                modifier = Modifier.padding(16.dp),
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
                is TranslationUiState.Success -> {
                    item {
                        TranslationResultView(result = state.result, viewModel = viewModel)
                    }
                }
            }
        }
}

@Composable
private fun TranslationResultView(
    result: TranslationResult,
    viewModel: TorahViewModel
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val isFavState by viewModel.isFavorite("trans_${result.queryText}").collectAsState()

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // Main Summary Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = primaryColor.copy(alpha = 0.12f))
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            viewModel.toggleFavorite(
                                itemId = "trans_${result.queryText}",
                                itemType = "TRANSLATION",
                                title = "${result.hebrewSquareScript} (${result.queryText})",
                                subtitle = "Traducción: ${result.mainTranslation}",
                                snippet = "Guematría: ${result.gematriaTotalValue}",
                                currentlyFav = isFavState
                            )
                        },
                        modifier = Modifier.testTag("fav_trans_${result.queryText}")
                    ) {
                        Icon(
                            imageVector = if (isFavState) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Guardar en Favoritos",
                            tint = primaryColor
                        )
                    }
                }

                Text(
                    text = result.hebrewSquareScript,
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )

                Text(
                    text = result.mainTranslation,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Pronunciación: ${result.pronunciationPhonetic}",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = primaryColor,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = "Guematría Total = ${result.gematriaTotalValue}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Letter-by-Letter Gematria Breakdown
        if (result.gematriaBreakdown.isNotEmpty()) {
            SectionCard(title = "Desglose de Guematría Letra por Letra", icon = Icons.Default.Functions, accentColor = primaryColor) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    result.gematriaBreakdown.forEach { breakdown ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = breakdown.letterSymbol, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = primaryColor)
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = breakdown.letterName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text(text = breakdown.meaningSummary, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(shape = RoundedCornerShape(6.dp), color = primaryColor) {
                                Text(
                                    text = "${breakdown.value}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Spiritual Meaning
        SectionCard(title = "Significado Teológico y Espiritual", icon = Icons.Default.Psychology, accentColor = primaryColor) {
            Text(
                text = result.spiritualMeaning,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 18.sp
            )
        }

        // Pardes 4 Levels
        CollapsibleSection(title = "Método Pardes (Peshat, Remez, Derash, Sod)", accentColor = primaryColor) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                if (result.pardesPeshat.isNotBlank()) PardesItem("Peshat (Literal)", result.pardesPeshat, Color(0xFF2D6A4F))
                if (result.pardesRemez.isNotBlank()) PardesItem("Remez (Alegórico)", result.pardesRemez, Color(0xFF1E2A38))
                if (result.pardesDerash.isNotBlank()) PardesItem("Derash (Rabínico)", result.pardesDerash, Color(0xFF9E721D))
                if (result.pardesSod.isNotBlank()) PardesItem("Sod (Místico)", result.pardesSod, Color(0xFF9B2226))
            }
        }

        // Biblical Quotes & Rabbinic Commentary
        if (result.biblicalQuotes.isNotEmpty() || result.rabbinicComments.isNotEmpty()) {
            CollapsibleSection(title = "Citas Bíblicas y Comentarios Rabínicos", accentColor = primaryColor) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    result.biblicalQuotes.forEach { quote ->
                        BiblicalQuoteCard(quote = quote, accentColor = primaryColor)
                    }
                    result.rabbinicComments.forEach { comment ->
                        CommentCard(comment = comment)
                    }
                }
            }
        }

        // Midrash & Kabbalah Insights
        if (result.midrashInsight.isNotBlank() || result.kabbalahInsight.isNotBlank()) {
            SectionCard(title = "Midrash y Kabbalah", icon = Icons.Default.AutoAwesome, accentColor = primaryColor) {
                if (result.midrashInsight.isNotBlank()) {
                    Text(text = "Midrash:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = primaryColor)
                    Text(text = result.midrashInsight, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(bottom = 6.dp))
                }
                if (result.kabbalahInsight.isNotBlank()) {
                    Text(text = "Kabbalah (Sefer Yetzirah):", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color(0xFF9B2226))
                    Text(text = result.kabbalahInsight, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
