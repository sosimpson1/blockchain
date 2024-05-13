package com.example.myapplication.api

import com.example.myapplication.data.CharacterResponses
import com.example.myapplication.data.ComicResponses
import com.example.myapplication.data.SeriesResponses
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("characters")
    suspend fun getAllCharacters(
        @Query("offset") offset: Int? = 0,
        @Query("limit") limit: Int? = 20
    ): CharacterResponses

    @GET("characters")
    suspend fun searchCharacter(
        @Query("nameStartsWith") query: String,
        @Query("offset") offset: Int? = 0,
        @Query("limit") limit: Int? = 20
    ): CharacterResponses

    @GET("characters/{characterId}/comics")
    suspend fun getCharacterComics(
        @Path("characterId") characterId: String,
        @Query("offset") offset: Int? = 0,
        @Query("limit") limit: Int? = 20
    ): ComicResponses

    @GET("characters/{characterId}/series")
    suspend fun getCharacterSeries(
        @Path("characterId") characterId: String,
        @Query("offset") offset: Int? = 0,
        @Query("limit") limit: Int? = 20
    ): SeriesResponses

}