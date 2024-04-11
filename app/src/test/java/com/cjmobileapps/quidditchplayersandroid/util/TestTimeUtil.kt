package com.cjmobileapps.quidditchplayersandroid.util

import com.cjmobileapps.quidditchplayersandroid.util.time.TimeUtil

object TestTimeUtil : TimeUtil {
    private var count = 1

    override suspend fun delayWithRandomTime() { }

    override fun getRandomSeconds(): Long = 0

    override fun isDelayLoopRunning(): Boolean = (count-- > 0)
}
