package com.anatame.flordia.presentation.activities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anatame.flordia.domain.models.MovieItem
import com.anatame.flordia.presentation.widgets.flordia_web_view.dto.MovieControls

class MainActivityViewModel: ViewModel() {
    val searchMovieItems: MutableLiveData<List<MovieItem>?> = MutableLiveData(null)
    val movieControls: MutableLiveData<MovieControls?> = MutableLiveData(null)
    val embedUrl: MutableLiveData<String?> = MutableLiveData(null)

    val serverDataId: MutableLiveData<String?> = MutableLiveData(null)
    val episodeDataId: MutableLiveData<String?> = MutableLiveData(null)
}