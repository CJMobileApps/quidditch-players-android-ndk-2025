package com.cjmobileapps.quidditchplayersandroid.util

import com.cjmobileapps.quidditchplayersandroid.util.time.TimeUtil

object TestTimeUtil : TimeUtil {
    override suspend fun delayWithRandomTime() { }

    override fun getRandomSeconds(): Long = 0
}
