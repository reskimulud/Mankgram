package com.mankart.mankgram.ui.mapviewstory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mankart.mankgram.databinding.ActivityMapViewStoryBinding

class MapViewStoryActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMapViewStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapViewStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}