package com.mankart.mankgram.network

import com.mankart.mankgram.UserLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    fun userLogin(
        @Body user: Map<String, String>
    ) : Call<UserLoginResponse>
}