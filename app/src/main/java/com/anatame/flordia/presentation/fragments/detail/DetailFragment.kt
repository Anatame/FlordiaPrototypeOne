package com.anatame.flordia.presentation.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.anatame.flordia.presentation.widgets.flordia_player.FlordiaPlayerSystem
import com.anatame.flordia.databinding.FragmentDetailBinding
import com.anatame.flordia.presentation.activities.MainActivity
import com.anatame.flordia.presentation.activities.MainActivityViewModel
import com.anatame.flordia.presentation.fragments.detail.adapter.EpisodesAdapter
import com.anatame.flordia.presentation.fragments.detail.adapter.ServersAdapter
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
                Timber.tag("playVideoCallledFromDetail").d(url)
                player?.playVideo(url, requireActivity())
            }
        }

        val serversAdapter = ServersAdapter(requireContext())
        val episodesAdapter = EpisodesAdapter(requireContext())

        binding.rvServers.apply{
            adapter = serversAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.rvEpisodes.apply{
            adapter = episodesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        mainActivityViewModel.movieControls.observe(viewLifecycleOwner) {
            serversAdapter.differ.submitList(it?.servers?.filter {
                it.name == "Vidstream" || it.name == "MyCloud"
            })
            episodesAdapter.differ.submitList(it?.seasonWiseEpisodes?.first())
        }

        serversAdapter.setOnItemClickListener{
            mainActivityViewModel.serverDataId.postValue(it.dataId)
        }

        episodesAdapter.setOnItemClickListener{
            mainActivityViewModel.episodeDataId.postValue(it.dataId)
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