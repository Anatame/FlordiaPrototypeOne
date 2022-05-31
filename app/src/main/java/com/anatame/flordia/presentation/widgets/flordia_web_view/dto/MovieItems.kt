package com.anatame.flordia.presentation.widgets.flordia_web_view.dto

import com.anatame.flordia.domain.models.MovieItem
import com.anatame.flordia.domain.models.MovieType

data class MovieItems(
    val list: List<MItem>
)

data class MItem(
    val thumbnail: String,
    val title: String,
    val type: String,
    val source: String
)