package com.cjmobileapps.quidditchplayersandroid.data.model

data class House(
    val name: HouseName,
    val imageUrl: String,
    val emoji: String
)

enum class HouseName {
    GRYFFINDOR,
    SLYTHERIN,
    RAVENCLAW,
    HUFFLEPUFF
}
