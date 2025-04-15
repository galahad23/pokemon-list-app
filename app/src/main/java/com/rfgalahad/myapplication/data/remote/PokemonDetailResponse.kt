package com.rfgalahad.myapplication.data.remote

data class PokemonDetailResponse(
    val name: String,
    val abilities: List<AbilityItem>
)
