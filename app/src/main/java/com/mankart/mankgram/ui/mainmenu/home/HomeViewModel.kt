package com.mankart.mankgram.ui.mainmenu.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mankart.mankgram.model.StoryModel
import com.mankart.mankgram.data.UserRepository
import com.mankart.mankgram.data.network.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

    private var _userStories = MutableLiveData<ArrayList<StoryModel>>()
    val userStories: LiveData<ArrayList<StoryModel>> = _userStories

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

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
                _message.value = t.message
            }
        })
    }

}