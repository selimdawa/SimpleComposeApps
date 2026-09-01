package com.flatcode.simplecomposeapps.pokemon.domain.model

import com.flatcode.simplecomposeapps.pokemon.data.database.entities.PokeEntity
import com.flatcode.simplecomposeapps.pokemon.data.model.PokeResult

data class PokeItem(
    val id: Int,
    val name: String,
    val url: String
)

fun PokeResult.toDatabase(): PokeEntity {
    val id = url.split("/").filter { it.isNotEmpty() }.last().toInt()
    return PokeEntity(id, name, url)
}