package com.anatame.flordia.presentation.widgets.flordia_web_view

interface WebEngineEventListener {
    fun pageStarted(): String?
    fun pageFinished(): String?
}