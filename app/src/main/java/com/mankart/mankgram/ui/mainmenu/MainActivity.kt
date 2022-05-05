package com.mankart.mankgram.ui.mainmenu

import android.Manifest
import android.animation.ObjectAnimator
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.mankart.mankgram.R
import com.mankart.mankgram.utils.clearDirectory
import com.mankart.mankgram.databinding.ActivityMainBinding

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var hideNavView = false

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    getString(R.string.permission_not_granted),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        val rvStory = findViewById<RecyclerView>(R.id.rv_story)
        if (rvStory != null) {
            hideShowBottomNavigation(rvStory)
        }

        supportActionBar?.hide()
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }

    private fun hideShowBottomNavigation(rvStory: RecyclerView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rvStory.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                val height = (binding.navView.height + 32).toFloat()

                if (!hideNavView && scrollY > oldScrollY) {
                    hideNavView = true
                    ObjectAnimator.ofFloat(binding.navView, "translationY", 0f, height).apply {
                        duration = 200
                        start()
                    }
                }

                if (hideNavView && scrollY < oldScrollY) {
                    hideNavView = false
                    ObjectAnimator.ofFloat(binding.navView, "translationY", height, 0f).apply {
                        duration = 200
                        start()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearDirectory(application)
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}