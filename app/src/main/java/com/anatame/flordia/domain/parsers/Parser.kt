package com.anatame.flordia.domain.parsers

import android.util.Log
import com.anatame.flordia.domain.models.MovieItem
import com.anatame.flordia.domain.models.MovieType
import org.jsoup.Jsoup
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

object Parser {
    fun getSearchItems(html: String): List<MovieItem>{
        val doc = Jsoup.parse(html)
        val filmList = doc.getElementsByClass("filmlist")[0].children()

        return filmList.filter {
            !it.hasClass("clearfix")
        }.map { element ->
            Timber.d(element.toString())
            val thumbnail = element.select("img").attr("src")
            val title = element.select("a")[1].text()
            val type = if(element.getElementsByClass("type").text().contains("TV")) MovieType.TV else MovieType.MOVIE
            val source = element.select("a")[1].attr("href")

            MovieItem(
                thumbnail,
                title,
                type,
                source
            )
        }
    }
}