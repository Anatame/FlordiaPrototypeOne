package com.anatame.flordia.models

import java.io.Serializable

object NavArgs{
    data class DetailFragment(
        val movieItem: MovieItem,
    ) : Serializable
}