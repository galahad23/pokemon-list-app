package com.rfgalahad.myapplication.data.remote

data class PokemonListModel (
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonItemModel>
)