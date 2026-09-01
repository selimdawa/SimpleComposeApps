package com.flatcode.simplecomposeapps.rickAndMorty.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Character
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Episode
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): Flow<List<Character>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<Episode>)

    @Query("SELECT * FROM episodes")
    fun getAllEpisodes(): Flow<List<Episode>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<Location>)

    @Query("SELECT * FROM locations")
    fun getAllLocations(): Flow<List<Location>>
}