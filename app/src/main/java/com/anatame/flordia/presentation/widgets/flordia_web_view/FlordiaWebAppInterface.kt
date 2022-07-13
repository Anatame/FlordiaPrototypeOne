package com.anatame.flordia.presentation.widgets.flordia_web_view

import android.webkit.JavascriptInterface
import com.anatame.flordia.presentation.widgets.flordia_web_view.dto.MovieControls
import com.anatame.flordia.presentation.widgets.flordia_web_view.dto.MovieItems
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import timber.log.Timber

class FlordiaWebAppInterface(
    private val webEngine: FlordiaWebEngine
) {
    private val gson = Gson()

    @JavascriptInterface
    fun getHtml(html: String) {
        // webEngineEventListener?.getHTML(html)
//        Parser.getAllEpisodes(html)
//        Log.d("MovieList", Parser.getSearchItems(html).toString())
//        File(mContext.filesDir, "test.html").printWriter().use { out ->
//            out.println(html)
//        }
    }

    @JavascriptInterface
    fun activateEps(episodes: String) {
        Timber.d(episodes.toString())
    }
    @JavascriptInterface
    fun getMovieList(data: String){
        val jsonData = gson.fromJson(data, MovieItems::class.java)

        jsonData.list.forEach{
            Timber.d("""
                ${it.title}
                ${it.thumbnail}
                ${it.type}
                ${it.source}
            """.trimIndent())
        }

        when (getStatus()){
            WebEngineStatus.Status.OnBaseScreen -> {}
            WebEngineStatus.Status.OnMovieDetailsScreen -> {
//                Timber.d(jsonData.list.size.toString())
//                webEngine.webEngineEventListener?.getMovieList(jsonData.list)
            }
            WebEngineStatus.Status.OnSearchScreen -> {
                Timber.d(jsonData.list.size.toString())
                webEngine.webEngineEventListener?.getMovieList(jsonData.list)
            }
        }
    }


    @JavascriptInterface
    fun getMovieControls(data: String) {
        Timber.tag("getSeasonsAndEpisodesLog").d(data)

        val jsonData = gson.fromJson(data, MovieControls::class.java)

        jsonData.seasonWiseEpisodes.forEach{
           Timber.d(it.size.toString())
        }

        when (getStatus()){
            WebEngineStatus.Status.OnBaseScreen -> {}
            WebEngineStatus.Status.OnMovieDetailsScreen -> {
                Timber.d("BROOOOOOoo")
                webEngine.webEngineEventListener?.getMovieControls(jsonData)
            }
            WebEngineStatus.Status.OnSearchScreen -> {}
        }
    }

    @JavascriptInterface
    fun epsSelected(dataId: String){
        Timber.d(dataId)
    }

    private fun getStatus(): WebEngineStatus.Status{
        return runBlocking(Dispatchers.Main) {
            webEngine.getCurrentStatus()
        }
    }

}


