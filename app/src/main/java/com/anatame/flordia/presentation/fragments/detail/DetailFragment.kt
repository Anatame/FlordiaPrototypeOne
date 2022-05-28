package com.anatame.flordia.presentation.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.anatame.flordia.databinding.FragmentDetailBinding
import com.anatame.flordia.databinding.FragmentSearchBinding

class DetailFragment : Fragment(){

    private lateinit var binding: FragmentDetailBinding
    private val navArgs: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)

        val movieItems = navArgs.detailFragmentArgs.movieItem

        binding.textView.text = """
            ${movieItems.title}
            ${movieItems.type}
            ${movieItems.source}
            ${movieItems.thumbnail}
        """.trimIndent()

        return binding.root
    }
}