package com.anatame.flordia.presentation.fragments.home.adapter

sealed class HomeRecyclerViewItem {
    data class HomeItemTypeOne(
        val text: String = "I'm One"
    ): HomeRecyclerViewItem()

    data class HomeItemTypeTwo(
        val text: String = "I'm 2"
    ): HomeRecyclerViewItem()

    data class HomeItemTypeThree(
        val text: String = "I'm 3"
    ): HomeRecyclerViewItem()

}