package com.mankart.mankgram

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData

class UserRepository(
    private val pref: SettingPreference
) {
    /**
     * Access data from DataStore (SettingPreference)
     */

    fun getThemeMode() : LiveData<Boolean> = pref.getThemeMode().asLiveData()
    suspend fun saveThemeMode(value: Boolean) = pref.saveThemeMode(value)

    fun getUserToken() : LiveData<String> = pref.getUserToken().asLiveData()
    suspend fun saveUserToken(value: String) = pref.saveUserToken(value)

    fun getUserName() : LiveData<String> = pref.getUserName().asLiveData()
    suspend fun saveUserName(value: String) = pref.saveUserName(value)

    fun getUserEmail() : LiveData<String> = pref.getUserEmail().asLiveData()
    suspend fun saveUserEmail(value: String) = pref.saveUserEmail(value)

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        @JvmStatic
        fun getInstance(
            pref: SettingPreference,
        ) : UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(pref)
            }.also { instance = it }
    }

}