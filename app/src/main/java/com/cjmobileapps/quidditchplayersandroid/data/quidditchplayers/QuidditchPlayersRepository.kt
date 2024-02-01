package com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers

import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper

interface QuidditchPlayersRepository {
    suspend fun getAllHouses(): ResponseWrapper<List<House>>
}
