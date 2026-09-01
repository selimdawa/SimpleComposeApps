package com.flatcode.simplecomposeapps.dictionary.data.repository

import com.flatcode.simplecomposeapps.dictionary.data.local.WordDao
import com.flatcode.simplecomposeapps.dictionary.data.local.WordEntity
import com.flatcode.simplecomposeapps.dictionary.service.DictionaryAPI
import com.flatcode.simplecomposeapps.utils.DATA
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionaryRepository @Inject constructor(
    private val api: DictionaryAPI,
    private val dao: WordDao
) {
    suspend fun getWordDefinition(word: String): String {
        val localWord = dao.getWordDefinition(word)
        if (localWord != null) {
            return localWord.definition
        }

        val response = api.getDefinition(word, DATA.DICTIONARY_API_KEY)
        if (response.isNotEmpty()) {
            val definition = response[0].shortdef?.joinToString("\n") ?: "No definition found"
            dao.insertWord(WordEntity(word, definition))
            return definition
        }
        return "No definition found"
    }
}