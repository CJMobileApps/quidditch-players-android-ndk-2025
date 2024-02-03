package com.cjmobileapps.quidditchplayersandroid.room

import androidx.room.TypeConverter
import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.HouseName
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun toHouseList(value: String?): List<House> {
        if(value.isNullOrEmpty() || value == "null") return emptyList()
        val type = object : TypeToken<List<House>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromHouseList(value: List<House>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toHouseName(value: Int): HouseName {
        return HouseName.entries[value]
    }

    @TypeConverter
    fun fromHouseName(value: HouseName): Int {
        return value.ordinal
    }
}
