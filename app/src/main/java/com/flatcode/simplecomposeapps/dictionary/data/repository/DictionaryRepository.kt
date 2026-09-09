package com.flatcode.simplecomposeapps.dictionary.data.repository

import com.flatcode.simplecomposeapps.dictionary.data.local.WordDao
import com.flatcode.simplecomposeapps.dictionary.data.local.WordEntity
import com.flatcode.simplecomposeapps.dictionary.service.DictionaryAPI
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionaryRepository @Inject constructor(
    private val api: DictionaryAPI,
    private val dao: WordDao
) {
    suspend fun getWordDefinition(word: String): Resource<String> = withContext(Dispatchers.IO) {
        try {
            val localWord = dao.getWordDefinition(word)
            if (localWord != null) {
                return@withContext Resource.Success(localWord.definition)
            }

            val response = api.getDefinition(word, DATA.DICTIONARY_API_KEY)
            if (response.isNotEmpty()) {
                val definition = response[0].shortdef?.joinToString("\n") ?: "No definition found"
                dao.insertWord(WordEntity(word, definition))
                Resource.Success(definition)
            } else {
                Resource.Error("No definition found")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }
}