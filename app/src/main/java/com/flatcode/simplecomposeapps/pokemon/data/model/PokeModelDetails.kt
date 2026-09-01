package com.flatcode.simplecomposeapps.pokemon.data.model

import com.flatcode.simplecomposeapps.pokemon.domain.model.PokeItemDetails

data class PokeModelDetails(
    val id: Int,
    val name: String,
    val types: List<Type>,
    val stats: List<Stat>,
    val height: Int,
    val weight: Int
)

data class Type(
    val slot: Int,
    val type: TypeName
)

data class TypeName(
    val name: String,
    val url: String
)

data class Stat(
    val base_stat: Int,
    val effort: Int,
    val stat: StatName
)

data class StatName(
    val name: String,
    val url: String
)

fun PokeModelDetails.toDomain() = PokeItemDetails(
    id = id,
    name = name,
    type1 = types[0].type.name,
    type2 = if (types.size > 1) types[1].type.name else null,
    hp = stats[0].base_stat,
    attack = stats[1].base_stat,
    defense = stats[2].base_stat,
    specialAttack = stats[3].base_stat,
    specialDefense = stats[4].base_stat,
    speed = stats[5].base_stat,
    height = height,
    weight = weight
)