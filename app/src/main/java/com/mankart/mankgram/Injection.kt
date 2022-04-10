package com.mankart.mankgram

import android.content.Context
import android.util.Log
import androidx.lifecycle.asLiveData
import com.mankart.mankgram.network.ApiConfig
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = SettingPreference.getInstance(context.dataStore)
        val token = runBlocking { pref.getUserToken().first() }
        val loggingInterceptor = if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ApiInterceptor(token))
            .build()

        val apiService = ApiConfig.getApiService(client)

        val appExecutors = AppExecutors()

        return UserRepository.getInstance(pref, apiService, appExecutors)
    }
}