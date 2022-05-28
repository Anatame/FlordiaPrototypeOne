package com.anatame.flordia.domain.models

data class MovieItem(
    val thumbnail: String,
    val title: String,
    val type: MovieType,
    val source: String
)

sealed class MovieType{
    object TV: MovieType()
    object MOVIE: MovieType()
}
