package com.mankart.mankgram

import android.content.Context
import com.mankart.mankgram.network.ApiConfig

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = SettingPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        val appExecutors = AppExecutors()

        return UserRepository.getInstance(pref, apiService, appExecutors)
    }
}