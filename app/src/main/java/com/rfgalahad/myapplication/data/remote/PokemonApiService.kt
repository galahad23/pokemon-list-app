package com.rfgalahad.myapplication.data.remote

import com.rfgalahad.myapplication.domain.model.PokemonListItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int = 0
    ): PokemonListModel

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name: String
    ): PokemonDetailResponse
}