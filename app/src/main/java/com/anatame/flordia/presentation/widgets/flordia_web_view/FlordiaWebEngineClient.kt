package com.anatame.flordia.presentation.widgets.flordia_web_view

import android.annotation.TargetApi
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.webkit.*
import timber.log.Timber
import java.io.IOException
import java.io.InputStream


class FlordiaWebEngineClient(
    val webEngine: FlordiaWebEngine
): WebViewClient() {

    init {
        setUpServiceWorkerHandler()
    }

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        return try {
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
                        Timber.tag("fromServiceWorker").d(request?.url.toString())
                        Timber.tag("requestIntercepted").d(request?.url.toString())
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
        val filePath = webEngine.webEngineEventListener?.pageStarted()
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)

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