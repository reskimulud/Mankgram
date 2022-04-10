package com.mankart.mankgram.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.mankart.mankgram.R
import com.mankart.mankgram.ui.ViewModelFactory
import com.mankart.mankgram.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private lateinit var factory: ViewModelFactory
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels { factory }

    private lateinit var message: String

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())
        initObserve()

        binding.tvLogin.setOnClickListener {
            activity?.supportFragmentManager?.commit {
                replace(R.id.placeholder, LoginFragment())
                addToBackStack(null)
            }
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.registerName.text.toString()
            val email = binding.registerEmail.text.toString()
            val password = binding.registerPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                val msg = getString(R.string.fill_field)
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            } else {
                if (password.length < 6) {
                    val msg = getString(R.string.length_character)
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                } else {
                    authenticationViewModel.userRegister(name, email, password)
                    authenticationViewModel.error.observe(viewLifecycleOwner) { event ->
                        event.getContentIfNotHandled()?.let { error ->
                            if(!error) {
                                activity?.supportFragmentManager?.commit {
                                    replace(R.id.placeholder, LoginFragment())
                                    val msg = getString(R.string.register_success)
                                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                val msg = getString(R.string.register_failed)
                                Toast.makeText(requireContext(), "$message: $msg", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initObserve() {
        authenticationViewModel.message.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                message = it
            }
        }

        authenticationViewModel.loading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                loading(it)
            }
        }
    }

    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnRegister.isEnabled = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnRegister.isEnabled = true
        }
    }
}