package com.rfgalahad.myapplication.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rfgalahad.myapplication.domain.model.PokemonListItem
import com.rfgalahad.myapplication.domain.repository.PokemonRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repo: PokemonRepository
) : ViewModel() {

    private val _pokemonList = MutableLiveData<List<PokemonListItem>>()
    val pokemonList: LiveData<List<PokemonListItem>> = _pokemonList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var currentPage = 1

    fun loadNextPage() {
        viewModelScope.launch {
            _isLoading.value = true
            val newList = repo.getPokemonList(currentPage)
            val currentList = _pokemonList.value.orEmpty()
            _pokemonList.value = currentList + newList
            currentPage++
            _isLoading.value = false
        }
    }

    fun resetPagination() {
        currentPage = 1
        _pokemonList.value = emptyList()
        loadNextPage()
    }
}