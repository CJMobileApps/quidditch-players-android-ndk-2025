package com.cjmobileapps.quidditchplayersandroid.hilt.module

import com.cjmobileapps.quidditchplayersandroid.data.datasource.QuidditchPlayersApiDataSource
import com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers.QuidditchPlayersRepository
import com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers.QuidditchPlayersRepositoryImpl
import com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers.QuidditchPlayersUseCase
import com.cjmobileapps.quidditchplayersandroid.network.QuidditchPlayersApi
import com.cjmobileapps.quidditchplayersandroid.util.coroutine.CoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun quidditchPlayersApiDataSource(
        quidditchPlayersApi: QuidditchPlayersApi,
        coroutineDispatchers: CoroutineDispatchers
    ): QuidditchPlayersApiDataSource {
        return QuidditchPlayersApiDataSource(
            quidditchPlayersApi = quidditchPlayersApi,
            coroutineDispatchers = coroutineDispatchers
        )
    }

    @Singleton
    @Provides
    fun quidditchPlayersRepository(
        quidditchPlayersApiDataSource: QuidditchPlayersApiDataSource
    ): QuidditchPlayersRepository {
        return QuidditchPlayersRepositoryImpl(
            quidditchPlayersApiDataSource = quidditchPlayersApiDataSource
        )
    }

    @Singleton
    @Provides
    fun quidditchPlayersUseCase(
        quidditchPlayersRepository: QuidditchPlayersRepository
    ): QuidditchPlayersUseCase {
        return QuidditchPlayersUseCase(
            quidditchPlayersRepository = quidditchPlayersRepository
        )
    }
}
