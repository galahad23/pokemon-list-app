package com.rfgalahad.myapplication.domain.repository

import com.rfgalahad.myapplication.domain.model.PokemonDetail
import com.rfgalahad.myapplication.domain.model.PokemonListItem

interface PokemonRepository {
    suspend fun getPokemonList(page: Int): List<PokemonListItem>
    suspend fun getPokemonDetail(name: String): PokemonDetail
}