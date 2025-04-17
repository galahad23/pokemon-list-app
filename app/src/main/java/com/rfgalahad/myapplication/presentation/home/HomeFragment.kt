package com.rfgalahad.myapplication.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rfgalahad.myapplication.data.remote.RetrofitInstance
import com.rfgalahad.myapplication.data.repository.PokemonRepositoryImpl
import com.rfgalahad.myapplication.databinding.FragmentHomeBinding
import com.rfgalahad.myapplication.presentation.common.BaseViewModelFactory
import com.rfgalahad.myapplication.presentation.common.EndlessScrollListener

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private lateinit var mainAdapter: PokemonListAdapter
    private lateinit var dropdownAdapter: PokemonSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = PokemonRepositoryImpl(RetrofitInstance.api)
        val factory = BaseViewModelFactory { HomeViewModel(repository) }
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        mainAdapter = PokemonListAdapter { pokemon ->
            val action = HomeFragmentDirections
                .actionHomeFragmentToPokemonDetailFragment(pokemon.name)
            findNavController().navigate(action)
        }

        binding.rvPokemonList.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.loadNextPage()

        viewModel.pokemonList.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                Toast.makeText(requireContext(), "No Pokémon found", Toast.LENGTH_SHORT).show()
            }
            mainAdapter.submitList(list)
        }

        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val query = binding.etSearch.text.toString().trim()
                if (query.isNotEmpty()) {
                    viewModel.searchPokemonByName(query)
                }
                true // Consume the action
            } else {
                false
            }
        }

        viewModel.searchResult.observe(viewLifecycleOwner) { results ->
            if (results.isNotEmpty()) {
                binding.searchDropdownRecycler.visibility = View.VISIBLE
                dropdownAdapter.submitList(results)
            } else {
                binding.searchDropdownRecycler.visibility = View.GONE
            }
        }

        dropdownAdapter = PokemonSearchAdapter { result ->
            val action = HomeFragmentDirections
                .actionHomeFragmentToPokemonDetailFragment(result.name)
            findNavController().navigate(action)
            binding.searchDropdownRecycler.visibility = View.GONE
        }

        binding.searchDropdownRecycler.apply {
            adapter = dropdownAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.rvPokemonList.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                viewModel.loadNextPage()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}