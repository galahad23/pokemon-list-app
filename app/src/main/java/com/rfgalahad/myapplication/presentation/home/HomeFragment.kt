package com.rfgalahad.myapplication.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rfgalahad.myapplication.R
import com.rfgalahad.myapplication.data.remote.PokemonApiService
import com.rfgalahad.myapplication.data.remote.RetrofitInstance
import com.rfgalahad.myapplication.data.repository.PokemonRepositoryImpl
import com.rfgalahad.myapplication.databinding.FragmentHomeBinding
import com.rfgalahad.myapplication.presentation.common.BaseViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: PokemonListAdapter // assume you already made this

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel setup
        val repository = PokemonRepositoryImpl(RetrofitInstance.api)
        val factory = BaseViewModelFactory { HomeViewModel(repository) }
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        adapter = PokemonListAdapter {
            // handle item click: navigate to detail fragment
        }
        binding.rvPokemonList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPokemonList.adapter = adapter

        viewModel.pokemonList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        // Search function
//        binding.etSearch.addTextChangedListener {
//            val query = it.toString()
//            viewModel.searchPokemon(query)
//        }

        // Navigate to Profile
        binding.tvProfile.setOnClickListener {
            //findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        // Initial fetch
        viewModel.loadNextPage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}