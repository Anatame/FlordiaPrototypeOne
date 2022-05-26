package com.anatame.flordia.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anatame.flordia.databinding.ActivityMainBinding
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
            addWebEngineEventListener(object: WebEngineEventListener{
                var startTime: Long = 0
                var endTime: Long = 0

                override fun pageStarted(): String? {
                    startTime = System.currentTimeMillis()
                    Timber.d("Page Started")

                    return "js/movies.js"
                }

                override fun pageFinished(): String? {
                    endTime = System.currentTimeMillis()
                    val totalTime = endTime.minus(startTime)
                    Timber.d("Page Finished")
                    Timber.d("Total Time Taken: $totalTime")

                    return "js/movies.js"
                }

            })
        }.loadUrl("https://fmovies.to/home")

        val remote = WebEngineRemote(binding.wvFlordiaWebView)

        binding.btnSubmit.setOnClickListener {
            val searchText = binding.etSearch.text.toString()
            remote.startSearch(searchText)
        }


    }
}