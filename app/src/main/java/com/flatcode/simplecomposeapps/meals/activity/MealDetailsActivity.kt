package com.flatcode.simplecomposeapps.meals.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simplecomposeapps.meals.ui.MealDetailScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val id = intent.getStringExtra(MEAL_ID) ?: ""
        val thumb = intent.getStringExtra(MEAL_THUMB) ?: ""

        setContent {
            MealDetailScreen(
                id = id, thumb = thumb, onBack = { finish() })
        }
    }

    companion object {
        const val MEAL_ID = "id"
        const val MEAL_NAME = "name"
        const val MEAL_THUMB = "thumb"
    }
}