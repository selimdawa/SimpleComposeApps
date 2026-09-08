package com.flatcode.simplecomposeapps.main

import android.app.Activity
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.main.data.MainDao
import com.flatcode.simplecomposeapps.main.data.MainEntity
import com.flatcode.simplecomposeapps.ui.theme.AppIcons
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainDao: MainDao
) : ViewModel() {

    val dataMain: LiveData<List<Main>> = mainDao.getAllItems().map { entities ->
        entities.map { mapFromEntity(it) }
    }.asLiveData()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(true)

    init {
        checkAndPopulate()
    }

    private fun checkAndPopulate() {
        viewModelScope.launch {
            val items = mainDao.getAllItems().first()
            val currentData = DATA.MAIN_DATA.map { mapToEntity(it) }
            
            if (items.size != currentData.size || items.any { it.activityClassName.isEmpty() }) {
                mainDao.deleteAll()
                mainDao.insertAll(currentData)
            } else {
                mainDao.insertAll(currentData)
            }
            isLoading.postValue(false)
        }
    }

    fun getItems() {
        // Handled by LiveData from Room
    }

    private fun mapToEntity(main: Main): MainEntity {
        val (type, value) = when (val img = main.image) {
            is ImageVector -> "IMAGE_VECTOR" to getImageVectorName(img)
            is Int -> "RESOURCE_ID" to img.toString()
            else -> "UNKNOWN" to ""
        }
        return MainEntity(
            title = main.title ?: "",
            imageType = type,
            imageValue = value,
            number = main.number,
            activityClassName = main.c?.name ?: ""
        )
    }

    private fun mapFromEntity(entity: MainEntity): Main {
        val image = when (entity.imageType) {
            "IMAGE_VECTOR" -> getImageVectorByName(entity.imageValue)
            "RESOURCE_ID" -> entity.imageValue.toIntOrNull()
            else -> null
        }
        val activityClass = try {
            Class.forName(entity.activityClassName).asSubclass(Activity::class.java)
        } catch (_: Exception) {
            null
        }
        return Main(image, entity.title, entity.number, activityClass)
    }

    private fun getImageVectorName(vector: ImageVector): String {
        return when (vector) {
            AppIcons.StopWatch -> "StopWatch"
            AppIcons.MultiDelete -> "MultiDelete"
            AppIcons.PdfReader -> "PdfReader"
            AppIcons.VideoPlayer -> "VideoPlayer"
            AppIcons.Dogs -> "Dogs"
            AppIcons.Countries -> "Countries"
            AppIcons.Calculator -> "Calculator"
            AppIcons.Crypto -> "Crypto"
            AppIcons.Dictionary -> "Dictionary"
            AppIcons.Meals -> "Meals"
            AppIcons.Pop -> "Pop"
            AppIcons.Movie -> "Movie"
            AppIcons.News -> "News"
            AppIcons.RickAndMorty -> "RickAndMorty"
            AppIcons.Weather -> "Weather"
            AppIcons.Poke -> "Poke"
            AppIcons.TodoNote -> "TodoNote"
            AppIcons.StockMarket -> "StockMarket"
            else -> ""
        }
    }

    private fun getImageVectorByName(name: String): ImageVector? {
        return when (name) {
            "StopWatch" -> AppIcons.StopWatch
            "MultiDelete" -> AppIcons.MultiDelete
            "PdfReader" -> AppIcons.PdfReader
            "VideoPlayer" -> AppIcons.VideoPlayer
            "Dogs" -> AppIcons.Dogs
            "Countries" -> AppIcons.Countries
            "Calculator" -> AppIcons.Calculator
            "Crypto" -> AppIcons.Crypto
            "Dictionary" -> AppIcons.Dictionary
            "Meals" -> AppIcons.Meals
            "Pop" -> AppIcons.Pop
            "Movie" -> AppIcons.Movie
            "News" -> AppIcons.News
            "RickAndMorty" -> AppIcons.RickAndMorty
            "Weather" -> AppIcons.Weather
            "Poke" -> AppIcons.Poke
            "TodoNote" -> AppIcons.TodoNote
            "StockMarket" -> AppIcons.StockMarket
            else -> null
        }
    }

}