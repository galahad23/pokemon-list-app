package com.rfgalahad.myapplication.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rfgalahad.myapplication.data.remote.PokemonDetailResponse
import com.rfgalahad.myapplication.data.repository.PokemonRepositoryImpl
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val repo: PokemonRepositoryImpl
) : ViewModel() {

    private val _pokemonDetail = MutableLiveData<PokemonDetailResponse>()
    val pokemonDetail: LiveData<PokemonDetailResponse> = _pokemonDetail

    fun loadPokemonDetail(name: String) {
        viewModelScope.launch {
            val detail = repo.getPokemonDetail(name)
            _pokemonDetail.value = detail
        }
    }
}