package com.mankart.mankgram

import android.content.Context

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = SettingPreference.getInstance(context.dataStore)

        return UserRepository.getInstance(pref)
    }
}