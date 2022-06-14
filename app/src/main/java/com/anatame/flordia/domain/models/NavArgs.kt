package com.anatame.flordia.domain.models

import android.widget.ImageView
import java.io.Serializable

object NavArgs{
    data class DetailFragment(
        val movieItem: MovieItem,
    ) : Serializable
}