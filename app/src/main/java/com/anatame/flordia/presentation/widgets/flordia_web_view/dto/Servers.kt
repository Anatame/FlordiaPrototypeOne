package com.anatame.flordia.presentation.widgets.flordia_web_view.dto

data class Servers(
    val servers: List<Server>
)

data class Server(
    val name: String,
    val dataId: String,
)