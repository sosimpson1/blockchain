package com.example.myapplication.databases

import androidx.room.*
import com.example.myapplication.data.CharacterResult
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDaos {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterResult)

    @Delete
    suspend fun delete(character: CharacterResult)

    @Query("SELECT * FROM characters_table")
    fun getFavouriteCharacters(): Flow<List<CharacterResult>>

}