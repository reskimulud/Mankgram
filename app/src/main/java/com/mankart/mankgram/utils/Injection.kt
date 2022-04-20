package com.mankart.mankgram.utils

import android.content.Context
import com.mankart.mankgram.BuildConfig
import com.mankart.mankgram.data.datastore.SettingPreference
import com.mankart.mankgram.data.UserRepository
import com.mankart.mankgram.data.database.UserStoryDatabase
import com.mankart.mankgram.data.network.ApiConfig
import com.mankart.mankgram.ui.mainmenu.dataStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val appExecutors = AppExecutors()
        val pref = SettingPreference.getInstance(context.dataStore)

        val loggingInterceptor = if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val userStoryDatabase = UserStoryDatabase.getDatabase(context)

        val apiService = ApiConfig.getApiService(client)

        return UserRepository.getInstance(pref, apiService, userStoryDatabase, appExecutors)
    }
}