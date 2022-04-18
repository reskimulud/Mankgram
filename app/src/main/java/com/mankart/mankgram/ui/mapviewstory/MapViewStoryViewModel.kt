package com.mankart.mankgram.ui.mapviewstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mankart.mankgram.data.UserRepository
import kotlinx.coroutines.launch

class MapViewStoryViewModel(private val userRepository: UserRepository): ViewModel() {
    fun getMapType() : LiveData<MapType> = userRepository.getMapType()

    fun saveMapType(mapType: MapType) {
        viewModelScope.launch {
            userRepository.saveMapType(mapType)
        }
    }

    fun getMapStyle() : LiveData<MapStyle> = userRepository.getMapStyle()

    fun saveMapStyle(mapStyle: MapStyle) {
        viewModelScope.launch {
            userRepository.saveMapStyle(mapStyle)
        }
    }
}