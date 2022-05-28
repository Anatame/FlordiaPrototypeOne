package com.anatame.flordia.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.anatame.flordia.databinding.ActivityMainBinding
import com.anatame.flordia.domain.managers.flordia_web_view.WebEngineEventListenerImpl
import com.anatame.flordia.domain.managers.flordia_web_view.WebEngineRemote
import com.anatame.flordia.domain.managers.flordia_web_view.WebRequestHandlerImpl

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideProgress()

        binding.wvFlordiaWebView.apply {
            addWebRequestHandler(WebRequestHandlerImpl)
            addWebEngineEventListener(WebEngineEventListenerImpl(
                startFunc,
                endFunc,
                "js/movies.js",
                "js/movies.js")
            )
        }.loadUrl("https://fmovies.to/")

        val remote = WebEngineRemote(binding.wvFlordiaWebView)

        binding.btnSubmit.setOnClickListener {
            val searchText = binding.etSearch.text.toString()
            remote.startSearch(searchText)
        }

        binding.btnGetHtml.setOnClickListener {
            remote.getHtml()
        }
    }

    private val startFunc: () -> Unit = {showProgress()}
    private val endFunc: () -> Unit = {hideProgress()}


    private fun showProgress(){
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun hideProgress(){
        binding.progressCircular.visibility = View.INVISIBLE
    }
}