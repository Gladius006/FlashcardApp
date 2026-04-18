package com.example.flashcardapp.repository

import androidx.lifecycle.LiveData
import com.example.flashcardapp.database.FlashcardDao
import com.example.flashcardapp.model.Flashcard

class FlashcardRepository(private val dao: FlashcardDao) {
    val allFlashcards: LiveData<List<Flashcard>> = dao.getAllFlashcards()
    val count: LiveData<Int> = dao.getCount()

    suspend fun getAllSync(): List<Flashcard> = dao.getAllFlashcardsSync()
    suspend fun getById(id: Int): Flashcard? = dao.getFlashcardById(id)
    suspend fun insert(flashcard: Flashcard) = dao.insertFlashcard(flashcard)
    suspend fun update(flashcard: Flashcard) = dao.updateFlashcard(flashcard)
    suspend fun delete(flashcard: Flashcard) = dao.deleteFlashcard(flashcard)
    suspend fun deleteAll() = dao.deleteAllFlashcards()
}