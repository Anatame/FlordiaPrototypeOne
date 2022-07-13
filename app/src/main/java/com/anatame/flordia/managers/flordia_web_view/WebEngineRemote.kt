package com.anatame.flordia.managers.flordia_web_view

import android.webkit.WebView
import timber.log.Timber

class WebEngineRemote (
    private val webEngine: WebView
) {
    fun startSearch(query: String){
        if(isAtHome())
            executeJS( "startSearchFromHome('${query}');")
        else
            executeJS( "startSearchOthers('${query}');")
    }

    fun getHtml() {
        executeJS("getHtml()")
    }

    fun getServers(){
        executeJS("getServers()")
    }

    fun getMovieControls() {
        executeJS("getMovieControls()")
    }

    fun getMovieList(){
        executeJS("getMovieList()")
    }

    fun selectServer(dataId: String?) {
        executeJS("selectServer($dataId)")
    }

    fun selectEpisode(dataId: String?) {
        Timber.d("called for $dataId")
        executeJS("selectEpisode('$dataId')")
    }

    private fun isAtHome(): Boolean = webEngine.url.toString().contains("home")

    private fun executeJS(js: String){
        webEngine.evaluateJavascript(js.trimIndent().trimMargin()){};
    }

}