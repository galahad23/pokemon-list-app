package com.rfgalahad.myapplication.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rfgalahad.myapplication.data.remote.RetrofitInstance
import com.rfgalahad.myapplication.data.repository.PokemonRepositoryImpl
import com.rfgalahad.myapplication.databinding.FragmentPokemonDetailsBinding
import com.rfgalahad.myapplication.presentation.common.BaseViewModelFactory

class PokemonDetailsFragment : Fragment() {

    private var _binding: FragmentPokemonDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PokemonDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val pokemonName = arguments?.getString("pokemonName") ?: return

        val repository = PokemonRepositoryImpl(RetrofitInstance.api)
        val factory = BaseViewModelFactory { PokemonDetailsViewModel(repository) }
        viewModel = ViewModelProvider(this, factory)[PokemonDetailsViewModel::class.java]

        viewModel.pokemonDetail.observe(viewLifecycleOwner) { detail ->
            binding.tvName.text = detail.name.replaceFirstChar { it.uppercase() }
            binding.tvAbilities.text = detail.abilities.joinToString("\n") {
                it.ability.name.replaceFirstChar { ch -> ch.uppercase() }
            }
        }

        viewModel.loadPokemonDetail(pokemonName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}