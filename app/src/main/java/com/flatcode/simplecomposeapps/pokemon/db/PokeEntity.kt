package com.flatcode.simplecomposeapps.pokemon.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flatcode.simplecomposeapps.pokemon.model.PokeItem

@Entity(tableName = "pokemon_table")
data class PokeEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val url: String
)

fun PokeEntity.toDomain() = PokeItem(id, name, url)