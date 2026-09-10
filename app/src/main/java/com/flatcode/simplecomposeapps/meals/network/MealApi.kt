package com.flatcode.simplecomposeapps.meals.network

import com.flatcode.simplecomposeapps.meals.model.CategoryList
import com.flatcode.simplecomposeapps.meals.model.MealList
import com.flatcode.simplecomposeapps.meals.model.MealsByCategoryList
import com.flatcode.simplecomposeapps.utils.DATA
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealApi @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getRandomMeal(): MealList {
        return client.get("${DATA.BASE_URL_MEALS}random.php").body()
    }

    suspend fun getMealDetails(id: String): MealList {
        return client.get("${DATA.BASE_URL_MEALS}lookup.php") {
            parameter("i", id)
        }.body()
    }

    suspend fun getPopularItems(categoryName: String): MealsByCategoryList {
        return client.get("${DATA.BASE_URL_MEALS}filter.php") {
            parameter("c", categoryName)
        }.body()
    }

    suspend fun getCategories(): CategoryList {
        return client.get("${DATA.BASE_URL_MEALS}categories.php").body()
    }

    suspend fun getMealsByCategory(categoryName: String): MealsByCategoryList {
        return client.get("${DATA.BASE_URL_MEALS}filter.php") {
            parameter("c", categoryName)
        }.body()
    }
}