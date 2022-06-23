package com.anatame.flordia.presentation.widgets.flordia_web_view

import android.os.Handler
import android.os.Looper
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import com.anatame.flordia.data.network.AppNetworkClient
import com.anatame.flordia.utils.FilterList
import com.anatame.flordia.utils.retryIO
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.Headers.Companion.toHeaders
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import javax.net.ssl.SSLHandshakeException

abstract class WebRequestHandler {

    var webEngine: FlordiaWebEngine? = null

    fun getWebResourceResponseForRequest(
        request: WebResourceRequest?)
    : WebResourceResponse?{

        request?.requestHeaders?.toHeaders()?.forEach {
            Timber.tag("requestHeaders").d("${it.first}: ${it.second}")
        }

        val url = request?.url.toString()
        return if(requestFilter(url) && currentRequestURL(url))
                    handleNetworkRequest(request)
        else {
            Timber.d("""Blocked:
                Host: ${request?.url?.host}
                Url: ${request?.url}

            """.trimIndent())
            getTextWebResource()
        }
    }


    open fun currentRequestURL(url: String): Boolean = true

    private fun handleNetworkRequest(
        request: WebResourceRequest?
    ): WebResourceResponse?
    {
        return try {
            Timber.tag("calledFor").d(request?.url.toString())
            if(request?.url.toString().contains("info"))
                Timber.tag("infoJson").d(request?.url.toString())

            webResourceResponseBuilder(getResponseForRequest(request))
        } catch(e: Exception) {
            e.printStackTrace()
            Timber.d("Failed for ${request?.url}")
            null
        }
    }

    private fun getResponseForRequest(request: WebResourceRequest?): Response? {
        val newRequest = request?.requestHeaders?.toHeaders()?.let {
            Request.Builder()
                .url(request.url.toString())
                .headers(it)
                .build()
        }

        return newRequest?.let {

            var newResponse: Response? = null

            AppNetworkClient.getClient().newCall(it).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    Handler(Looper.getMainLooper()).post {
                        webEngine?.webEngineEventListener?.onError("Rip")
                    }
                    Timber.tag("errorfuck").d("MOTHERFUCKER")
                }

                override fun onResponse(call: Call, response: Response) {
                    newResponse = response
                }
            })

            return newResponse
        }
    }




    private fun webResourceResponseBuilder(response: Response?): WebResourceResponse? {
        return response?.let {

            WebResourceResponse(
                it.body?.contentType()?.let { "${it.type}/${it.subtype}" },
                it.body?.contentType()?.charset(Charset.defaultCharset())?.name(),
                it.code,
                "OK",
                it.headers.toMap(),
                it.body?.byteStream()
            )
        };
    }

    private fun requestFilter(url: String): Boolean {
        return (checkIfContainsKeyWord(url, FilterList.allowedHosts)
                && !checkIfContainsKeyWord(url, FilterList.blockedKeywords))
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

    private fun getTextWebResource(data: InputStream = ByteArrayInputStream("".toByteArray())): WebResourceResponse {
        return WebResourceResponse("text/plain", "UTF-8", data)
    }

}