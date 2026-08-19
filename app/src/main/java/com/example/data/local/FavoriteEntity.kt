package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val itemId: String,              // Unique identifier (e.g., "letter_alef", "feast_pesach", "month_tishrei", or translation timestamp)
    val itemType: String,            // "LETTER", "FEAST", "MONTH", "TRANSLATION"
    val title: String,               // Display title e.g. "Alef (א)"
    val subtitle: String,            // Display subtitle e.g. "Valor 1 - Cabeza de Buey"
    val snippet: String,             // Short detail preview
    val extraDataJson: String = "",  // Full JSON string if needed
    val savedTimestamp: Long = System.currentTimeMillis()
)
