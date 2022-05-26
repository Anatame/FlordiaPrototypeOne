package com.anatame.flordia.domain.managers.flordia_web_view

import com.anatame.flordia.presentation.widgets.flordia_web_view.WebEngineEventListener
import timber.log.Timber

class WebEngineEventListenerImpl(
    private val scriptAtStart: String? = null,
    private val scriptAtFinish: String? = null
): WebEngineEventListener {
    var startTime: Long = 0
    var endTime: Long = 0

    override fun pageStarted(): String? {
        startTime = System.currentTimeMillis()
        Timber.d("Page Started")

        return scriptAtStart
    }

    override fun pageFinished(): String? {
        endTime = System.currentTimeMillis()
        val totalTime = endTime.minus(startTime)
        Timber.d("Page Finished")
        Timber.d("Total Time Taken: $totalTime")

        return scriptAtFinish
    }

}