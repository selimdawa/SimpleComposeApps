package com.flatcode.simplecomposeapps.pokemon.model

import kotlinx.serialization.Serializable

@Serializable
data class PokeModelDetails(
    val id: Int,
    val name: String,
    val types: List<Type>,
    val stats: List<Stat>,
    val height: Int,
    val weight: Int
)

@Serializable
data class Type(
    val slot: Int,
    val type: TypeName
)

@Serializable
data class TypeName(
    val name: String,
    val url: String
)

@Serializable
data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: StatName
)

@Serializable
data class StatName(
    val name: String,
    val url: String
)

fun PokeModelDetails.toDomain() = PokeItemDetails(
    id = id,
    name = name,
    type1 = types.getOrNull(0)?.type?.name ?: "unknown",
    type2 = types.getOrNull(1)?.type?.name,
    hp = stats.find { it.stat.name == "hp" }?.base_stat ?: 0,
    attack = stats.find { it.stat.name == "attack" }?.base_stat ?: 0,
    defense = stats.find { it.stat.name == "defense" }?.base_stat ?: 0,
    specialAttack = stats.find { it.stat.name == "special-attack" }?.base_stat ?: 0,
    specialDefense = stats.find { it.stat.name == "special-defense" }?.base_stat ?: 0,
    speed = stats.find { it.stat.name == "speed" }?.base_stat ?: 0,
    height = height,
    weight = weight
)