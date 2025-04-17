package com.rfgalahad.myapplication.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rfgalahad.myapplication.databinding.ItemPokemonBinding
import com.rfgalahad.myapplication.domain.model.PokemonDetail

class PokemonSearchAdapter(
    private val onItemClick: (PokemonDetail) -> Unit
) : RecyclerView.Adapter<PokemonSearchAdapter.PokemonViewHolder>() {

    private val pokemonList = mutableListOf<PokemonDetail>()

    fun submitList(newList: List<PokemonDetail>) {
        pokemonList.clear()
        pokemonList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    override fun getItemCount(): Int = pokemonList.size

    inner class PokemonViewHolder(private val binding: ItemPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: PokemonDetail) {
            binding.tvPokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            binding.root.setOnClickListener { onItemClick(pokemon) }
        }
    }
}