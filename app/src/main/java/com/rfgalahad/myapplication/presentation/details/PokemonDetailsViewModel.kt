package com.rfgalahad.myapplication.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rfgalahad.myapplication.data.repository.PokemonRepositoryImpl
import com.rfgalahad.myapplication.domain.model.PokemonDetail
import kotlinx.coroutines.launch

class PokemonDetailsViewModel(
    private val repo: PokemonRepositoryImpl
) : ViewModel() {

    private val _pokemonDetail = MutableLiveData<PokemonDetail>()
    val pokemonDetail: LiveData<PokemonDetail> = _pokemonDetail

    fun loadPokemonDetail(name: String) {
        viewModelScope.launch {
            val detail = repo.getPokemonDetail(name)
            _pokemonDetail.value = detail
        }
    }
}