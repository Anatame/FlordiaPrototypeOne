package com.anatame.flordia.utils

object FilterList {
    val allowedHosts: List<String> = listOf<String>(
        "bunnycdn",
        "fmovies",
        "9anime",
        "cloudflare",
        "googleapis",
        "vizcloud",
        "mcloud",
    )

    val blockedKeywords: List<String> = listOf<String>(
//        "css",
        "font-awesome",
        "fonts",
        "Swiper",
        "png",
//        "panel",
        "ico",
        "lazysizes",
    )
}