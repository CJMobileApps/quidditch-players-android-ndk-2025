package com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers

import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.Player
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerEntity
import com.cjmobileapps.quidditchplayersandroid.data.model.Position
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrappers
import kotlinx.coroutines.flow.Flow

interface QuidditchPlayersRepository {
    suspend fun getAllHouses(): ResponseWrapper<List<House>>

    suspend fun getAllHousesFlow(): Flow<List<House>>

    suspend fun createAllHousesToDB(houses: List<House>)

    suspend fun getPlayersByHouse(houseName: String): ResponseWrapper<List<Player>>

    suspend fun createPlayersByHouseToDB(players: List<PlayerEntity>)

    suspend fun getAllPlayersFlow(): Flow<List<PlayerEntity>>

    suspend fun fetchPlayersAndPositions(houseName: String): ResponseWrappers<List<Player>, Map<Int, Position>>
}
