package com.anatame.flordia.presentation.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.anatame.exoplayer.widgets.player.FlordiaPlayerSystem
import com.anatame.flordia.databinding.FragmentDetailBinding
import com.anatame.flordia.presentation.activities.MainActivity
import com.anatame.flordia.presentation.activities.MainActivityViewModel
import timber.log.Timber

class DetailFragment : Fragment(){

    private lateinit var binding: FragmentDetailBinding
    private val navArgs: DetailFragmentArgs by navArgs()
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private val viewModel: DetailViewModel by viewModels()
    private var player: FlordiaPlayerSystem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        player = binding.flordiaPlayer

        mainActivityViewModel = (activity as MainActivity).viewModel

        mainActivityViewModel.embedUrl.observe(viewLifecycleOwner){ url ->
            url?.let{
                binding.textView.text = it
                player?.playVideo(url, requireActivity())
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        player?.resume(viewModel.currentPlaybackPos)
        Timber.tag("detailLifecycle").d("resumeCalled")
    }

    override fun onPause() {
        super.onPause()
        player?.stop()?.let {
            viewModel.currentPlaybackPos = it
        }
        Timber.tag("detailLifecycle").d("paused")
    }

    override fun onStop() {
        super.onStop()
        player?.stop()?.let {
            viewModel.currentPlaybackPos = it
        }
        Timber.tag("detailLifecycle").d("stopped")
    }


    override fun onDestroy() {
        super.onDestroy()
        player?.destroy()?.let {
            viewModel.currentPlaybackPos = it
        }
        Timber.tag("playerLifeCycle").d("destroyed")
    }
}