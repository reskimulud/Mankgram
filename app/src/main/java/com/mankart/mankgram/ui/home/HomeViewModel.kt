package com.mankart.mankgram.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mankart.mankgram.UserRepository

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getName(): LiveData<String> {
        return userRepository.getUserName()
    }

}