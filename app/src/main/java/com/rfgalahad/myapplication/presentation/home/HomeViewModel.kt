package com.rfgalahad.myapplication.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rfgalahad.myapplication.domain.model.PokemonDetail
import com.rfgalahad.myapplication.domain.model.PokemonListItem
import com.rfgalahad.myapplication.domain.repository.PokemonRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel(
    private val repo: PokemonRepository
) : ViewModel() {

    private val _pokemonList = MutableLiveData<List<PokemonListItem>>()
    val pokemonList: LiveData<List<PokemonListItem>> = _pokemonList

    private val _searchResult = MutableLiveData<List<PokemonDetail>>()
    val searchResult: LiveData<List<PokemonDetail>> = _searchResult

    private var currentPage = 1

    fun searchPokemonByName(query: String) {
        viewModelScope.launch {
            try {
                val result = repo.getPokemonDetail(query.trim().lowercase())
                result?.let {
                    _searchResult.value = listOf(PokemonDetail(it.name, it.abilities))
                } ?: run {
                    _searchResult.value = emptyList()
                }
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    _searchResult.value = emptyList() // not found, don't crash
                } else {
                    e.printStackTrace()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadNextPage() {
        viewModelScope.launch {
            val result = repo.getPokemonList(currentPage)
            val current = _pokemonList.value ?: emptyList()
            _pokemonList.value = current + result
            currentPage++
        }
    }

    fun resetPagination() {
        currentPage = 1
        _pokemonList.value = emptyList()
        loadNextPage()
    }
}