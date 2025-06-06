package com.rfgalahad.myapplication.data.repository

import com.rfgalahad.myapplication.data.remote.PokemonApiService
import com.rfgalahad.myapplication.data.remote.PokemonDetailResponse
import com.rfgalahad.myapplication.domain.model.PokemonListItem
import com.rfgalahad.myapplication.domain.repository.PokemonRepository

class PokemonRepositoryImpl(
    private val apiService: PokemonApiService
) : PokemonRepository {

    override suspend fun getPokemonList(page: Int): List<PokemonListItem> {
        val offset = (page - 1) * 10
        val response = apiService.getPokemonList(limit = 10, offset = offset)
        return response.results.map {
            PokemonListItem(
                name = it.name,
                url = it.url
            )
        }
    }

    override suspend fun getPokemonDetail(name: String): PokemonDetailResponse {
        val detail = apiService.getPokemonDetail(name)
        return PokemonDetailResponse(
            name = detail.name,
            abilities = detail.abilities
        )
    }
}