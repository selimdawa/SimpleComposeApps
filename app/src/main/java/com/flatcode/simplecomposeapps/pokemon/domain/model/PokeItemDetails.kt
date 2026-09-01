package com.flatcode.simplecomposeapps.pokemon.domain.model

import com.flatcode.simplecomposeapps.pokemon.data.database.entities.PokeDetailEntity

data class PokeItemDetails(
    val id: Int,
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

fun PokeItemDetails.toDatabase() = PokeDetailEntity(
    id, name, type1, type2, hp, attack, defense, specialAttack, specialDefense, speed, height, weight
)