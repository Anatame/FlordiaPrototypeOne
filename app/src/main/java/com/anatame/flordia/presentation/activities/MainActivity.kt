package com.anatame.flordia.presentation.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.anatame.flordia.R
import com.anatame.flordia.databinding.ActivityMainBinding
import com.anatame.flordia.domain.managers.flordia_web_view.WebEngineEventListenerImpl
import com.anatame.flordia.domain.managers.flordia_web_view.WebEngineRemote
import com.anatame.flordia.domain.managers.flordia_web_view.WebRequestHandlerImpl
import com.anatame.flordia.domain.models.MovieItem
import com.anatame.flordia.presentation.widgets.flordia_web_view.FlordiaWebEngine
import com.anatame.flordia.utils.Constants.BASE_URL_MOVIE
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel: MainActivityViewModel by viewModels()
    private lateinit var webEngine: FlordiaWebEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideProgress()

        webEngine = binding.visualWebEngine
        // webEngine = FlordiaWebEngine(this)
        val remote = WebEngineRemote(webEngine)
        val listener = WebEngineEventListenerImpl(
            startFunc,
            endFunc = {
                hideProgress()
                remote.getMovieList()
            },
            getMovieList,
            embedUrlDetected,
            "js/movies.js"
        )

        webEngine.apply {
            webRequestHandler = WebRequestHandlerImpl
            webEngineEventListener = listener
        }.loadUrl(BASE_URL_MOVIE + "/search?keyword=moon+knight&vrf=%2FmTFtmbuaDGqr4RtKYBwD%2BIV")
//        }.loadUrl(BASE_URL_MOVIE)


        binding.btnSubmit.setOnClickListener {
            val searchText = binding.etSearch.text.toString()
            remote.startSearch(searchText)
        }

        binding.btnGetHtml.setOnClickListener {
            remote.getHtml()
            remote.getMovieList()
        }

        binding.btnGetEpsSeas.setOnClickListener {
            if (webEngine.isVisible)
                webEngine.visibility = View.INVISIBLE
            else
                webEngine.visibility = View.VISIBLE
        }

        listener.setOnControlsFetched {
            viewModel.movieControls.postValue(it)
        }

        viewModel.serverDataId.observe(this){
            remote.selectServer(it)
        }

    }


    private val startFunc: () -> Unit = { showProgress() }

    // private val endFunc: () -> Unit = { hideProgress() }
    private val getMovieList: (List<MovieItem>) -> Unit = {
        viewModel.searchMovieItems.postValue(it)
    }

    private val embedUrlDetected: (url: String) -> Unit = {
        viewModel.embedUrl.postValue(it)
        Timber.tag("streamUrl").d(it)
    }




    private fun showProgress() {
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progressCircular.visibility = View.INVISIBLE
    }

    fun loadMovieDetails(url: String) {
        webEngine.loadUrl(BASE_URL_MOVIE + url)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_mainactivity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                refresh()
            }

            R.id.goback -> {
                goback()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun goback() {
        webEngine.goBack()
    }

    private fun refresh() {
        webEngine.reload()
    }

    override fun onPause() {
        super.onPause()
        webEngine.onPause()
    }

    override fun onResume() {
        super.onResume()
        webEngine.onResume()
    }

}

