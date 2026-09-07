package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.HebrewLetter
import com.example.ui.components.AncientPictograph
import com.example.ui.viewmodel.TorahViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlefatoScreen(
    viewModel: TorahViewModel,
    onNavigateBack: () -> Unit,
    onSelectLetter: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val letters = viewModel.letters.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
        it.symbol.contains(searchQuery) ||
        it.numericValue.toString() == searchQuery ||
        it.pictographMeaning.contains(searchQuery, ignoreCase = true) ||
        it.transliteration.contains(searchQuery, ignoreCase = true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF130D06),
                        Color(0xFF1A1208),
                        Color(0xFF150E06)
                    )
                )
            )
    ) {
        // Fondo con destellos estelares dorados sutiles (Cosmos Sagrado)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val starColor = Color(0xFFFFE8A3).copy(alpha = 0.35f)
            val brightStarColor = Color(0xFFFFF7D6).copy(alpha = 0.65f)

            // Puntos estelares
            val stars = listOf(
                Offset(size.width * 0.08f, size.height * 0.12f),
                Offset(size.width * 0.92f, size.height * 0.18f),
                Offset(size.width * 0.15f, size.height * 0.38f),
                Offset(size.width * 0.88f, size.height * 0.45f),
                Offset(size.width * 0.06f, size.height * 0.68f),
                Offset(size.width * 0.94f, size.height * 0.72f),
                Offset(size.width * 0.48f, size.height * 0.88f),
                Offset(size.width * 0.82f, size.height * 0.92f),
                Offset(size.width * 0.32f, size.height * 0.15f),
                Offset(size.width * 0.68f, size.height * 0.28f)
            )

            stars.forEachIndexed { index, pos ->
                val r = if (index % 2 == 0) 1.8f else 1.2f
                drawCircle(
                    color = if (index % 3 == 0) brightStarColor else starColor,
                    radius = r,
                    center = pos
                )
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            // Barra de Búsqueda estilo Pergamino Dorado (fiel a la imagen de referencia)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFF5EFE4),
                border = BorderStroke(1.2.dp, Color(0xFFDAC7A2)),
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color(0xFF9E7B35),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text(
                                text = "Buscar letra, simbolo o valor numérico...",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF4A3E2D)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("alefato_search_input"),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = Color(0xFF261A07),
                            unfocusedTextColor = Color(0xFF261A07)
                        ),
                        singleLine = true
                    )
                }
            }

            // Grícula de 2 columnas de las 22 Letras Hebreas (Diseño idéntico a la imagen de referencia)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(start = 14.dp, end = 14.dp, bottom = 100.dp, top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(letters, key = { it.id }) { letter ->
                    IosHebrewLetterCard(
                        letter = letter,
                        onClick = { onSelectLetter(letter.id) }
                    )
                }
            }
        }
    }
}

/**
 * Tarjeta de Letra Hebrea estilo Lujo Dorado & Pergamino (Diseño idéntico a la imagen de referencia)
 */
@Composable
private fun IosHebrewLetterCard(
    letter: HebrewLetter,
    onClick: () -> Unit
) {
    val bronzeBadgeGradient = Brush.horizontalGradient(
        listOf(
            Color(0xFF9E743D),
            Color(0xFF7A5420)
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("letter_card_${letter.id}")
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF8)),
        border = BorderStroke(1.dp, Color(0xFFE4D7C2)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            // Fila Superior: Badge "Valor: X" a la izquierda e Ilustración Pictográfica a la derecha
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Badge Valor Numérico (Pill Metálica Bronce)
                Box(
                    modifier = Modifier
                        .background(bronzeBadgeGradient, RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "Valor: ${letter.numericValue}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // Ilustración Pictográfica Ancestral (Buey, Casa, Camello, Puerta, etc.)
                AncientPictograph(
                    letterId = letter.id,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.height(1.dp))

            // Letra Hebrea Central Grande
            Text(
                text = letter.symbol,
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1C160F),
                textAlign = TextAlign.Center
            )

            // Nombre de la Letra
            Text(
                text = letter.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF21180A),
                fontFamily = FontFamily.Serif,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Fonética / Transliteración
            Text(
                text = getLetterSoundSummary(letter),
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF6E6455),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(3.dp))

            // Caja inferior de significado pictográfico (Cápsula Beige Cálida)
            Surface(
                color = Color(0xFFEFE6D5),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = getLetterMeaningSummary(letter),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF382D1F),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
                    textAlign = TextAlign.Center,
                    lineHeight = 13.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Resumen fonético limpio y conciso para la tarjeta
 */
private fun getLetterSoundSummary(letter: HebrewLetter): String {
    return when (letter.id.lowercase()) {
        "alef" -> "' / Silenciosa (Vocálica)"
        "bet" -> "B / V"
        "gimel" -> "G (Gato)"
        "dalet" -> "D"
        "he" -> "H (Suave)"
        "vav" -> "V / W / O / U"
        "zayin" -> "Z"
        "chet" -> "Ch (J Gutural)"
        "tet" -> "T (Enfática)"
        "yod" -> "Y / I"
        "kaf" -> "K / J (Doble)"
        "lamed" -> "L"
        "mem" -> "M"
        "nun" -> "N"
        "samej" -> "S"
        "ayin" -> "' (Gutural profunda)"
        "pe" -> "P / F"
        "tsadi" -> "Ts (Tzadik)"
        "qof" -> "Q / K"
        "resh" -> "R"
        "shin" -> "Sh / S"
        "tav" -> "T"
        else -> letter.transliteration
    }
}

/**
 * Resumen de significado de 2 líneas exactamente como en la imagen de referencia
 */
private fun getLetterMeaningSummary(letter: HebrewLetter): String {
    return when (letter.id.lowercase()) {
        "alef" -> "Cabeza de buey,\nFuerza, Líder, Padre."
        "bet" -> "Casa, Hogar, Tienda,\nMorada interior."
        "gimel" -> "Camello, Generosidad,\nRecompensa, Elección."
        "dalet" -> "Puerta, Humildad,\nApertura, Pobreza."
        "he" -> "Ventana, Aliento,\nRevelación, Oración."
        "vav" -> "Clavo, Estaca,\nConexión, Unión."
        "zayin" -> "Espada, Cetro,\nSustento, Reposo."
        "chet" -> "Cerca, Muro,\nProtección, Vida."
        "tet" -> "Cesta, Vasija,\nBondad Oculta."
        "yod" -> "Mano, Brazo,\nCreación, Punto Divino."
        "kaf" -> "Palma de mano,\nCorona, Receptáculo."
        "lamed" -> "Vara de pastor,\nAprender, Enseñar."
        "mem" -> "Aguas vivas,\nPurificación, Sabiduría."
        "nun" -> "Pez, Semilla,\nPerpetuidad, Humildad."
        "samej" -> "Pilar, Apoyo,\nProtección divina."
        "ayin" -> "Ojo, Visión,\nDiscernimiento, Luz."
        "pe" -> "Boca, Palabra,\nAliento, Expresión."
        "tsadi" -> "Hombre justo,\nAnzuelo, Rectitud."
        "qof" -> "Detrás de la cabeza,\nSantidad, Ojo de aguja."
        "resh" -> "Cabeza, Comienzo,\nLiderazgo, Elección."
        "shin" -> "Dientes, Fuego,\nTransformación, Shalom."
        "tav" -> "Sello de pacto,\nVerdad, Consumación."
        else -> letter.pictographMeaning.replace(", ", ",\n")
    }
}
