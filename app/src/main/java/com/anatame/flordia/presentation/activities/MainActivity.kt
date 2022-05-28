package com.anatame.flordia.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.anatame.flordia.databinding.ActivityMainBinding
import com.anatame.flordia.domain.managers.flordia_web_view.WebEngineEventListenerImpl
import com.anatame.flordia.domain.managers.flordia_web_view.WebEngineRemote
import com.anatame.flordia.domain.managers.flordia_web_view.WebRequestHandlerImpl
import com.anatame.flordia.domain.models.MovieItem
import com.anatame.flordia.presentation.widgets.flordia_web_view.FlordiaWebEngine

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideProgress()

        val webEngine = FlordiaWebEngine(this)
        val remote = WebEngineRemote(webEngine)

        webEngine.apply {
            addWebRequestHandler(WebRequestHandlerImpl)
            addWebEngineEventListener(WebEngineEventListenerImpl(
                startFunc,
                endFunc = {
                    hideProgress()
                    remote.getHtml()
                },
                getMovieList,
                "js/movies.js"
            ))
        }.loadUrl("https://fmovies.to/")


        binding.btnSubmit.setOnClickListener {
            val searchText = binding.etSearch.text.toString()
            remote.startSearch(searchText)
        }

        binding.btnGetHtml.setOnClickListener {
            remote.getHtml()
        }
    }

    private val startFunc: () -> Unit = { showProgress() }
    // private val endFunc: () -> Unit = { hideProgress() }
    private val getMovieList: (List<MovieItem>) -> Unit = {
        viewModel.searchMovieItems.postValue(it)
    }


    private fun showProgress(){
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun hideProgress(){
        binding.progressCircular.visibility = View.INVISIBLE
    }
}