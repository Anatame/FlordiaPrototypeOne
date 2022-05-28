package com.anatame.flordia.presentation.widgets.flordia_web_view

interface WebEngineEventListener {
    fun pageStarted(): String?
    fun pageFinished(): String?
    fun getHTML(html: String)
    fun embedUrlDetected(url: String)
}