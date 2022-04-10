package com.mankart.mankgram

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.mankart.mankgram.network.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call

class UserRepository(
    private val pref: SettingPreference,
    private val apiService: ApiService,
    val appExecutors: AppExecutors
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

    suspend fun clearCache() = pref.clearCache()

    /**
     * Access data from API (Retrofit)
     */

    fun userLogin(email: String, password: String) : Call<UserResponse>  {
        val user: Map<String, String> = mapOf(
            "email" to email,
            "password" to password
        )

        return apiService.userLogin(user)
    }

    fun userRegister(name: String, email: String, password: String) : Call<UserResponse>  {
        val user: Map<String, String> = mapOf(
            "name" to name,
            "email" to email,
            "password" to password
        )

        return apiService.userRegister(user)
    }

    fun getUserStories() = apiService.getUserStories()

    fun uploadStory(photo: MultipartBody.Part, description: RequestBody) = apiService.postUserStory(photo, description)

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        @JvmStatic
        fun getInstance(
            pref: SettingPreference,
            apiService: ApiService,
            appExecutors: AppExecutors
        ) : UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(pref, apiService, appExecutors)
            }.also { instance = it }
    }

}