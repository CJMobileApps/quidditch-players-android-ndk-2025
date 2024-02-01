package com.cjmobileapps.quidditchplayersandroid.data.datasource

import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
import com.cjmobileapps.quidditchplayersandroid.network.QuidditchPlayersApi
import com.cjmobileapps.quidditchplayersandroid.util.coroutine.CoroutineDispatchers
import com.cjmobileapps.quidditchplayersandroid.util.withContextApiWrapper

class QuidditchPlayersApiDataSource(
    private val quidditchPlayersApi: QuidditchPlayersApi,
    private val coroutineDispatchers: CoroutineDispatchers
) {

    suspend fun getAllHouses(): ResponseWrapper<List<House>> {
        return withContextApiWrapper(coroutineDispatchers.io) {
            quidditchPlayersApi.getAllHouses()
        }
    }
}
