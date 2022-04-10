package com.mankart.mankgram.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.mankart.mankgram.R
import com.mankart.mankgram.databinding.FragmentLoginBinding
import com.mankart.mankgram.ui.ViewModelFactory
import com.mankart.mankgram.ui.mainmenu.MainActivity

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private lateinit var factory: ViewModelFactory
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels { factory }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var message: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())
        initObserve()

        binding.tvRegister.setOnClickListener {
            activity?.supportFragmentManager?.commit {
                replace(R.id.placeholder, RegisterFragment())
                addToBackStack(null)
            }
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                val msg = getString(R.string.fill_field)
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            } else {
                authenticationViewModel.userLogin(email, password)
                authenticationViewModel.error.observe(viewLifecycleOwner) { event ->
                    event.getContentIfNotHandled()?.let { error ->
                        if (!error) {
                            authenticationViewModel.user.observe(viewLifecycleOwner) { event ->
                                event.getContentIfNotHandled()?.let {
                                    authenticationViewModel.saveUserToken(it.token)
                                    authenticationViewModel.saveUserName(it.name)
                                }
                            }
                            authenticationViewModel.saveUserEmail(email)
                            val intent = Intent(activity, MainActivity::class.java)
                            startActivity(intent)
                            activity?.finish()
                        } else {
                            val msg = getString(R.string.wrong_credential)
                            Toast.makeText(context, "$message: $msg", Toast.LENGTH_SHORT).show()
                            binding.loginPassword.apply {
                                text?.clear()
                                setError(null)
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
            binding.btnLogin.isEnabled = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnLogin.isEnabled = true
        }
    }

}

