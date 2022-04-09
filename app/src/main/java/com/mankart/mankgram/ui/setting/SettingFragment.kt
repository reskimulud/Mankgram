package com.mankart.mankgram.ui.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mankart.mankgram.ViewModelFactory
import com.mankart.mankgram.authentication.AuthenticationActivity
import com.mankart.mankgram.authentication.AuthenticationViewModel
import com.mankart.mankgram.databinding.FragmentSettingBinding
import java.util.*

class SettingFragment : Fragment() {
    private lateinit var factory: ViewModelFactory
    private val settingViewModel: SettingViewModel by activityViewModels { factory }
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels { factory }
    private var _binding: FragmentSettingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factory = ViewModelFactory.getInstance(requireActivity())

        initObserve()
        initView()
    }

    private fun initView() {
        binding.setLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        binding.localName.text = Locale.getDefault().displayName
        binding.btnLogout.setOnClickListener {
            startActivity(Intent(activity, AuthenticationActivity::class.java))
            activity?.finish()
        }

        binding.switchMode.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeMode(isChecked)
        }

        binding.btnLogout.setOnClickListener {
            authenticationViewModel.logout()
            startActivity(Intent(activity, AuthenticationActivity::class.java))
            activity?.finish()
        }
    }

    private fun initObserve() {
        settingViewModel.getThemeMode().observe(viewLifecycleOwner) { isNightMode ->
            if (isNightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            binding.switchMode.isChecked = isNightMode
        }

        authenticationViewModel.getUserName().observe(viewLifecycleOwner) { userName ->
            binding.tvName.text = userName
        }
        authenticationViewModel.getUserEmail().observe(viewLifecycleOwner) { userEmail ->
            binding.tvEmail.text = userEmail
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}