package com.example.flashcardapp.ui.home

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flashcardapp.R
import com.example.flashcardapp.adapter.FlashcardAdapter
import com.example.flashcardapp.databinding.FragmentManageBinding
import com.example.flashcardapp.model.Flashcard
import com.example.flashcardapp.viewmodel.FlashcardViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ManageFragment : Fragment() {

    private var _binding: FragmentManageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FlashcardViewModel by activityViewModels()
    private lateinit var adapter: FlashcardAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentManageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupMenu()
        setupObservers()
        binding.fabAdd.setOnClickListener {
            viewModel.setEditingFlashcard(null)
            findNavController().navigate(R.id.action_manageFragment_to_addEditFragment)
        }
    }

    private fun setupRecyclerView() {
        adapter = FlashcardAdapter(
            onEdit = { card ->
                viewModel.setEditingFlashcard(card)
                findNavController().navigate(R.id.action_manageFragment_to_addEditFragment)
            },
            onDelete = { card -> confirmDelete(card) }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_manage, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_delete_all -> { confirmDeleteAll(); true }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupObservers() {
        viewModel.allFlashcards.observe(viewLifecycleOwner) { cards ->
            adapter.submitList(cards)
            if (cards.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.layoutEmptyState.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.layoutEmptyState.visibility = View.GONE
            }
        }
    }

    private fun confirmDelete(card: Flashcard) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Card")
            .setMessage("Delete \"${card.question}\"?")
            .setPositiveButton("Delete") { _, _ ->
                viewModel.delete(card)
                Snackbar.make(binding.root, "Card deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") { viewModel.insert(card.question, card.answer) }
                    .show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun confirmDeleteAll() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete All Cards")
            .setMessage("This will permanently delete all flashcards. Continue?")
            .setPositiveButton("Delete All") { _, _ ->
                Snackbar.make(binding.root, "All cards deleted", Snackbar.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}