package com.anatame.flordia.presentation.fragments.home.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anatame.flordia.databinding.FragmentAnimeBinding

class AnimeFragment: Fragment() {
    private lateinit var binding: FragmentAnimeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAnimeBinding.inflate(layoutInflater)

        return binding.root
    }
}