package com.mankart.mankgram.data.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mankart.mankgram.BuildConfig.CIPHER_KEY
import com.mankart.mankgram.BuildConfig.IV
import com.mankart.mankgram.ui.mapviewstory.MapStyle
import com.mankart.mankgram.ui.mapviewstory.MapType
import io.github.reskimulud.encrypteddatastore.EncryptedDataStore.secureEdit
import io.github.reskimulud.encrypteddatastore.EncryptedDataStore.secureMap
import io.github.reskimulud.encrypteddatastore.algorithm.aes.AES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreference private constructor(
    private val dataStore: DataStore<Preferences>,
    private val aes: AES,
) {

    /**
     * Dark Mode
     */

    fun getThemeMode(): Flow<Boolean> = dataStore.data.map {
        it[THEME_MODE_KEY] ?: false
    }

    suspend fun saveThemeMode(themeMode: Boolean) {
        dataStore.edit {
            it[THEME_MODE_KEY] = themeMode
            if (themeMode) {
                it[MAP_STYLE_KEY] = MapStyle.NIGHT.name
            } else {
                it[MAP_STYLE_KEY] = MapStyle.NORMAL.name
            }
        }
    }

    /**
     * User Token
     */

    fun getUserToken(): Flow<String> = dataStore.data.secureMap(aes) {
        it[USER_TOKEN_KEY] ?: DEFAULT_VALUE
    }

    suspend fun saveUserToken(token: String) {
        dataStore.secureEdit(token, aes) { preferences, encryptedToken ->
            preferences[USER_TOKEN_KEY] = encryptedToken
            Log.e("SettingPreference", "Token saved! saveUserToken: $token")
        }
    }

    /**
     * User Email
     */

    fun getUserEmail(): Flow<String> = dataStore.data.secureMap(aes) {
        it[USER_EMAIL_KEY] ?: DEFAULT_VALUE
    }

    suspend fun saveUserEmail(email: String) {
        dataStore.secureEdit(email, aes) { preferences, encryptedEmail ->
            preferences[USER_EMAIL_KEY] = encryptedEmail
        }
    }

    /**
     * User Name
     */

    fun getUserName(): Flow<String> = dataStore.data.secureMap(aes) {
        it[USER_NAME_KEY] ?: DEFAULT_VALUE
    }

    suspend fun saveUserName(name: String) {
        dataStore.secureEdit(name, aes) { preferences, encryptedName ->
            preferences[USER_NAME_KEY] = encryptedName
        }
    }

    /**
     * First Time
     */

    fun isFirstTime(): Flow<Boolean> = dataStore.data.map {
        it[FIRST_TIME_KEY] ?: true
    }

    suspend fun saveIsFirstTime(firstTime: Boolean) {
        dataStore.edit {
            it[FIRST_TIME_KEY] = firstTime
        }
    }

    /**
     * Map Type
     */

    fun getMapType(): Flow<MapType> = dataStore.data.map {
        when (it[MAP_TYPE_KEY]) {
            MapType.NORMAL.name -> MapType.NORMAL
            MapType.SATELLITE.name -> MapType.SATELLITE
            MapType.TERRAIN.name -> MapType.TERRAIN
            else -> MapType.NORMAL
        }
    }

    suspend fun saveMapType(mapType: MapType) {
        dataStore.edit {
            it[MAP_TYPE_KEY] = when (mapType) {
                MapType.NORMAL -> MapType.NORMAL.name
                MapType.SATELLITE -> MapType.SATELLITE.name
                MapType.TERRAIN -> MapType.TERRAIN.name
            }
        }
    }

    /**
     * Map Style
     */

    fun getMapStyle(): Flow<MapStyle> = dataStore.data.map {
        when (it[MAP_STYLE_KEY]) {
            MapStyle.NORMAL.name -> MapStyle.NORMAL
            MapStyle.NIGHT.name -> MapStyle.NIGHT
            MapStyle.SILVER.name -> MapStyle.SILVER
            else -> MapStyle.NORMAL
        }
    }

    suspend fun saveMapStyle(mapStyle: MapStyle) {
        dataStore.edit {
            it[MAP_STYLE_KEY] = when (mapStyle) {
                MapStyle.NORMAL -> MapStyle.NORMAL.name
                MapStyle.NIGHT -> MapStyle.NIGHT.name
                MapStyle.SILVER -> MapStyle.SILVER.name
            }
        }
    }

    suspend fun clearCache() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        private val THEME_MODE_KEY = booleanPreferencesKey("theme_mode")
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val FIRST_TIME_KEY = booleanPreferencesKey("first_time")
        private val MAP_TYPE_KEY = stringPreferencesKey("map_type")
        private val MAP_STYLE_KEY = stringPreferencesKey("map_style")

        @Volatile
        private var INSTANCE: SettingPreference? = null
        const val DEFAULT_VALUE = "not_set_yet"

        fun getInstance(dataStore: DataStore<Preferences>) : SettingPreference {
            return INSTANCE ?: synchronized(this) {
                val aes: AES = AES.Builder()
                    .setKey(CIPHER_KEY)
                    .setIv(IV)
                    .build()
                val instance = SettingPreference(dataStore, aes)
                INSTANCE = instance
                instance
            }
        }
    }
}