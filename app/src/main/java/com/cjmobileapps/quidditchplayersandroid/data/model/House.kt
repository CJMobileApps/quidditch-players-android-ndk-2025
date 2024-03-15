package com.cjmobileapps.quidditchplayersandroid.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class House(
    @PrimaryKey(autoGenerate = true) val houseId: Int = 0,
    val name: HouseName,
    val imageUrl: String,
    val emoji: String,
)

enum class HouseName {
    GRYFFINDOR,
    SLYTHERIN,
    RAVENCLAW,
    HUFFLEPUFF,
}
