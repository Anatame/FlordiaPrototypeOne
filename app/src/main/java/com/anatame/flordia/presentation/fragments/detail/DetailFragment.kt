package com.anatame.flordia.presentation.fragments.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.anatame.flordia.presentation.widgets.flordia_web_view.dto.Episode
import com.anatame.flordia.presentation.widgets.flordia_web_view.dto.Season
import timber.log.Timber

class DetailFragment : Fragment(){

    private lateinit var binding: FragmentDetailBinding
    private val navArgs: DetailFragmentArgs by navArgs()
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private val viewModel: DetailViewModel by viewModels()
    private var player: FlordiaPlayerSystem? = null
    private lateinit var episodesAdapter: EpisodesAdapter

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
                hideProgress()
            }
        }

        val serversAdapter = ServersAdapter(requireContext())
        episodesAdapter = EpisodesAdapter(requireContext())


        binding.rvServers.apply{
            adapter = serversAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.rvEpisodes.apply{
            adapter = episodesAdapter
            layoutManager = LinearLayoutManager(context)
        }

        mainActivityViewModel.movieControls.observe(viewLifecycleOwner) { controls ->
            serversAdapter.differ.submitList(controls?.servers?.filter {
                it.name == "Vidstream" || it.name == "MyCloud"
            })

            controls?.let {handleSeasonsLoaded(it.seasons, it.seasonWiseEpisodes)  }

        }

        serversAdapter.setOnItemClickListener{
            mainActivityViewModel.serverDataId.postValue(it.dataId)
            showProgress()
        }

        episodesAdapter.setOnItemClickListener{
            mainActivityViewModel.episodeDataId.postValue(it.dataId)
            showProgress()
        }

        return binding.root
    }

    private fun handleSeasonsLoaded(seasons: List<Season>, seasonWiseEpisodes: List<List<Episode>>){
        val spinner = binding.spSeasons

        Log.d("SeasonsData", seasons.toString())

        val dataAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(),  android.R.layout.simple_spinner_dropdown_item,  seasons.map {
            it.name
        })
        spinner.adapter = dataAdapter

        episodesAdapter.differ.submitList(seasonWiseEpisodes.first())

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                episodesAdapter.differ.submitList(seasonWiseEpisodes[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun showProgress() {
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progressCircular.visibility = View.INVISIBLE
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