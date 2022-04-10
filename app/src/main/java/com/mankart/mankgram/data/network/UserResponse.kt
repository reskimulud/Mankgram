package com.mankart.mankgram.data.network

import com.google.gson.annotations.SerializedName
import com.mankart.mankgram.model.StoryModel
import com.mankart.mankgram.model.UserModel

data class UserResponse(

    @field:SerializedName("message")
	val message: String? = null,

    @field:SerializedName("loginResult")
	val loginResult: UserModel? = null,

    @field:SerializedName("listStory")
	val listStory: ArrayList<StoryModel>? = null,

    )
