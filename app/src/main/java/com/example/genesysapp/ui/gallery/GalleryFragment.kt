package com.example.genesysapp.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.genesysapp.R
import com.example.genesysapp.data.RandomUser
import com.example.genesysapp.databinding.FragmentGalleryBinding
import com.example.genesysapp.ui.RandomUserAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val viewModule by viewModels<GalleryViewModule>()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGalleryBinding.bind(view)

        val randomUserAdapter = RandomUserAdapter(
            listener = { onItemClick(it) }
        )

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = randomUserAdapter

            swipeRefreshLayout.setOnRefreshListener {
                viewModule.onRefresh()
                swipeRefreshLayout.isRefreshing = false
            }
        }

        viewModule.randomUsers.observe(viewLifecycleOwner) { randomUsers ->
            randomUserAdapter.submitList(randomUsers)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModule.userEvents.collect { event ->
                when (event) {
                    is GalleryViewModule.UserEvent.ShowSummaryMessage -> {
                        val action =
                            InfoDialogFragmentDirections.actionGlobalInfoDialogFragment(event.msg)
                        findNavController().navigate(action)
                    }
                }
            }
        }

        setHasOptionsMenu(true)
    }

    private fun onItemClick(randomUser: RandomUser) {
        val title = "${randomUser.name.title} ${randomUser.name.first} ${randomUser.name.last}"
        val action =
            GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(randomUser, title)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sort -> {
                viewModule.onUserSort()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}