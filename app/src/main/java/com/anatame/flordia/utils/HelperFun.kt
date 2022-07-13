package com.anatame.flordia.utils

import kotlinx.coroutines.delay
import timber.log.Timber
import java.io.IOException
import java.net.SocketException
import javax.net.ssl.SSLHandshakeException
import kotlin.system.measureTimeMillis

suspend fun <T> retryIO(
    times: Int = Int.MAX_VALUE,
    initialDelay: Long = 100, // 0.1 second
    maxDelay: Long = 1000,    // 1 second
    factor: Double = 2.0,
    block: suspend () -> T): T
{
    var currentDelay = initialDelay
    repeat(times - 1) {
        try {
            Timber.tag("retryIO").d("repeating Request")
            return block()
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.tag("damnMan").d("brah")
        }
        delay(currentDelay)
        currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
    }
    return block() // last attempt
}

fun <T> customElapsedTime(
    tag: String? = null,
    timeTaken:((time: Long) -> Unit)? = null,
    block: () -> T
): T {

    tag?.apply { Timber.tag(this).d("RequestStarted") } ?: run {
        Timber.d("RequestStarted")
    }

    var final: T

    val elapsedTime = measureTimeMillis {
        final = block()
    }

    timeTaken?.let { it(elapsedTime) }

    tag?.apply { Timber.tag(this).d(elapsedTime.toString()) } ?: run {
        Timber.d(elapsedTime.toString())
    }

    return final
}
