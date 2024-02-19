package com.cjmobileapps.quidditchplayersandroid.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

data class Player(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val yearsPlayed: List<Int>,
    val favoriteSubject: String,
    val position: Int,
    val imageUrl: String,
    val house: HouseName
)

@Entity
data class PlayerEntity(
    @PrimaryKey val id: UUID,
    val firstName: String,
    val lastName: String,
    val yearsPlayed: List<Int>,
    val favoriteSubject: String,
    val position: String,
    val imageUrl: String,
    val house: HouseName
)

fun List<Player>.toPlayersEntities(positions: Map<Int, Position>): List<PlayerEntity> {
    return this.map { it.toPlayerEntity(positions) }
}

fun Player.toPlayerEntity(positions: Map<Int, Position>): PlayerEntity {
    return PlayerEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        yearsPlayed = yearsPlayed,
        favoriteSubject = favoriteSubject,
        position = positions[this.position]?.positionName ?: "",
        imageUrl = imageUrl,
        house = house
    )
}
