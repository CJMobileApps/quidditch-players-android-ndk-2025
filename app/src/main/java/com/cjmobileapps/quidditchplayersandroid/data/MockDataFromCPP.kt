package com.cjmobileapps.quidditchplayersandroid.data

object MockDataFromCPP {
    init {
        System.loadLibrary("quidditchplayersandroid")
    }

    external fun stringFromJNI(): String
    external fun stringFromJNI2()


}
