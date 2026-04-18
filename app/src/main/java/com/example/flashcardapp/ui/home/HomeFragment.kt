package com.example.flashcardapp.ui.home

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.flashcardapp.R
import com.example.flashcardapp.databinding.FragmentHomeBinding
import com.example.flashcardapp.viewmodel.FlashcardViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FlashcardViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        setupObservers()
        setupClickListeners()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_home, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_manage -> {
                        findNavController().navigate(R.id.action_homeFragment_to_manageFragment)
                        true
                    }
                    R.id.action_shuffle -> {
                        viewModel.resetQuiz()
                        Snackbar.make(binding.root, "Deck reset!", Snackbar.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupObservers() {
        viewModel.allFlashcards.observe(viewLifecycleOwner) { cards ->
            if (cards.isNullOrEmpty()) showEmptyState()
            else { showCardState(); updateCard() }
        }
        viewModel.currentIndex.observe(viewLifecycleOwner) { updateCard() }
        viewModel.isAnswerVisible.observe(viewLifecycleOwner) { updateAnswerVisibility(it) }
    }

    private fun setupClickListeners() {
        binding.btnShowAnswer.setOnClickListener { viewModel.toggleAnswer() }
        binding.btnNext.setOnClickListener { viewModel.next() }
        binding.btnPrevious.setOnClickListener { viewModel.previous() }
        binding.fabAdd.setOnClickListener {
            viewModel.setEditingFlashcard(null)
            findNavController().navigate(R.id.action_homeFragment_to_addEditFragment)
        }
        binding.btnEditCurrent.setOnClickListener {
            val card = viewModel.getCurrentCard() ?: return@setOnClickListener
            viewModel.setEditingFlashcard(card)
            findNavController().navigate(R.id.action_homeFragment_to_addEditFragment)
        }
        binding.btnDeleteCurrent.setOnClickListener {
            val card = viewModel.getCurrentCard() ?: return@setOnClickListener
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Delete Card")
                .setMessage("Are you sure you want to delete this flashcard?")
                .setPositiveButton("Delete") { _, _ ->
                    viewModel.delete(card)
                    Snackbar.make(binding.root, "Card deleted", Snackbar.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun updateCard() {
        val cards = viewModel.allFlashcards.value ?: return
        if (cards.isEmpty()) return
        val index = viewModel.currentIndex.value ?: 0
        val card = cards.getOrNull(index) ?: return
        binding.tvQuestion.text = card.question
        binding.tvAnswer.text = card.answer
        binding.tvCounter.text = "${index + 1} / ${cards.size}"
        updateAnswerVisibility(viewModel.isAnswerVisible.value ?: false)
    }

    private fun updateAnswerVisibility(visible: Boolean) {
        if (visible) {
            binding.tvAnswer.visibility = View.VISIBLE
            binding.tvAnswerLabel.visibility = View.VISIBLE
            binding.btnShowAnswer.text = getString(R.string.hide_answer)
        } else {
            binding.tvAnswer.visibility = View.INVISIBLE
            binding.tvAnswerLabel.visibility = View.INVISIBLE
            binding.btnShowAnswer.text = getString(R.string.show_answer)
        }
    }

    private fun showEmptyState() {
        binding.layoutCard.visibility = View.GONE
        binding.layoutNavigation.visibility = View.GONE
        binding.layoutEmptyState.visibility = View.VISIBLE
    }

    private fun showCardState() {
        binding.layoutCard.visibility = View.VISIBLE
        binding.layoutNavigation.visibility = View.VISIBLE
        binding.layoutEmptyState.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}