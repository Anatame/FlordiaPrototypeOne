package com.anatame.flordia.domain.managers.flordia_web_view

import com.anatame.flordia.presentation.widgets.flordia_web_view.WebRequestHandler

object WebRequestHandlerImpl: WebRequestHandler() {

    override fun currentRequestURL(url: String): Boolean {
        if(isThisStreamUrl(url)) handleEmbedUrl(url)

        return super.currentRequestURL(url)
    }

    private fun isThisEmbedUrl(url: String): Boolean {
        return url.contains("/e/")
    }

    private fun isThisStreamUrl(url: String): Boolean {
        return !url.contains("ajax")
                && !url.contains("/e/")
                && url.contains("info")
    }


    private fun handleEmbedUrl(url: String){
        webEngineEventListener?.embedUrlDetected(url)
    }

//    override fun getWebResourceResponseForRequest(
//        webEngineEventListener: WebEngineEventListener?,
//        request: WebResourceRequest?): WebResourceResponse? {
//
//        return if(requestFilter(request?.url.toString()))
//            handleNetworkRequest(webEngineEventListener, request)
//        else {
//            Timber.d("""Blocked:
//                Host: ${request?.url?.host}
//                Url: ${request?.url}
//
//            """.trimIndent())
//            getTextWebResource()
//        }
//    }
//
//    private fun handleNetworkRequest(
//        webEngineEventListener: WebEngineEventListener?,
//        request: WebResourceRequest?): WebResourceResponse?
//    {
//        return try {
//            Timber.d("called for ${request?.url.toString()}")
//
//            handleEmbedUrlDetected(webEngineEventListener, request?.url.toString())
//
//            val newRequest = request?.requestHeaders?.toHeaders()?.let {
//                Request.Builder()
//                    .url(request.url.toString())
//                    .headers(it)
//                    .build()
//            }
//
//            val response = newRequest?.let { AppNetworkClient.getClient().newCall(it).execute() }
//
//
//            webResourceResponseConstructor(response)
//        } catch(e: Exception) {
//            e.printStackTrace()
//            Timber.d("Failed for ${request?.url}")
//            null
//        }
//    }
//
//    private fun webResourceResponseConstructor(response: Response?): WebResourceResponse? {
//        return response?.let {
//            WebResourceResponse(
//                it.body?.contentType()?.let { "${it.type}/${it.subtype}" },
//                it.body?.contentType()?.charset(Charset.defaultCharset())?.name(),
//                it.code,
//                "OK",
//                it.headers.toMap(),
//                it.body?.byteStream()
//            )
//        };
//    }
//
//    private fun requestFilter(url: String): Boolean {
//        return (checkIfContainsKeyWord(url, FilterList.allowedHosts)
//                && !checkIfContainsKeyWord(url, FilterList.blockedKeywords))
//    }
//
//    private fun checkIfContainsKeyWord(url: String, list: List<String>): Boolean{
//        val strArr = url.split(".", "?", "/")
//        strArr.forEach {
//            if(list.contains(it.trim())) {
//                return true
//            }
//        }
//        return false
//    }
//
//    private val textStream: InputStream = ByteArrayInputStream("".toByteArray())
//
//    private fun getTextWebResource(data: InputStream = textStream): WebResourceResponse {
//        return WebResourceResponse("text/plain", "UTF-8", data)
//    }
//
//    private fun handleEmbedUrlDetected(webEngineEventListener: WebEngineEventListener?, url: String){
//        if(url.contains("/e/")){
//            Timber.tag("EmbedUrl").d(url)
//            webEngineEventListener?.embedUrlDetected(url)
//        }
//    }

}
//            if(request?.url.toString().endsWith(".list") ||
//                request?.url.toString().endsWith(".playlist") ||
//                request?.url.toString().endsWith(".m3u8") ||
//                request?.url.toString().contains("/e/")
//            ) {
//                Timber.d("play for ${request?.url.toString()}")
//            }



//            response?.let {
//
//                Timber.d("""
//                    |
//                ${it.body?.contentType()?.let { "${it.type}/${it.subtype}" }}
//                ${it.body?.contentType()?.charset(Charset.defaultCharset())?.name()}
//                ${it.code}
//                "OK"
//                ${it.headers.toMap()}
//                """.trimIndent())
//            };
