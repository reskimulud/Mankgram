package com.mankart.mankgram.ui.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mankart.mankgram.authentication.AuthenticationActivity
import com.mankart.mankgram.databinding.FragmentSettingBinding
import java.util.*

class SettingFragment : Fragment() {
    private val settingViewModel: SettingViewModel by activityViewModels()
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
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        settingViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        binding.setLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        binding.localName.text = Locale.getDefault().displayName
        binding.btnLogout.setOnClickListener {
            startActivity(Intent(activity, AuthenticationActivity::class.java))
            activity?.finish()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}