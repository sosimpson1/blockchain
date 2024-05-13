package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.CharacterResult
import com.example.myapplication.data.ComicResults
import com.example.myapplication.data.SeriesResults
import com.example.myapplication.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(private val repository: CharacterRepository) : ViewModel() {

    private val _characters = MutableStateFlow<List<CharacterResult>>(emptyList())
    val characters: StateFlow<List<CharacterResult>> = _characters



    suspend fun getAllCharacters(offset: Int? = 0, limit: Int? = 20): List<CharacterResult> {
        return repository.getAllCharacters(offset, limit)
    }
//Todo fix the search box
    fun searchCharacter(query: String, offset: Int? = 0, limit: Int? = 20) {
        viewModelScope.launch {
            val result = repository.searchCharacter(query, offset, limit)
            _characters.value = result
        }
    }

    suspend fun getCharacterComics(characterId: String, offset: Int? = 0, limit: Int? = 20): List<ComicResults> {
        return repository.getCharacterComics(characterId, offset, limit)
    }

    suspend fun getCharacterSeries(characterId: String, offset: Int? = 0, limit: Int? = 20):List<SeriesResults>  {
          return repository.getCharacterSeries(characterId, offset, limit)
        }

    fun addCharacterToFavourite(characterResult: CharacterResult) {
        viewModelScope.launch {
            repository.addCharacterToFavourite(characterResult)
        }
    }

    fun removeCharacterFromFavourite(characterResult: CharacterResult) {
        viewModelScope.launch {
            repository.removeCharacterFromFavourite(characterResult)
        }
    }

    fun getFavouriteCharacters(): Flow<List<CharacterResult>> {
        return repository.getFavouriteCharacters()
    }
}






