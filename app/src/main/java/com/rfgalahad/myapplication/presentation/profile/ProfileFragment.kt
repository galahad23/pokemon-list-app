package com.rfgalahad.myapplication.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rfgalahad.myapplication.R
import com.rfgalahad.myapplication.data.repository.UserRepositoryProvider
import com.rfgalahad.myapplication.databinding.FragmentProfileBinding
import com.rfgalahad.myapplication.presentation.common.BaseViewModelFactory
import com.rfgalahad.myapplication.presentation.common.UserSessionManager

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel
    private lateinit var sessionManager: UserSessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val userRepository = UserRepositoryProvider.provide(requireContext())
        val factory = BaseViewModelFactory {
            ProfileViewModel(userRepository)
        }
        viewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = UserSessionManager(requireContext())

        val userEmail = sessionManager.getUserEmail()

        if (userEmail.isNullOrEmpty()) {
            findNavController().navigate(R.id.loginFragment)
            return
        }

        viewModel.loadUser(userEmail)

        viewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.textViewName.text = user.username
                binding.textViewEmail.text = user.email
            }
        }

        binding.buttonLogout.setOnClickListener {
            sessionManager.clearSession()
            viewModel.logout()
            findNavController().navigate(R.id.loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}