package com.flatcode.simplecomposeapps.pokemon.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flatcode.simplecomposeapps.pokemon.domain.model.PokeItemDetails

@Entity(tableName = "pokemon_details")
data class PokeDetailEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val type1: String,
    val type2: String?,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    val height: Int,
    val weight: Int
)

fun PokeDetailEntity.toDomain() = PokeItemDetails(
    id, name, type1, type2, hp, attack, defense, specialAttack, specialDefense, speed, height, weight
)