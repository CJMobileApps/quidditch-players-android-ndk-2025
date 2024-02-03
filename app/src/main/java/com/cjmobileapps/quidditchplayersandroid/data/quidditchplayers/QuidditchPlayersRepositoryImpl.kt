package com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers

import com.cjmobileapps.quidditchplayersandroid.data.datasource.QuidditchPlayersApiDataSource
import com.cjmobileapps.quidditchplayersandroid.data.datasource.QuidditchPlayersLocalDataSource
import com.cjmobileapps.quidditchplayersandroid.data.model.House

class QuidditchPlayersRepositoryImpl(
    private val quidditchPlayersApiDataSource: QuidditchPlayersApiDataSource,
    private val quidditchPlayersLocalDataSource: QuidditchPlayersLocalDataSource
) : QuidditchPlayersRepository {
    override suspend fun getAllHouses() = quidditchPlayersApiDataSource.getAllHouses()

    override suspend fun getAllHousesFlow() = quidditchPlayersLocalDataSource.getAllHousesFlow()

    override suspend fun createAllHousesToDB(houses: List<House>) =
        quidditchPlayersLocalDataSource.createAllHouses(houses)
}
