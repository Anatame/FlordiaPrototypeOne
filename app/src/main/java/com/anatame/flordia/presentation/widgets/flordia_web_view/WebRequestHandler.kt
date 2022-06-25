package com.anatame.flordia.presentation.widgets.flordia_web_view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import com.anatame.flordia.data.network.AppNetworkClient
import com.anatame.flordia.presentation.widgets.flordia_web_view.utils.getTextWebResource
import com.anatame.flordia.presentation.widgets.flordia_web_view.utils.replaceFile
import com.anatame.flordia.presentation.widgets.flordia_web_view.utils.requestFilter
import com.anatame.flordia.utils.FilterList
import okhttp3.*
import okhttp3.Headers.Companion.toHeaders
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import kotlin.system.measureTimeMillis

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

            val fileName: String? =  FilterList.offlineFiles[request?.url.toString()]

            if(fileName == null)
                webResourceResponseBuilder(getResponseForRequest(request))
            else
                fileName.let { webEngine?.context?.let { ctx -> replaceFile(ctx, it) } }

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
            var timeTaken: Long = 0
            val reqRes = customElapsedTime({ timeTaken = it }){
                try {
                    AppNetworkClient.getClient().newCall(it).execute()
                } catch (e: Exception){
                    Timber.tag("errorfuck").d("MOTHERFUCKER")
                    Handler(Looper.getMainLooper()).post {
                        webEngine?.webEngineEventListener?.onError("RIP")
                    }
                    null
                }
            }

            if(timeTaken >= 50){
                Timber.tag("LongRequest").d("total time taken is $timeTaken for ${it.url.toString()}")
            }
            return reqRes
        }
    }


    fun <T> customElapsedTime(
        timeTaken:((time: Long) -> Unit),
        block: () -> T
    ): T {

        var final: T

        val elapsedTime = measureTimeMillis {
            final = block()
        }

        timeTaken(elapsedTime)

        return final
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
}