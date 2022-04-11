package com.mankart.mankgram.ui.mainmenu.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mankart.mankgram.data.UserRepository
import kotlinx.coroutines.launch

class SettingViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getThemeMode(): LiveData<Boolean> = userRepository.getThemeMode()

    fun saveThemeMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            userRepository.saveThemeMode(isDarkMode)
        }
    }

    fun getUserName(): LiveData<String> = userRepository.getUserName()

    fun getUserEmail(): LiveData<String> = userRepository.getUserEmail()

    fun getIsFirstTime(): LiveData<Boolean> = userRepository.getIsFirstTime()
    fun saveIsFirstTime(value: Boolean) {
        viewModelScope.launch {
            userRepository.saveIsFirstTime(value)
        }
    }

}