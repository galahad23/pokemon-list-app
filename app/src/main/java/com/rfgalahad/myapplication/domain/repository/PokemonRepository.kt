package com.rfgalahad.myapplication.domain.repository

import com.rfgalahad.myapplication.data.remote.PokemonDetailResponse
import com.rfgalahad.myapplication.domain.model.PokemonListItem

interface PokemonRepository {
    suspend fun getPokemonList(page: Int): List<PokemonListItem>
    suspend fun getPokemonDetail(name: String): PokemonDetailResponse
}