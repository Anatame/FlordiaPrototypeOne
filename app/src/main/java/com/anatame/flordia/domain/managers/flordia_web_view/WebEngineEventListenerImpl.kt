package com.anatame.flordia.domain.managers.flordia_web_view

import android.webkit.WebResourceError
import com.anatame.flordia.domain.models.MovieItem
import com.anatame.flordia.domain.models.MovieType
import com.anatame.flordia.presentation.widgets.flordia_web_view.WebEngineEventListener
import com.anatame.flordia.presentation.widgets.flordia_web_view.dto.MItem
import com.anatame.flordia.presentation.widgets.flordia_web_view.dto.MovieControls
import timber.log.Timber

class WebEngineEventListenerImpl(
    private val startFunc: () -> Unit,
    private val endFunc: ()-> Unit,
    private val getMovieList: (List<MovieItem>)-> Unit,
    private val getEmbedUrl: (String)-> Unit,
    private val errorHappened: (errorDescription: String?) -> Unit,
    private val script: String? = null,
): WebEngineEventListener {
    var startTime: Long = 0
    var endTime: Long = 0

    private var onControlsFetched: ((MovieControls)-> Unit)? = null

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
//        val movieList = Parser.getSearchItems(html)
//        Timber.d(movieList.toString())
//        Timber.d(movieList.size.toString())
//        getMovieList(movieList)
    }

    override fun getMovieList(list: List<MItem>?) {
        val movieList: ArrayList<MovieItem> = ArrayList()
        list?.forEach {
            movieList.add(
                MovieItem(
                    it.thumbnail,
                    it.title,
                    if(it.type == "TV") MovieType.TV else MovieType.MOVIE,
                    it.source
                )
            )
        }

        getMovieList(movieList)
    }

    override fun getMovieControls(controls: MovieControls) {
        Timber.d(controls.toString())
        onControlsFetched?.let { it(controls) }
    }

    override fun embedUrlDetected(url: String) {
        getEmbedUrl(url)
        Timber.d("calledForStreamUrl")
    }

    override fun onError(errorDescription: String?) {
        errorHappened(errorDescription)
    }

    fun setOnControlsFetched(listener: (MovieControls) -> Unit){
        onControlsFetched = listener
    }

}