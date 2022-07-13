package com.anatame.flordia.presentation.widgets.flordia_web_view.utils

import android.content.Context
import android.webkit.WebResourceResponse
import com.anatame.flordia.utils.FilterList
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream

fun requestFilter(url: String): Boolean {
    return (checkIfContainsKeyWord(url, FilterList.allowedHosts)
            && !checkIfContainsKeyWord(url, FilterList.blockedKeywords))
}

fun checkIfContainsKeyWord(url: String, list: List<String>): Boolean{
    val strArr = url.split(".", "?", "/")
    strArr.forEach {
        if(list.contains(it.trim())) {
            return true
        }
    }
    return false
}

fun getTextWebResource(data: InputStream = ByteArrayInputStream("".toByteArray())): WebResourceResponse {
    return WebResourceResponse("text/plain", "UTF-8", data)
}

fun replaceFile(context: Context, scriptFile: String): WebResourceResponse {
    val input: InputStream
    return try {
        input = context.assets.open(scriptFile)
        // String-ify the script byte-array using BASE64 encoding !!!
        val encoded = input
        Timber.tag("encoded").d(encoded.toString())
        return if (scriptFile.endsWith("html")) {
            getTextWebResourceHTML(encoded)
        } else {
            getTextWebResourceJS(encoded)
        }
    } catch (e: IOException) {
        val textStream: InputStream = ByteArrayInputStream("".toByteArray())
        getTextWebResource(textStream)
    }
}



fun getTextWebResourceJS(data: InputStream): WebResourceResponse {
    Timber.tag("javascriptcalled").d("fuck dude")
    val headers = HashMap<String, String>()

    return WebResourceResponse(
        "text/javascript",
        "UTF-8",
        200,
        "OK",
        headers,
        data
    )
}

fun getTextWebResourceHTML(data: InputStream): WebResourceResponse {
    Timber.tag("htmlcalled").d("fuck dude")
    return WebResourceResponse("text/html", "UTF-8", data)
}
