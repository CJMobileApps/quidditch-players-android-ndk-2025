package com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers

import com.cjmobileapps.quidditchplayersandroid.data.datasource.QuidditchPlayersApiDataSource

class QuidditchPlayersRepositoryImpl(
    private val quidditchPlayersApiDataSource: QuidditchPlayersApiDataSource
) : QuidditchPlayersRepository {
    override suspend fun getAllHouses() = quidditchPlayersApiDataSource.getAllHouses()
}
