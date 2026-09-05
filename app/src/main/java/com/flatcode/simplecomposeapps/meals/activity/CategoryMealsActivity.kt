package com.flatcode.simplecomposeapps.meals.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simplecomposeapps.meals.ui.CategoryMealsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryMealsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val categoryName = intent.getStringExtra(CATEGORY_NAME) ?: ""

        setContent {
            CategoryMealsScreen(
                categoryName = categoryName,
                onBack = { finish() },
                onMealClick = { id, name, thumb ->
                    val intent = Intent(this, MealDetailsActivity::class.java).apply {
                        putExtra(MealDetailsActivity.MEAL_ID, id)
                        putExtra(MealDetailsActivity.MEAL_NAME, name)
                        putExtra(MealDetailsActivity.MEAL_THUMB, thumb)
                    }
                    startActivity(intent)
                })
        }
    }

    companion object {
        const val CATEGORY_NAME = "categoryName"
    }
}