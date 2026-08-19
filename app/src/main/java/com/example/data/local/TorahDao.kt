package com.example.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TorahDao {
    // Favorites
    @Query("SELECT * FROM favorites ORDER BY savedTimestamp DESC")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE itemId = :itemId)")
    fun isFavorite(itemId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE itemId = :itemId")
    suspend fun deleteFavoriteById(itemId: String)

    // Search History
    @Query("SELECT * FROM search_history ORDER BY timestamp DESC LIMIT 50")
    fun getSearchHistory(): Flow<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(history: SearchHistoryEntity)

    @Query("DELETE FROM search_history")
    suspend fun clearSearchHistory()

    // User Notes
    @Query("SELECT * FROM user_notes ORDER BY updatedAt DESC")
    fun getAllNotes(): Flow<List<UserNoteEntity>>

    @Query("SELECT * FROM user_notes WHERE topicId = :topicId")
    fun getNotesForTopic(topicId: String): Flow<List<UserNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: UserNoteEntity)

    @Query("DELETE FROM user_notes WHERE noteId = :noteId")
    suspend fun deleteNoteById(noteId: Long)
}
