package com.anatame.flordia.presentation.widgets.flordia_web_view

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
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
}