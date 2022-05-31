package com.anatame.flordia.presentation.widgets.flordia_web_view

import android.content.Context
import android.webkit.JavascriptInterface
import com.anatame.flordia.presentation.widgets.flordia_web_view.dto.MovieItems
import com.anatame.flordia.presentation.widgets.flordia_web_view.dto.SeasonsAndEpisodes
import com.anatame.flordia.presentation.widgets.flordia_web_view.dto.Servers
import com.google.gson.Gson
import timber.log.Timber

class FlordiaWebAppInterface(
    private val mContext: Context,
) {
    private val gson = Gson()
    var webEngineEventListener: WebEngineEventListener? = null

    @JavascriptInterface
    fun getHtml(html: String) {
        webEngineEventListener?.getHTML(html)
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

    }

    @JavascriptInterface
    fun getServers(data: String) {
        Timber.tag("getServersLog").d(data)
        val jsonData = gson.fromJson(data, Servers::class.java)

        jsonData.servers.forEach{
            Timber.d(it.name)
        }
    }

    @JavascriptInterface
    fun getSeasonsAndEpisodes(data: String) {
        Timber.tag("getSeasonsAndEpisodesLog").d(data)

        val jsonData = gson.fromJson(data, SeasonsAndEpisodes::class.java)

        jsonData.seasonWiseEpisodes.forEach{
           Timber.d(it.size.toString())
        }
    }
}


