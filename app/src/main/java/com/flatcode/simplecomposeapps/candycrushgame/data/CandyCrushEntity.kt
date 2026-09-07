package com.flatcode.simplecomposeapps.candycrushgame.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "candy_crush_scores")
data class CandyCrushEntity(
    @PrimaryKey val id: Int = 1,
    val highScore: Int,
    val score: Int = 0,
    val boardState: String = ""
)