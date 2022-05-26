package com.anatame.flordia.presentation.widgets.flordia_web_view

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse

interface WebRequestHandler {
    fun getWebResourceResponseForRequest(request: WebResourceRequest?): WebResourceResponse?
}