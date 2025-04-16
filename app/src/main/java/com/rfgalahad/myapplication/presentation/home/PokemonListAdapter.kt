package com.rfgalahad.myapplication.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rfgalahad.myapplication.databinding.ItemPokemonBinding
import com.rfgalahad.myapplication.domain.model.PokemonListItem

class PokemonListAdapter(
    private val onItemClick: (PokemonListItem) -> Unit
) : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    private val pokemonList = mutableListOf<PokemonListItem>()

    fun submitList(newList: List<PokemonListItem>) {
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
        fun bind(pokemon: PokemonListItem) {
            binding.tvPokemonName.text = pokemon.name.replaceFirstChar { it.uppercase() }
            binding.root.setOnClickListener { onItemClick(pokemon) }
        }
    }
}