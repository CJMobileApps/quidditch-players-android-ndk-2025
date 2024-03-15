package com.cjmobileapps.quidditchplayersandroid.util

import java.util.concurrent.TimeUnit

object TimeUtil {
    fun getRandomSeconds(): Long {
        val seconds = (1..60).random().toLong()
        return TimeUnit.SECONDS.toMillis(seconds)
    }
}
