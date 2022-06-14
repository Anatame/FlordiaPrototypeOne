package com.anatame.flordia.domain.managers.flordia_web_view

import com.anatame.flordia.presentation.widgets.flordia_web_view.WebRequestHandler
import timber.log.Timber

object WebRequestHandlerImpl: WebRequestHandler() {

    override fun currentRequestURL(url: String): Boolean {
        if (isThisStreamUrl(url)) {
            handleEmbedUrl(url)
            Timber.tag("calledForStreamUrl").d(url)
        }

        return super.currentRequestURL(url)
    }

    private fun isThisStreamUrl(url: String): Boolean {
        return StreamUrlFilter.validate(url)
    }

    private fun handleEmbedUrl(url: String) {
        webEngine?.webEngineEventListener?.embedUrlDetected(url)
        Timber.d("calledForStreamUrl")
    }

}

object StreamUrlFilter {

    private val blockedList = listOf(
        "1080",
        "1080p",
        "720",
        "720p",
        "480",
        "480p",
        "360",
        "360p",
    )

    fun validate(url: String): Boolean {
        return (url.endsWith("list.m3u8")
                || url.endsWith("playlist.m3u8")
                || url.contains("list.m3u8")
                || url.contains("playlist.m3u8")
                ) && !checkIfContainsKeyWord(url, blockedList)
    }

    private fun checkIfContainsKeyWord(url: String, list: List<String>): Boolean{
        val strArr = url.split(".", "?", "/")
        strArr.forEach {
            if(list.contains(it.trim())) {
                return true
            }
        }
        return false
    }
}