package com.rfgalahad.myapplication.domain.model

import com.rfgalahad.myapplication.data.remote.AbilityItem

data class PokemonDetail(
    val name: String,
    val abilities: List<AbilityItem>
)
