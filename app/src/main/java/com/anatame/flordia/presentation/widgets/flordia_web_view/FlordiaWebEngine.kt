package com.anatame.flordia.presentation.widgets.flordia_web_view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import android.webkit.WebView

class FlordiaWebEngine(
     context: Context,
     attrs: AttributeSet? = null
): WebView(context, attrs) {

    private val flordiaWebEngineClient = FlordiaWebEngineClient()
    private var flordiaWebAppInterface = FlordiaWebAppInterface(this.context, )

    init{
        setUpConfig()
    }

    fun addWebRequestHandler(handler: WebRequestHandler){
        flordiaWebEngineClient.webRequestHandler = handler
    }

    fun addWebEngineEventListener(listener: WebEngineEventListener){
        flordiaWebEngineClient.webEngineEventListener = listener
        flordiaWebAppInterface.webEngineEventListener = listener
    }

    private fun setUpConfig() {
        layoutParams = LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        settings.userAgentString =
           "Mozilla/5.0 (Linux; Android 7.0; SM-G930V Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.125 Mobile Safari/537.36"
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.mediaPlaybackRequiresUserGesture = false
        settings.loadsImagesAutomatically = false
        settings.blockNetworkImage = true

        enableSlowDraw()
        setWillNotDraw(false)

        visibility = View.GONE

        addJavascriptInterface(
            flordiaWebAppInterface,
            "Android")

        webViewClient = flordiaWebEngineClient
    }

    companion object {
        fun enableSlowDraw(){
            enableSlowWholeDocumentDraw()
        }
    }

}