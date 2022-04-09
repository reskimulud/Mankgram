package com.mankart.mankgram.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mankart.mankgram.UserRepository
import kotlinx.coroutines.launch

class SettingViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getThemeMode(): LiveData<Boolean> = userRepository.getThemeMode()

    fun saveThemeMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            userRepository.saveThemeMode(isDarkMode)
        }
    }

    fun getUserToken(): LiveData<String> = userRepository.getUserToken()

    fun saveUserToken(token: String) {
        viewModelScope.launch {
            userRepository.saveUserToken(token)
        }
    }

    fun getUserName(): LiveData<String> = userRepository.getUserName()

    fun saveUserName(name: String) {
        viewModelScope.launch {
            userRepository.saveUserName(name)
        }
    }

    fun getUserEmail(): LiveData<String> = userRepository.getUserEmail()

    fun saveUserEmail(email: String) {
        viewModelScope.launch {
            userRepository.saveUserEmail(email)
        }
    }

}