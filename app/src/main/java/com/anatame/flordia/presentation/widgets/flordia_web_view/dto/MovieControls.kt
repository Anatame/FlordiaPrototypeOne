package com.anatame.flordia.presentation.widgets.flordia_web_view.dto

data class MovieControls(
    val servers: List<Server>,
    val seasons: List<Season>,
    val seasonWiseEpisodes: List<List<Episode>>
)

data class Server(
    val name: String,
    val dataId: String
)

data class Season(
    val name: String
)

data class Episode(
    val name: String,
    val dataId: String,
    val source: String,
)

