package com.anatame.flordia.presentation.widgets.flordia_web_view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import timber.log.Timber


class FlordiaWebEngine(
     context: Context,
     attrs: AttributeSet? = null
): WebView(context, attrs) {

    private val flordiaWebEngineClient = FlordiaWebEngineClient()
    private var flordiaWebAppInterface = FlordiaWebAppInterface(this.context)

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
//        layoutParams = LayoutParams(
//            WindowManager.LayoutParams.MATCH_PARENT,
//            WindowManager.LayoutParams.MATCH_PARENT
//        )

        settings.userAgentString =
           "Mozilla/5.0 (Linux; Android 7.0; SM-G930V Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.125 Mobile Safari/537.36"
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.mediaPlaybackRequiresUserGesture = false
        settings.loadsImagesAutomatically = false
        settings.blockNetworkImage = true
        webChromeClient = object: WebChromeClient(){
            override fun onConsoleMessage(cm: ConsoleMessage): Boolean {
                // Timber.tag("TAG").d(cm.message() + " at " + cm.sourceId() + ":" + cm.lineNumber())
                return true
            }
        }

//        enableSlowDraw()
//        setWillNotDraw(false)

        // visibility = View.GONE

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