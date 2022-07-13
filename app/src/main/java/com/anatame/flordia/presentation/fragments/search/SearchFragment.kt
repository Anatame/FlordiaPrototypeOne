package com.anatame.flordia.presentation.fragments.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.anatame.flordia.databinding.FragmentSearchBinding
import com.anatame.flordia.models.MovieItem
import com.anatame.flordia.models.NavArgs
import com.anatame.flordia.presentation.activities.MainActivity
import com.anatame.flordia.presentation.fragments.search.adapter.SearchScreenAdapter


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchScreenAdapter: SearchScreenAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        val viewModel = (activity as MainActivity).viewModel
        setUpSearchRecyclerView()

        viewModel.searchMovieItems.observe(viewLifecycleOwner) {
            searchScreenAdapter.differ.submitList(it)
        }

        return binding.root
    }

    private fun setUpSearchRecyclerView(){
        searchScreenAdapter = SearchScreenAdapter(requireContext())
        binding.rvSearchScreen.apply {
            adapter = searchScreenAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        searchScreenAdapter.setOnItemClickListener {movieItem, imageView ->
            onSearchItemClicked(movieItem, imageView)
        }
    }

    private fun onSearchItemClicked(movieItem: MovieItem, imageView: ImageView) {
        val navArgs = NavArgs.DetailFragment(movieItem)
        val direction = SearchFragmentDirections.actionSearchFragmentToDetailFragment(navArgs)
        findNavController().navigate(direction)
        (requireActivity() as MainActivity).loadMovieDetails(movieItem.source)
    }
}