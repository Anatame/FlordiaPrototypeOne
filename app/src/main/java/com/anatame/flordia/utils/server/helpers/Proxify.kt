package com.anatame.flordia.utils.server.helpers

import android.annotation.SuppressLint
import android.net.http.SslError
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.webkit.ProxyConfig
import androidx.webkit.ProxyController
import androidx.webkit.WebViewFeature
import com.anatame.flordia.utils.server.Server
import com.anatame.flordia.utils.server.ServerAddress
import java.util.concurrent.Executor


class Proxify(
    private val serverAddress: ServerAddress
) {

    init {
        setProxy(serverAddress.host, serverAddress.port)
    }


    private fun setProxy(host: String, port: Int) {
        if (WebViewFeature.isFeatureSupported(WebViewFeature.PROXY_OVERRIDE)) {
            val proxyUrl = "${host}:${port}"
            val proxyConfig: ProxyConfig = ProxyConfig.Builder()
                .addProxyRule(proxyUrl)
                .build()
            ProxyController.getInstance().setProxyOverride(proxyConfig, object : Executor {
                override fun execute(command: Runnable) {

                }
            }, Runnable { Log.w("fromProxy", "WebView proxy") })
        } else {
            // use the solution of other anwsers
        }
    }
}