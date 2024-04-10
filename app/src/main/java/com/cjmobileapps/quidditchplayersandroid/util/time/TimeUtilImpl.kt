package com.cjmobileapps.quidditchplayersandroid.util.time

import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

object TimeUtilImpl : TimeUtil {

    override fun isDelayLoopRunning() = true

    override suspend fun delayWithRandomTime() = delay(getRandomSeconds())

    override fun getRandomSeconds(): Long {
        val seconds = (1..60).random().toLong()
        return TimeUnit.SECONDS.toMillis(seconds)
    }
}
