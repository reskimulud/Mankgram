package com.mankart.mankgram.ui.mapviewstory.fragment

import android.content.Intent
import android.content.res.Resources
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.mankart.mankgram.R
import com.mankart.mankgram.databinding.FragmentMapsBinding
import com.mankart.mankgram.ui.ViewModelFactory
import com.mankart.mankgram.ui.mainmenu.MainActivity
import com.mankart.mankgram.ui.mapviewstory.MapStyle
import com.mankart.mankgram.ui.mapviewstory.MapType
import com.mankart.mankgram.ui.mapviewstory.MapViewStoryViewModel

class MapsFragment : Fragment() {
    private var _binding: FragmentMapsBinding? = null
    private lateinit var factory: ViewModelFactory
    private val mapViewStoryViewModel: MapViewStoryViewModel by activityViewModels { factory }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        googleMap.setPadding(0, 160, 0, 0)
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true
        googleMap.uiSettings.isIndoorLevelPickerEnabled = true

        mapViewStoryViewModel.userStories.observe(viewLifecycleOwner) {
            Log.e("MapsFragment", "userStories: $it")
            it?.let {
                for (story in it) {
                    val lat: Double = story.lat!!
                    val lon: Double = story.lon!!

                    val latLng = LatLng(lat, lon)
                    googleMap.addMarker(MarkerOptions().position(latLng).title(story.name))?.tag = story.id
                }
                val latLng = LatLng(it[0].lat!!, it[0].lon!!)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
            }
        }

        mapViewStoryViewModel.getMapType().observe(viewLifecycleOwner) {
            when (it) {
                MapType.NORMAL -> setMapType(googleMap, MapType.NORMAL)
                MapType.SATELLITE -> setMapType(googleMap, MapType.SATELLITE)
                MapType.TERRAIN -> setMapType(googleMap, MapType.TERRAIN)
                else -> setMapType(googleMap, MapType.NORMAL)
            }
        }

        mapViewStoryViewModel.getMapStyle().observe(viewLifecycleOwner) {
            when (it) {
                MapStyle.NORMAL -> setMapStyle(googleMap, MapStyle.NORMAL)
                MapStyle.NIGHT -> setMapStyle(googleMap, MapStyle.NIGHT)
                MapStyle.SILVER -> setMapStyle(googleMap, MapStyle.SILVER)
                else -> setMapStyle(googleMap, MapStyle.NORMAL)
            }
        }

        googleMap.setOnMarkerClickListener { marker ->
            val story = mapViewStoryViewModel.userStories.value?.find { it.id == marker.tag }
            story?.let {
                val latLog = LatLng(it.lat!!, it.lon!!)
                val toDialogDetailStoryFragment = MapsFragmentDirections.actionMapsFragmentToDialogDetailStoryFragment(
                    it.name,
                    it.description,
                    it.image
                )
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLog, 10f))
                findNavController().navigate(toDialogDetailStoryFragment)
            }
            true
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())

        binding.backArrow.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }

        binding.toolbar.inflateMenu(R.menu.map_style_option)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.map_style_option -> {
                    findNavController().navigate(R.id.action_mapsFragment_to_mapOptionFragment)
                }
            }
            true
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        fetchUserStories()
        mapViewStoryViewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchUserStories() {
        mapViewStoryViewModel.getUserToken().observe(viewLifecycleOwner) {
            mapViewStoryViewModel.getUserStoriesWithLocation(it)
            Log.e("MapView", "Token: $it")
        }
    }

    private fun setMapType(mMap: GoogleMap, mapType: MapType) {
        when (mapType) {
            MapType.NORMAL -> {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            }
            MapType.SATELLITE -> {
                mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            }
            MapType.TERRAIN -> {
                mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            }
        }
    }

    private fun setMapStyle(mMap: GoogleMap, mapStyle: MapStyle) {
        try {
            val success = when (mapStyle) {
                MapStyle.NORMAL -> mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireActivity(), R.raw.map_style_normal))
                MapStyle.NIGHT -> mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireActivity(), R.raw.map_style_night))
                MapStyle.SILVER -> mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireActivity(), R.raw.map_style_silver))
            }
            if (!success) {
                Log.e("MapsFragment", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("MapsFragment", "Can't find style. Error: ", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        OnMapReadyCallback {
            it.clear()
        }
    }
}