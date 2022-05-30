package com.anatame.flordia.presentation.widgets.flordia_web_view

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import com.google.gson.Gson
import timber.log.Timber

class FlordiaWebAppInterface(
    private val mContext: Context,
) {

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
    fun getServers(serverHTML: String) {
        Timber.d(serverHTML.toString())
    }

    @JavascriptInterface
    fun getSeasonsAndEpisodes(data: String) {
        val gson = Gson()
        val jsonData = gson.fromJson(data, SeasonsAndEpisodes::class.java)

        Timber.tag("seasonEpsodesbrah").d("""JSONDATA
            ${jsonData.season}
            ${jsonData.episodes}
        """.trimIndent())
    }
}

data class SeasonsAndEpisodes(
    val season: List<Season>,
    val episodes: List<List<Episode>>
)

data class Season(
    val name: String
)

data class Episode(
    val name: String,
    val dataId: String,
    val source: String,
)


