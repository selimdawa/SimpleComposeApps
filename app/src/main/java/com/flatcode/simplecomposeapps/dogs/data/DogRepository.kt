package com.flatcode.simplecomposeapps.dogs.data

import com.flatcode.simplecomposeapps.dogs.service.ApiService
import com.flatcode.simplecomposeapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogRepository @Inject constructor(
    private val apiService: ApiService,
    private val dogDao: DogDao,
) {
    fun getDogsByBreed(breed: String, isOnline: Boolean): Flow<Resource<List<String>>> = flow {
        emit(Resource.Loading())
        val localData = dogDao.getDogsByBreedOnce(breed).map { it.imageUrl }
        if (localData.isNotEmpty()) {
            emit(Resource.Success(localData))
        }

        if (isOnline) {
            try {
                val lowercaseBreed = breed.lowercase()
                val response = if (" " in lowercaseBreed) {
                    val parts = lowercaseBreed.split(" ")
                    apiService.getSubBreedImages(parts[0], parts[1])
                } else {
                    apiService.getBreedImages(lowercaseBreed)
                }

                val entities = response.images.map { DogEntity(it, breed) }
                dogDao.deleteDogsByBreed(breed)
                dogDao.insertDogs(entities)

                emit(Resource.Success(entities.map { it.imageUrl }))
            } catch (e: Exception) {
                if (localData.isEmpty()) {
                    emit(Resource.Error(e.message ?: "An error occurred"))
                }
            }
        } else if (localData.isEmpty()) {
            emit(Resource.Error("No internet and no cached data"))
        }
    }.flowOn(Dispatchers.IO)
}