package com.cjmobileapps.quidditchplayersandroid.util

import com.cjmobileapps.quidditchplayersandroid.util.coroutine.CoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.util.concurrent.Executor
import kotlin.coroutines.CoroutineContext

// todo move this to appropriate package util - coroutine
// then at time util to approriate package
object TestCoroutineDispatchers : CoroutineDispatchers {
    override val io: CoroutineContext = Dispatchers.Unconfined

    override val ioExecutor: Executor = Dispatchers.Unconfined.asExecutor()

    override val main: CoroutineContext = Dispatchers.Unconfined
}
