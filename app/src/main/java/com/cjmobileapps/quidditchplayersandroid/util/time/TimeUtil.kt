package com.cjmobileapps.quidditchplayersandroid.util.time

interface TimeUtil {
    suspend fun delayWithRandomTime()

    fun getRandomSeconds(): Long

    fun isDelayLoopRunning(): Boolean
}
