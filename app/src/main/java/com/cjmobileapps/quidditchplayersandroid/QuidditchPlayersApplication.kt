package com.cjmobileapps.quidditchplayersandroid

import android.app.Application
import com.cjmobileapps.quidditchplayersandroid.data.MockDataFromCPP
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

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
    }
}
