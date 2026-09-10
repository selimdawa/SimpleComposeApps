package com.flatcode.simplecomposeapps.dogs.data

import com.flatcode.simplecomposeapps.dogs.service.ApiService
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogRepository @Inject constructor(
    private val apiService: ApiService,
    private val dogDao: DogDao,
) {
    suspend fun getDogsFromApi(breed: String): Resource<List<String>> = withContext(Dispatchers.IO) {
        try {
            val lowercaseBreed = breed.lowercase()
            val response = if (" " in lowercaseBreed) {
                val parts = lowercaseBreed.split(" ")
                apiService.getSubBreedImages(parts[0], parts[1])
            } else {
                apiService.getBreedImages(lowercaseBreed)
            }

            val entities = response.images
                .filter { it.isNotBlank() }
                .map { DogEntity(it, breed) }

            dogDao.deleteDogsByBreed(breed)
            dogDao.insertDogs(entities)
            Resource.Success(entities.map { it.imageUrl })
        } catch (_: Exception) {
            Resource.Error(Strings.FAILED_LOAD_DATA)
        }
    }

    suspend fun getDogsFromDb(breed: String): List<String> = withContext(Dispatchers.IO) {
        dogDao.getDogsByBreedOnce(breed).map { it.imageUrl }
    }
}