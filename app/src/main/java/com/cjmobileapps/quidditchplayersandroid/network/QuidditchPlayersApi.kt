package com.cjmobileapps.quidditchplayersandroid.network

import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface QuidditchPlayersApi {

    @GET("api/v1/quidditchplayers/house")
    fun getAllHouses(): Deferred<Response<ResponseWrapper<List<House>>>>
}
