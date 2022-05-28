package com.anatame.flordia.domain.managers.flordia_web_view

import com.anatame.flordia.domain.models.MovieItem
import com.anatame.flordia.domain.parsers.Parser
import com.anatame.flordia.presentation.widgets.flordia_web_view.WebEngineEventListener
import com.google.android.material.progressindicator.BaseProgressIndicator
import timber.log.Timber

class WebEngineEventListenerImpl(
    private val startFunc: () -> Unit,
    private val endFunc: ()-> Unit,
    private val getMovieList: (List<MovieItem>)-> Unit,
    private val script: String? = null,
): WebEngineEventListener {
    var startTime: Long = 0
    var endTime: Long = 0

    override fun pageStarted(): String? {
        startTime = System.currentTimeMillis()
        Timber.d("Page Started")
        startFunc()
        return script
    }

    override fun pageFinished(): String? {
        endTime = System.currentTimeMillis()
        val totalTime = endTime.minus(startTime)
        Timber.d("Page Finished")
        Timber.d("Total Time Taken: $totalTime")
        endFunc()
        return script
    }

    override fun getHTML(html: String) {
        val movieList = Parser.getSearchItems(html)
        Timber.d(movieList.toString())
        Timber.d(movieList.size.toString())
        getMovieList(movieList)
    }

}