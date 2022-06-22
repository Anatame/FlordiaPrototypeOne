package com.anatame.flordia.presentation.fragments.home.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.anatame.flordia.databinding.FragmentAnimeBinding
import com.anatame.flordia.databinding.FragmentMovieBinding
import com.anatame.flordia.presentation.fragments.home.adapter.HomeAdapter
import com.anatame.flordia.presentation.fragments.home.adapter.HomeRecyclerViewItem

class MovieFragment : Fragment() {
    private lateinit var binding: FragmentMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMovieBinding.inflate(layoutInflater)

        val homeAdapter = HomeAdapter()


        binding.rvHome.apply{
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(context)
        }

        homeAdapter.items = listOf<HomeRecyclerViewItem>(
            HomeRecyclerViewItem.HomeItemTypeOne(),
            HomeRecyclerViewItem.HomeItemTypeTwo(),
            HomeRecyclerViewItem.HomeItemTypeThree(),
            HomeRecyclerViewItem.HomeItemTypeOne(),
            HomeRecyclerViewItem.HomeItemTypeTwo(),
            HomeRecyclerViewItem.HomeItemTypeThree(),
            HomeRecyclerViewItem.HomeItemTypeOne(),
            HomeRecyclerViewItem.HomeItemTypeTwo(),
            HomeRecyclerViewItem.HomeItemTypeThree(),
            HomeRecyclerViewItem.HomeItemTypeOne(),
            HomeRecyclerViewItem.HomeItemTypeTwo(),
            HomeRecyclerViewItem.HomeItemTypeThree(),
            HomeRecyclerViewItem.HomeItemTypeOne(),
            HomeRecyclerViewItem.HomeItemTypeTwo(),
            HomeRecyclerViewItem.HomeItemTypeThree(),
        )

        return binding.root
    }
}