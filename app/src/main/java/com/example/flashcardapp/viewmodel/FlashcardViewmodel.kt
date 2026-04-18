package com.example.flashcardapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.flashcardapp.database.FlashcardDatabase
import com.example.flashcardapp.model.Flashcard
import com.example.flashcardapp.repository.FlashcardRepository
import kotlinx.coroutines.launch

class FlashcardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FlashcardRepository
    val allFlashcards: LiveData<List<Flashcard>>
    val count: LiveData<Int>

    private val _currentIndex = MutableLiveData(0)
    val currentIndex: LiveData<Int> get() = _currentIndex

    private val _isAnswerVisible = MutableLiveData(false)
    val isAnswerVisible: LiveData<Boolean> get() = _isAnswerVisible

    private val _editingFlashcard = MutableLiveData<Flashcard?>(null)
    val editingFlashcard: LiveData<Flashcard?> get() = _editingFlashcard

    init {
        val dao = FlashcardDatabase.getDatabase(application).flashcardDao()
        repository = FlashcardRepository(dao)
        allFlashcards = repository.allFlashcards
        count = repository.count
    }

    fun insert(question: String, answer: String) = viewModelScope.launch {
        repository.insert(Flashcard(question = question.trim(), answer = answer.trim()))
    }

    fun update(id: Int, question: String, answer: String) = viewModelScope.launch {
        repository.update(Flashcard(id = id, question = question.trim(), answer = answer.trim()))
    }

    fun delete(flashcard: Flashcard) = viewModelScope.launch {
        repository.delete(flashcard)
        val size = allFlashcards.value?.size ?: 0
        if (_currentIndex.value!! >= size - 1 && _currentIndex.value!! > 0) {
            _currentIndex.value = _currentIndex.value!! - 1
        }
    }

    fun next() {
        val size = allFlashcards.value?.size ?: 0
        if (size == 0) return
        _currentIndex.value = (_currentIndex.value!! + 1) % size
        _isAnswerVisible.value = false
    }

    fun previous() {
        val size = allFlashcards.value?.size ?: 0
        if (size == 0) return
        _currentIndex.value = if (_currentIndex.value!! == 0) size - 1 else _currentIndex.value!! - 1
        _isAnswerVisible.value = false
    }

    fun toggleAnswer() {
        _isAnswerVisible.value = !(_isAnswerVisible.value ?: false)
    }

    fun resetQuiz() {
        _currentIndex.value = 0
        _isAnswerVisible.value = false
    }

    fun setEditingFlashcard(flashcard: Flashcard?) {
        _editingFlashcard.value = flashcard
    }

    fun getCurrentCard(): Flashcard? {
        val list = allFlashcards.value ?: return null
        if (list.isEmpty()) return null
        val idx = _currentIndex.value ?: 0
        return list.getOrNull(idx)
    }
}