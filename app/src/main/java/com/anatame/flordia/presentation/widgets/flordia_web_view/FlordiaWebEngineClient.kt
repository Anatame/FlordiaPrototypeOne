package com.anatame.flordia.presentation.widgets.flordia_web_view

import android.graphics.Bitmap
import android.webkit.*
import timber.log.Timber
import java.io.IOException
import java.io.InputStream


class FlordiaWebEngineClient(
    val webEngine: FlordiaWebEngine
): WebViewClient() {

    var startTime: Long = 0
    var endTime: Long = 0

    init {
        setUpServiceWorkerHandler()
    }

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        return try {
            request?.requestHeaders?.forEach{
                Timber.tag("engInterceptedHeaders")
                    .d("${it.key}: ${it.value}")
            }
            Timber.tag("requestIntercepted").d(request?.url.toString())
             webEngine.webRequestHandler?.getWebResourceResponseForRequest(request)

        } catch(e: Exception) {
            e.printStackTrace();
            null
        }
    }

    private fun setUpServiceWorkerHandler() {
        ServiceWorkerController.getInstance().setServiceWorkerClient(
            object : ServiceWorkerClient() {
                override fun shouldInterceptRequest(request: WebResourceRequest?): WebResourceResponse? {
                    return try {
                         webEngine.webRequestHandler?.getWebResourceResponseForRequest(request)
                    } catch(e: Exception) {
                        e.printStackTrace();
                        null
                    }
                }
            }
        )
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        startTime = System.currentTimeMillis()
        val filePath = webEngine.webEngineEventListener?.pageStarted()
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)

        endTime = System.currentTimeMillis()
        Timber.d("requestStartTime: ${(endTime - startTime).toString()}")

        val filePath = webEngine.webEngineEventListener?.pageStarted()
        filePath?.let{injectScriptFile(view, it)}
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)

        val filePath = webEngine.webEngineEventListener?.pageFinished()
        filePath?.let{injectScriptFile(view, it)}
    }

    
    private fun injectScriptFile(view: WebView?, scriptFile: String) {
        val input: InputStream?
        try {
            input = view?.context?.assets?.open(scriptFile)

            // String-ify the script byte-array using BASE64 encoding !!!
            val encoded = input?.bufferedReader().use { it?.readText() }
            Timber.d(encoded.toString())
            view?.evaluateJavascript(
                encoded.toString()
            ) {}
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }

}

class DefaultWebRequestHandler: WebRequestHandler()