package com.flatcode.simplecomposeapps.dogs.data

import com.flatcode.simplecomposeapps.dogs.service.ApiService
import com.flatcode.simplecomposeapps.utils.BaseRepository
import com.flatcode.simplecomposeapps.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogRepository @Inject constructor(
    private val apiService: ApiService,
    private val dogDao: DogDao,
) : BaseRepository() {
    fun getDogsByBreed(breed: String, isOnline: Boolean): Flow<Resource<List<String>>> =
        networkBoundResource(
            query = { dogDao.getDogsByBreed(breed).map { list -> list.map { it.imageUrl } } },
            fetch = {
                val lowercaseBreed = breed.lowercase()
                if (" " in lowercaseBreed) {
                    val parts = lowercaseBreed.split(" ")
                    apiService.getSubBreedImages(parts[0], parts[1])
                } else {
                    apiService.getBreedImages(lowercaseBreed)
                }
            },
            saveFetchResult = { response ->
                val entities = response.images
                    .filter { it.isNotBlank() }
                    .map { DogEntity(it, breed) }
                dogDao.deleteDogsByBreed(breed)
                dogDao.insertDogs(entities)
            },
            shouldFetch = { isOnline }
        )
}