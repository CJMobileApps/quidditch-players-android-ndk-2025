package com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers

import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
import kotlinx.coroutines.flow.Flow

interface QuidditchPlayersRepository {
    suspend fun getAllHouses(): ResponseWrapper<List<House>>

    suspend fun getAllHousesFlow(): Flow<List<House>>

    suspend fun createAllHousesToDB(houses: List<House>)
}
