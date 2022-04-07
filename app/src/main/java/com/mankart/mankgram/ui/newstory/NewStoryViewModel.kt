package com.mankart.mankgram.ui.newstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewStoryViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is New Story Fragment"
    }
    val text: LiveData<String> = _text
}