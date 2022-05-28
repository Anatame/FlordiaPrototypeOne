package com.anatame.flordia.presentation.activities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anatame.flordia.domain.models.MovieItem

class MainActivityViewModel: ViewModel() {
    val searchMovieItems: MutableLiveData<List<MovieItem>?> = MutableLiveData(null)
    val embedUrl: MutableLiveData<String?> = MutableLiveData(null)
}