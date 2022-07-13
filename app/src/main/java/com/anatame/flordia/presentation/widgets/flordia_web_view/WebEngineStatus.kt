package com.anatame.flordia.presentation.widgets.flordia_web_view

object WebEngineStatus {

    private var url: String? = null
    operator fun invoke(url: String?): WebEngineStatus {
        this.url = url
        return this
    }

    val currentStatus: Status
        get() {
            return if (checkIfOnSearchScreen() == true) {
                Status.OnSearchScreen
            } else if (checkIfOnMovieDetailsScreen()){
                Status.OnMovieDetailsScreen
            } else {
                Status.OnBaseScreen
            }
        }

    private fun checkIfOnSearchScreen(): Boolean? {
        return url?.contains("search")
    }

    private fun checkIfOnMovieDetailsScreen(): Boolean {
        return url?.contains("series") == true || url?.contains("movie") == true
    }

    sealed class Status{
        object OnBaseScreen: Status()
        object OnSearchScreen: Status()
        object OnMovieDetailsScreen: Status()
    }
}