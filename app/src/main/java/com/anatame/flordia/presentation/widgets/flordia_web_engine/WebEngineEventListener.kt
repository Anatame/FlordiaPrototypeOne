package com.anatame.flordia.presentation.widgets.flordia_web_engine

interface WebEngineEventListener {
    fun pageStarted()
    fun pageFinished()
    fun getSearchList(movieList: List<MovieItem>)
    fun getEpisodes()
}

data class MovieItem(
    val thumbnail: String,
    val title: String,
    val type: String,
    val source: String
)

data class Server(
    val name: String,
    val dataId: String
)

data class Episode(
    val name: String,
    val dataId: String,
    val source: String,
)

data class Season(
    val name: String,
    val dataId: String
)