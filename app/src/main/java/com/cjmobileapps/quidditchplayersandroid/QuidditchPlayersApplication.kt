package com.cjmobileapps.quidditchplayersandroid

import android.app.Application
import com.cjmobileapps.quidditchplayersandroid.data.MockDataFromCPP
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.UUID

@HiltAndroidApp
class QuidditchPlayersApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        MockDataFromCPP.stringFromJNI2()
        val blah = MockDataFromCPP.stringFromJNI()
        println("HERE_ blah " + blah)

        val blahStatus = MockDataFromCPP.convertToKotlin(UUID.randomUUID().toString(), "Player 1 is online")
        println("HERE_ blahStatus " + blahStatus)
        println("HERE_ getStatus " + MockDataFromCPP.getStatus("Harry Potter"))
        println("HERE_ MockDataFromCPP.getResponseWrapperMockStatus() " + MockDataFromCPP.getResponseWrapperMockStatus())
        println("HERE_ MockDataFromCPP.getMockStatus(  " + MockDataFromCPP.getMockStatus())
        println("HERE_ MockDataFromCPP.getResponseWrapperMockStatus() " + MockDataFromCPP.getResponseWrapperMockStatus())
        println("HERE_ MockDataFromCPP.getMockHouses " + MockDataFromCPP.getMockHouses())
        println("HERE_ " + MockDataFromCPP.getMockHousesResponseWrapper())
        println("HERE_ " + MockDataFromCPP.getMockPositions())
        println("HERE_ " + MockDataFromCPP.getMockPositionsResponseWrapper())

        println("HERE_ MockDataFromCPP.getMockAllQuidditchTeams().size " + MockDataFromCPP.getMockAllQuidditchTeams().size)
        MockDataFromCPP.getMockAllQuidditchTeams().forEach {
            println("HERE_ " + it)
        }

        println("HERE_  getMockAllQuidditchTeamsResponseWrapper(): " + MockDataFromCPP.getMockAllQuidditchTeamsResponseWrapper())
        println("HERE_  MockDataFromCPP.getMockTrueResponseWrapper(): " + MockDataFromCPP.getMockTrueResponseWrapper())
    }
}
