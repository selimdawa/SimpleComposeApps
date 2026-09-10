package com.flatcode.simplecomposeapps.pokemon.model

import com.flatcode.simplecomposeapps.pokemon.db.PokeEntity

data class PokeItem(
    val id: Int,
    val name: String,
    val url: String
) {
    val formatId = "N° ${id.toString().padStart(3, '0')}"
}

fun PokeResult.toDatabase(): PokeEntity {
    val id = url.split("/").last { it.isNotEmpty() }.toInt()
    return PokeEntity(id, name, url)
}