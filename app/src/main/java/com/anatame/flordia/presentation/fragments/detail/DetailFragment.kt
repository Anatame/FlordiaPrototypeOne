package com.anatame.flordia.presentation.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.anatame.flordia.databinding.FragmentDetailBinding
import com.anatame.flordia.presentation.activities.MainActivity

class DetailFragment : Fragment(){

    private lateinit var binding: FragmentDetailBinding
    private val navArgs: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)

        val movieItem = navArgs.detailFragmentArgs.movieItem

        (activity as MainActivity).loadMovieDetails(movieItem.source)

        val viewModel = (activity as MainActivity).viewModel

        viewModel.embedUrl.observe(viewLifecycleOwner){url ->
            url?.let{

            }
        }


//        binding.textView.text = """
//            ${movieItem.title}
//            ${movieItem.type}
//            ${movieItem.source}
//            ${movieItem.thumbnail}
//        """.trimIndent()

        return binding.root
    }
}