package com.anatame.flordia.domain.models

import java.io.Serializable

data class MovieItem(
    val thumbnail: String,
    val title: String,
    val type: MovieType,
    val source: String
): Serializable

sealed class MovieType: Serializable{
    object TV: MovieType()
    object MOVIE: MovieType()
}
