package com.mankart.mankgram.data.network

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
    fun getUserStories(
        @Query("location") location: Int,
    ) : Call<UserResponse>

    @Multipart
    @POST("stories")
    fun postUserStory(
        @Part photo : MultipartBody.Part,
        @Part("description") description: RequestBody
    ) : Call<UserResponse>
}