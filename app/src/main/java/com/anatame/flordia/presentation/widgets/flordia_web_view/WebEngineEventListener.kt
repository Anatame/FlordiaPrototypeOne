package com.anatame.flordia.presentation.widgets.flordia_web_view

import com.anatame.flordia.presentation.widgets.flordia_web_view.dto.MItem
import com.anatame.flordia.presentation.widgets.flordia_web_view.dto.MovieControls

interface WebEngineEventListener {
    fun pageStarted(): String?
    fun pageFinished(): String?
    fun getHTML(html: String)
    fun getMovieList(list: List<MItem>?)
    fun getMovieControls(controls: MovieControls)
    fun embedUrlDetected(url: String)
    fun onError(errorDescription: String?)
}