package com.anatame.flordia.domain.managers.flordia_web_view

import android.webkit.WebView

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

    private fun isAtHome(): Boolean = webEngine.url.toString().contains("home")

    private fun executeJS(js: String){
        webEngine.evaluateJavascript(js.trimIndent().trimMargin()){};
    }


}