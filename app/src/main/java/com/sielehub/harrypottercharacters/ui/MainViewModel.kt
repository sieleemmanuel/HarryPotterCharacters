package com.sielehub.harrypottercharacters.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sielehub.harrypottercharacters.util.Resource
import com.sielehub.harrypottercharacters.data.model.Character
import com.sielehub.harrypottercharacters.data.repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository):ViewModel() {

    private val _characters = MutableStateFlow<Resource<List<Character>>>(Resource.Loading())
    val characters = _characters.asStateFlow()

    private val _character = MutableStateFlow<Resource<Character>>(Resource.Loading())
    val character = _character.asStateFlow()

    private val _searchedCharacter = MutableStateFlow(emptyList<Character>())
    val searchedCharacter = _searchedCharacter.asStateFlow()

    fun getCharacters() {
        viewModelScope.launch {
            _characters.value = Resource.Loading()
            _characters.value = mainRepository.getCharacters()
        }
    }

    fun searchCharacter(characterList: List<Character>, query: String): List<Character> {
        return characterList.filter {
            it.name.lowercase().contains(query.lowercase()) ||
                    it.house.lowercase().contains(query)
        } ?: emptyList()
    }



    init {
        getCharacters()
    }

}