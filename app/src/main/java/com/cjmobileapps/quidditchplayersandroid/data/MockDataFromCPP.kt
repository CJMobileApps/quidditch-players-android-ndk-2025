package com.cjmobileapps.quidditchplayersandroid.data

import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
import com.cjmobileapps.quidditchplayersandroid.data.model.Status

object MockDataFromCPP {
    init {
        System.loadLibrary("quidditchplayersandroid")
    }

    external fun stringFromJNI(): String

    external fun stringFromJNI2()

    external fun convertToKotlin(
        playerId: String,
        status: String,
    ): Status

    external fun getStatus(name: String): String

    external fun getResponseWrapperMockStatus(): ResponseWrapper<Status>

    external fun getMockStatus(): Status

    external fun getMockHouses(): List<House>

    external fun getMockHousesResponseWrapper(): ResponseWrapper<List<House>>
}
