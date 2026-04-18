package com.example.flashcardapp.ui.add_edit

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.flashcardapp.R
import com.example.flashcardapp.databinding.FragmentAddEditBinding
import com.example.flashcardapp.viewmodel.FlashcardViewModel
import com.google.android.material.snackbar.Snackbar

class AddEditFragment : Fragment() {

    private var _binding: FragmentAddEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FlashcardViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editingCard = viewModel.editingFlashcard.value

        if (editingCard != null) {
            requireActivity().title = getString(R.string.edit_card)
            binding.etQuestion.setText(editingCard.question)
            binding.etAnswer.setText(editingCard.answer)
            binding.btnSave.text = getString(R.string.update_card)
        } else {
            requireActivity().title = getString(R.string.add_card)
            binding.btnSave.text = getString(R.string.save_card)
        }

        binding.btnSave.setOnClickListener {
            val question = binding.etQuestion.text.toString().trim()
            val answer = binding.etAnswer.text.toString().trim()

            if (question.isEmpty()) {
                binding.tilQuestion.error = "Question cannot be empty"
                return@setOnClickListener
            } else binding.tilQuestion.error = null

            if (answer.isEmpty()) {
                binding.tilAnswer.error = "Answer cannot be empty"
                return@setOnClickListener
            } else binding.tilAnswer.error = null

            if (editingCard != null) {
                viewModel.update(editingCard.id, question, answer)
                Snackbar.make(binding.root, "Card updated!", Snackbar.LENGTH_SHORT).show()
            } else {
                viewModel.insert(question, answer)
                Snackbar.make(binding.root, "Card added!", Snackbar.LENGTH_SHORT).show()
            }
            findNavController().navigateUp()
        }

        binding.btnCancel.setOnClickListener { findNavController().navigateUp() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}