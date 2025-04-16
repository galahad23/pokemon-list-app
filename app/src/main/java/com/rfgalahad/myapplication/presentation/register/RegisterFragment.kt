package com.rfgalahad.myapplication.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rfgalahad.myapplication.R
import com.rfgalahad.myapplication.data.local.UserModel
import com.rfgalahad.myapplication.data.repository.UserRepositoryProvider
import com.rfgalahad.myapplication.databinding.FragmentRegisterBinding
import com.rfgalahad.myapplication.presentation.common.BaseViewModelFactory

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val userRepository = UserRepositoryProvider.provide(requireContext())
        val factory = BaseViewModelFactory {
            RegisterViewModel(userRepository)
        }

        viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnRegister.setOnClickListener {
            val name = binding.editName.text.toString()
            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            val newUser = UserModel(
                id = 0,
                username = name,
                email = email,
                password = password
            )

            viewModel.register(newUser).observe(viewLifecycleOwner) { success ->
                if (success) {
                    Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                } else {
                    Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}