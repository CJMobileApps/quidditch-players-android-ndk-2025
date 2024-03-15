package com.cjmobileapps.quidditchplayersandroid.hilt.module

import com.cjmobileapps.quidditchplayersandroid.util.coroutine.CoroutineDispatchers
import com.cjmobileapps.quidditchplayersandroid.util.coroutine.CoroutineDispatchersImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CoroutinesModule {
    @Singleton
    @Provides
    fun coroutinesDispatchers(): CoroutineDispatchers {
        return CoroutineDispatchersImpl
    }
}
