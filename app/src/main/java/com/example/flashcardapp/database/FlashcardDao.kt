package com.example.flashcardapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.flashcardapp.model.Flashcard

@Dao
interface FlashcardDao {
    @Query("SELECT * FROM flashcards ORDER BY createdAt DESC")
    fun getAllFlashcards(): LiveData<List<Flashcard>>

    @Query("SELECT * FROM flashcards ORDER BY createdAt DESC")
    suspend fun getAllFlashcardsSync(): List<Flashcard>

    @Query("SELECT * FROM flashcards WHERE id = :id")
    suspend fun getFlashcardById(id: Int): Flashcard?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcard(flashcard: Flashcard)

    @Update
    suspend fun updateFlashcard(flashcard: Flashcard)

    @Delete
    suspend fun deleteFlashcard(flashcard: Flashcard)

    @Query("DELETE FROM flashcards")
    suspend fun deleteAllFlashcards()

    @Query("SELECT COUNT(*) FROM flashcards")
    fun getCount(): LiveData<Int>
}