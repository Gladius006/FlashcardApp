package com.example.flashcardapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcardapp.databinding.ItemFlashcardBinding
import com.example.flashcardapp.model.Flashcard

class FlashcardAdapter(
    private val onEdit: (Flashcard) -> Unit,
    private val onDelete: (Flashcard) -> Unit
) : ListAdapter<Flashcard, FlashcardAdapter.FlashcardViewHolder>(DiffCallback) {

    inner class FlashcardViewHolder(private val binding: ItemFlashcardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(flashcard: Flashcard) {
            binding.tvQuestion.text = flashcard.question
            binding.tvAnswer.text = flashcard.answer
            binding.btnEdit.setOnClickListener { onEdit(flashcard) }
            binding.btnDelete.setOnClickListener { onDelete(flashcard) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashcardViewHolder {
        val binding = ItemFlashcardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlashcardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FlashcardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Flashcard>() {
        override fun areItemsTheSame(oldItem: Flashcard, newItem: Flashcard) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Flashcard, newItem: Flashcard) = oldItem == newItem
    }
}