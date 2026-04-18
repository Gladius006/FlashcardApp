package com.example.flashcardapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.flashcardapp.model.Flashcard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Flashcard::class], version = 1, exportSchema = false)
abstract class FlashcardDatabase : RoomDatabase() {

    abstract fun flashcardDao(): FlashcardDao

    companion object {
        @Volatile
        private var INSTANCE: FlashcardDatabase? = null

        fun getDatabase(context: Context): FlashcardDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlashcardDatabase::class.java,
                    "flashcard_database"
                )
                    .addCallback(FlashcardDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class FlashcardDatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    val dao = database.flashcardDao()
                    dao.insertFlashcard(Flashcard(question = "What is the capital of France?", answer = "Paris"))
                    dao.insertFlashcard(Flashcard(question = "What is 2 + 2?", answer = "4"))
                    dao.insertFlashcard(Flashcard(question = "What planet is known as the Red Planet?", answer = "Mars"))
                    dao.insertFlashcard(Flashcard(question = "Who wrote 'Romeo and Juliet'?", answer = "William Shakespeare"))
                    dao.insertFlashcard(Flashcard(question = "What is the chemical symbol for water?", answer = "H₂O"))
                }
            }
        }
    }
}