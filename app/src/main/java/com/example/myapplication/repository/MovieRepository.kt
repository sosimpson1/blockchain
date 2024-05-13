package com.example.myapplication.repository

import com.example.myapplication.api.MoviesApi
import com.example.myapplication.data.CharacterResult
import com.example.myapplication.databases.CharacterDaos
import com.example.myapplication.data.ComicResults
import com.example.myapplication.data.SeriesResults
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(private val moviesApi: MoviesApi, private val characterDaos: CharacterDaos) {


    suspend fun getAllCharacters(offset: Int? = 0, limit: Int? = 20): List<CharacterResult> {
        return moviesApi.getAllCharacters(offset, limit).data.results
    }

    suspend fun searchCharacter(query: String, offset: Int? = 0, limit: Int? = 20): List<CharacterResult> {
        return moviesApi.searchCharacter(query, offset, limit).data.results
    }

    suspend fun getCharacterComics(characterId: String, offset: Int? = 0, limit: Int? = 20): List<ComicResults> {
        return moviesApi.getCharacterComics(characterId, offset, limit).data.results
    }

    suspend fun getCharacterSeries(characterId: String, offset: Int? = 0, limit: Int? = 20): List<SeriesResults> {
        return moviesApi.getCharacterSeries(characterId, offset, limit).data.results
    }

    suspend fun addCharacterToFavourite(characterResult: CharacterResult) {
        characterDaos.insert(characterResult)
    }

    suspend fun removeCharacterFromFavourite(characterResult: CharacterResult) {
        characterDaos.delete(characterResult)
    }

    fun getFavouriteCharacters(): Flow<List<CharacterResult>> {
        return characterDaos.getFavouriteCharacters()
    }
}