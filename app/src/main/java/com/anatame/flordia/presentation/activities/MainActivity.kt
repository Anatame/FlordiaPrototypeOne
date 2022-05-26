package com.anatame.flordia.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anatame.flordia.databinding.ActivityMainBinding
import com.anatame.flordia.domain.managers.flordia_web_view.WebEngineEventListenerImpl
import com.anatame.flordia.domain.managers.flordia_web_view.WebEngineRemote
import com.anatame.flordia.domain.managers.flordia_web_view.WebRequestHandlerImpl
import com.anatame.flordia.presentation.widgets.flordia_web_view.WebEngineEventListener
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wvFlordiaWebView.apply {
            addWebRequestHandler(WebRequestHandlerImpl)
            addWebEngineEventListener(WebEngineEventListenerImpl("js/movies.js", "js/movies.js"))
        }.loadUrl("https://fmovies.to/home")

        val remote = WebEngineRemote(binding.wvFlordiaWebView)

        binding.btnSubmit.setOnClickListener {
            val searchText = binding.etSearch.text.toString()
            remote.startSearch(searchText)
        }

        TODO("Add viewmodel and control status and events from there")

    }
}