package com.mankart.mankgram.utils

import android.content.Context
import com.mankart.mankgram.BuildConfig
import com.mankart.mankgram.data.datastore.SettingPreference
import com.mankart.mankgram.data.UserRepository
import com.mankart.mankgram.data.network.ApiConfig
import com.mankart.mankgram.ui.mainmenu.dataStore
import kotlinx.coroutines.flow.first
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