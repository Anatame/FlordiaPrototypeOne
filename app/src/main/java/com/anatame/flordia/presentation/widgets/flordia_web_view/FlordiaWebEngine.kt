package com.anatame.flordia.presentation.widgets.flordia_web_view

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView

class FlordiaWebEngine(
     context: Context,
     attrs: AttributeSet
): WebView(context, attrs) {

    private val flordiaWebEngineClient = FlordiaWebEngineClient()

    init{
        setUpConfig()
    }

    fun addWebRequestHandler(handler: WebRequestHandler){
        flordiaWebEngineClient.webRequestHandler = handler
    }

    fun addWebEngineEventListener(listener: WebEngineEventListener){
        flordiaWebEngineClient.webEngineEventListener = listener
    }

    private fun setUpConfig() {
        settings.userAgentString =
           "Mozilla/5.0 (Linux; Android 7.0; SM-G930V Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.125 Mobile Safari/537.36"
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.mediaPlaybackRequiresUserGesture = false
        settings.loadsImagesAutomatically = false
        settings.blockNetworkImage = true

        webViewClient = flordiaWebEngineClient
    }

}