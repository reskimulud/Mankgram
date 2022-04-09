package com.mankart.mankgram.ui.home

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mankart.mankgram.StoryModel
import com.mankart.mankgram.UserRepository
import com.mankart.mankgram.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    private var _userStories = MutableLiveData<ArrayList<StoryModel>>()
    val userStories: LiveData<ArrayList<StoryModel>> = _userStories

    fun getToken() : LiveData<String> {
        return userRepository.getUserToken()
    }

    fun getName(): LiveData<String> {
        return userRepository.getUserName()
    }

    fun getUserStories() {
        val client = userRepository.getUserStories()
        client.enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()?.listStory
                    userRepository.appExecutors.networkIO.execute {
                        _userStories.postValue(userResponse!!)
                    }
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

}