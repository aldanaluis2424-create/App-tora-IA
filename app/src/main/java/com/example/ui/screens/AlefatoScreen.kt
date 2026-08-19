package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
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
import com.example.data.model.HebrewLetter
import com.example.ui.navigation.Screen
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
        it.pictographMeaning.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
            // Search Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .testTag("alefato_search_input"),
                placeholder = { Text("Buscar letra, símbolo o valor numérico...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // Letters Grid
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 145.dp),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(letters) { letter ->
                    LetterCard(
                        letter = letter,
                        onClick = { onSelectLetter(letter.id) }
                    )
                }
            }
        }
}

@Composable
private fun LetterCard(
    letter: HebrewLetter,
    onClick: () -> Unit
) {
    val accentColor = parseColor(letter.colorHex)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("letter_card_${letter.id}")
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = accentColor.copy(alpha = 0.15f),
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    Text(
                        text = "Valor: ${letter.numericValue}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = accentColor,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = letter.pictographSymbol,
                    fontSize = 18.sp
                )
            }

            Text(
                text = letter.symbol,
                fontSize = 38.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Text(
                text = letter.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )

            Text(
                text = letter.transliteration,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = letter.pictographMeaning,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(6.dp),
                    textAlign = TextAlign.Center,
                    maxLines = 2
                )
            }
        }
    }
}
