package com.mankart.mankgram.network

import com.mankart.mankgram.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("login")
    fun userLogin(
        @Body user: Map<String, String>
    ) : Call<UserResponse>

    @POST("register")
    fun userRegister(
        @Body user: Map<String, String>
    ) : Call<UserResponse>

    @GET("stories")
    fun getUserStories() : Call<UserResponse>

    @Multipart
    @POST("stories")
    fun postUserStory(
        @Part photo : MultipartBody.Part,
        @Part("description") description: RequestBody
    ) : Call<UserResponse>
}