package com.anatame.flordia.utils

object FilterList {

    val offlineFiles = HashMap<String, String>()

    init {
        InitOfflineFiles()
    }

    val allowedHosts: List<String> = listOf<String>(
        "bunnycdn",
        "fmovies",
        "9anime",
        "cloudflare",
        "googleapis",
        "vizcloud",
        "mcloud",
        "proxyman69",
        "saiyaman",
        "workers",
        "dev"
    )

    val blockedKeywords: List<String> = listOf<String>(
        //"css",
        "font-awesome",
        "fonts",
        "Swiper",
        "png",
        "subtitles",
        "ico",
        "lazysizes",
    )


    private fun InitOfflineFiles(){
        val movieJsSupportFiles = "js/support_files_movie"
        val moviePlayerMcloudFiles = "js/support_files_movie/player/mcloud"

        offlineFiles.put("https://fmovies.to/search?keyword=moon+knight&vrf=%2FmTFtmbuaDGqr4RtKYBwD%2BIV", "html/movie_search_page.html")
        offlineFiles.put("https://fmovies.to/sw.js", "$movieJsSupportFiles/sw.js")
        offlineFiles.put("https://fmovies.to/ajax/user/panel", "$movieJsSupportFiles/pannel")
        offlineFiles.put("https://s1.bunnycdn.ru/assets/template_1/min/all.js?62a80feb", "$movieJsSupportFiles/all.js")
        offlineFiles.put("https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js", "$movieJsSupportFiles/jquerymin.js")
        offlineFiles.put("https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js", "$movieJsSupportFiles/popper.js")
        offlineFiles.put("https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js", "$movieJsSupportFiles/popper.js")
        offlineFiles.put("https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.0/js/bootstrap.min.js", "$movieJsSupportFiles/bootstrap.js")

        // detail screen
        offlineFiles.put("https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js", "$movieJsSupportFiles/googleJquery.js")

        // vizcloud
//        offlineFiles.put("https://vizcloud.store/assets/player/jwplayer-8.24.3/jwplayer.core.controls.js", "$movieJsSupportFiles/jwcontrols.js")
//        offlineFiles.put("https://vizcloud.store/assets/player/jwplayer-8.24.3/jwplayer.js", "$movieJsSupportFiles/jwplayer.js")
//        offlineFiles.put("https://vizcloud.store/assets/player/jwplayer-8.24.3/provider.hlsjs.js", "$movieJsSupportFiles/vizcloudhls.js")
//        offlineFiles.put("https://vizcloud.store/assets/player/jwplayer-8.24.3/jwpsrv.js", "$movieJsSupportFiles/jpsrv.js")

        // mcloud
        offlineFiles.put("https://mcloud.to/assets/player/jwplayer-8.24.3/provider.hlsjs.js", "$moviePlayerMcloudFiles/hls.js")
        offlineFiles.put("https://mcloud.to/assets/player/jwplayer-8.24.3/jwplayer.core.controls.js", "$moviePlayerMcloudFiles/playercontrols.js")
        offlineFiles.put("https://mcloud.to/assets/player/jwplayer-8.24.3/jwpsrv.js", "$moviePlayerMcloudFiles/jpsrv.js")
        offlineFiles.put("https://mcloud.to/assets/player/jwplayer-8.24.3/jwplayer.js", "$moviePlayerMcloudFiles/jwplayer.js")
    }


}