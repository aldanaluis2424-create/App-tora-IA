package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val query: String,
    val translationResultJson: String,
    val gematriaValue: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_notes")
data class UserNoteEntity(
    @PrimaryKey(autoGenerate = true)
    val noteId: Long = 0,
    val topicId: String,          // e.g., "letter_alef"
    val topicTitle: String,       // e.g., "Estudio sobre Alef"
    val category: String,         // "Alefato", "Fiesta", "Calendario", "Traducción"
    val content: String,
    val updatedAt: Long = System.currentTimeMillis()
)
