package com.mankart.mankgram.ui.mapviewstory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mankart.mankgram.R
import com.mankart.mankgram.databinding.ActivityMapViewStoryBinding
import com.mankart.mankgram.ui.mainmenu.MainActivity

class MapViewStoryActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMapViewStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapViewStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}