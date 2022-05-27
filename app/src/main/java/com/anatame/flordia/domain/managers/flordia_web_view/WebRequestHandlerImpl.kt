package com.anatame.flordia.domain.managers.flordia_web_view

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import com.anatame.flordia.data.network.AppNetworkClient
import com.anatame.flordia.presentation.widgets.flordia_web_view.WebRequestHandler
import com.anatame.flordia.utils.FilterList
import okhttp3.Headers.Companion.toHeaders
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.nio.charset.Charset

object WebRequestHandlerImpl: WebRequestHandler {
    override fun getWebResourceResponseForRequest(request: WebResourceRequest?): WebResourceResponse? {

        return if(requestFilter(request?.url.toString()))
            handleNetworkRequest(request)
        else {
            Timber.d("""Blocked:
                Host: ${request?.url?.host}
                Url: ${request?.url}

            """.trimIndent())
            getTextWebResource()
        }
    }

    private fun handleNetworkRequest(request: WebResourceRequest?): WebResourceResponse? {
        return try {
            Timber.d("called for ${request?.url.toString()}")
            if(request?.url.toString().endsWith(".list") ||
                request?.url.toString().endsWith(".playlist") ||
                request?.url.toString().endsWith(".m3u8") ||
                request?.url.toString().contains("embed")
            ) {
                Timber.d("play for ${request?.url.toString()}")
            }


            val newRequest = request?.requestHeaders?.toHeaders()?.let {
                Request.Builder()
                    .url(request.url.toString())
                    .headers(it)
                    .build()
            }

            val response = newRequest?.let { AppNetworkClient.getClient().newCall(it).execute() }

            response?.let {

                Timber.d("""
                    |
                ${it.body?.contentType()?.let { "${it.type}/${it.subtype}" }}
                ${it.body?.contentType()?.charset(Charset.defaultCharset())?.name()}
                ${it.code}
                "OK"
                ${it.headers.toMap()}
                """.trimIndent())
            };

            webResourceResponseConstructor(response)
        } catch(e: Exception) {
            e.printStackTrace()
            Timber.d("Failed for ${request?.url}")
            null
        }
    }

    private fun webResourceResponseConstructor(response: Response?): WebResourceResponse? {
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

    private val textStream: InputStream = ByteArrayInputStream("".toByteArray())

    private fun getTextWebResource(data: InputStream = textStream): WebResourceResponse {
        return WebResourceResponse("text/plain", "UTF-8", data)
    }

}