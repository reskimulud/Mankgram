package com.mankart.mankgram.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mankart.mankgram.data.UserRepository
import com.mankart.mankgram.ui.authentication.AuthenticationViewModel
import com.mankart.mankgram.ui.mainmenu.home.HomeViewModel
import com.mankart.mankgram.ui.mainmenu.newstory.NewStoryViewModel
import com.mankart.mankgram.ui.mainmenu.setting.SettingViewModel
import com.mankart.mankgram.utils.Injection

class ViewModelFactory private constructor(private val userRepository: UserRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> SettingViewModel(userRepository) as T
            modelClass.isAssignableFrom(AuthenticationViewModel::class.java) -> AuthenticationViewModel(userRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(userRepository) as T
            modelClass.isAssignableFrom(NewStoryViewModel::class.java) -> NewStoryViewModel(userRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideUserRepository(context)
                )
            }.also { instance = it }
    }
}