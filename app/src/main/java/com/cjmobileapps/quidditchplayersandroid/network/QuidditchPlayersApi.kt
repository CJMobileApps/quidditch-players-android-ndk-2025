package com.cjmobileapps.quidditchplayersandroid.network

import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.Player
import com.cjmobileapps.quidditchplayersandroid.data.model.Position
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuidditchPlayersApi {

    @GET("api/v1/quidditchplayers/house")
    fun getAllHouses(): Deferred<Response<ResponseWrapper<List<House>>>>

    @GET("api/v1/quidditchplayers/player")
    fun getPlayersByHouse(
        @Query("houseName") houseName: String
    ): Deferred<Response<ResponseWrapper<List<Player>>>>

    @GET("api/v1/quidditchplayers/position")
    fun getPositions(): Deferred<Response<ResponseWrapper<Map<Int, Position>>>>
}
