package com.mankart.mankgram.ui.mapviewstory.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mankart.mankgram.R
import com.mankart.mankgram.databinding.FragmentMapOptionBinding
import com.mankart.mankgram.ui.ViewModelFactory
import com.mankart.mankgram.ui.mapviewstory.MapStyle
import com.mankart.mankgram.ui.mapviewstory.MapType
import com.mankart.mankgram.ui.mapviewstory.MapViewStoryViewModel

class MapOptionFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentMapOptionBinding? = null
    private lateinit var factory: ViewModelFactory
    private val mapViewStoryViewModel: MapViewStoryViewModel by activityViewModels { factory }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMapOptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())

        onClickListener()
        initObserver()
        hideMapStyleGroup()
    }

    private fun initObserver() {
        mapViewStoryViewModel.getMapType().observe(viewLifecycleOwner) {
            when (it) {
                MapType.NORMAL -> {
                    highlightMapTypeSwitcher(MapType.NORMAL)
                    hideMapStyleGroup(false)
                }
                MapType.SATELLITE -> highlightMapTypeSwitcher(MapType.SATELLITE)
                MapType.TERRAIN -> highlightMapTypeSwitcher(MapType.TERRAIN)
                else -> highlightMapTypeSwitcher(MapType.NORMAL)
            }
        }
        mapViewStoryViewModel.getMapStyle().observe(viewLifecycleOwner) {
            when (it) {
                MapStyle.NORMAL -> highlightMapStyleSwitcher(MapStyle.NORMAL)
                MapStyle.NIGHT -> highlightMapStyleSwitcher(MapStyle.NIGHT)
                MapStyle.SILVER -> highlightMapStyleSwitcher(MapStyle.SILVER)
                else -> highlightMapStyleSwitcher(MapStyle.NORMAL)
            }
        }
    }

    private fun hideMapStyleGroup(isHide: Boolean = true) {
        if (isHide) {
            binding.mapStyleGroup.visibility = View.GONE
        } else {
            binding.mapStyleGroup.visibility = View.VISIBLE
        }
    }

    private fun onClickListener() {
        binding.ivClose.setOnClickListener {
            dismiss()
        }

        // Map Type
        binding.cvMapDefault.setOnClickListener {
            mapViewStoryViewModel.saveMapType(MapType.NORMAL)
            Toast.makeText(context, getString(R.string.map_type_normal), Toast.LENGTH_SHORT).show()
            dismiss()
        }

        binding.cvMapSatellite.setOnClickListener {
            mapViewStoryViewModel.saveMapType(MapType.SATELLITE)
            Toast.makeText(context, getString(R.string.map_type_satellite), Toast.LENGTH_SHORT).show()
            dismiss()
        }

        binding.cvMapTerrain.setOnClickListener {
            mapViewStoryViewModel.saveMapType(MapType.TERRAIN)
            Toast.makeText(context, getString(R.string.map_type_terrain), Toast.LENGTH_SHORT).show()
            dismiss()
        }

        // Map Style
        binding.cvMapStyleDefault.setOnClickListener {
            mapViewStoryViewModel.saveMapStyle(MapStyle.NORMAL)
            Toast.makeText(context, getString(R.string.map_style_normal), Toast.LENGTH_SHORT).show()
            dismiss()
        }

        binding.cvMapStyleNight.setOnClickListener {
            mapViewStoryViewModel.saveMapStyle(MapStyle.NIGHT)
            Toast.makeText(context, getString(R.string.map_style_night), Toast.LENGTH_SHORT).show()
            dismiss()
        }

        binding.cvMapStyleSilver.setOnClickListener {
            mapViewStoryViewModel.saveMapStyle(MapStyle.SILVER)
            Toast.makeText(context, getString(R.string.map_style_silver), Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    private fun highlightMapTypeSwitcher(type: MapType) {
        when (type) {
            MapType.NORMAL -> {
                // Normal
                binding.ivMapDefault.setPadding(1, 1, 1, 1)
                binding.tvMapDefault.setTextColor(resources.getColor(R.color.colorAccent))

                // Satellite
                binding.ivMapSatellite.setPadding(0, 0, 0, 0)
                binding.tvMapSatellite.setTextColor(resources.getColor(R.color.colorTextSecondary))

                // Terrain
                binding.ivMapTerrain.setPadding(0, 0, 0, 0)
                binding.tvMapTerrain.setTextColor(resources.getColor(R.color.colorTextSecondary))
            }
            MapType.SATELLITE -> {
                // Normal
                binding.ivMapDefault.setPadding(0, 0, 0, 0)
                binding.tvMapDefault.setTextColor(resources.getColor(R.color.colorTextSecondary))

                // Satellite
                binding.ivMapSatellite.setPadding(1, 1, 1, 1)
                binding.tvMapSatellite.setTextColor(resources.getColor(R.color.colorAccent))

                // Terrain
                binding.ivMapTerrain.setPadding(0, 0, 0, 0)
                binding.tvMapTerrain.setTextColor(resources.getColor(R.color.colorTextSecondary))
            }
            MapType.TERRAIN -> {
                // Normal
                binding.ivMapDefault.setPadding(0, 0, 0, 0)
                binding.tvMapDefault.setTextColor(resources.getColor(R.color.colorTextSecondary))

                // Satellite
                binding.ivMapSatellite.setPadding(0, 0, 0, 0)
                binding.tvMapSatellite.setTextColor(resources.getColor(R.color.colorTextSecondary))

                // Terrain
                binding.ivMapTerrain.setPadding(1, 1, 1, 1)
                binding.tvMapTerrain.setTextColor(resources.getColor(R.color.colorAccent))
            }
        }
    }

    private fun highlightMapStyleSwitcher(style: MapStyle) {
        when (style) {
            MapStyle.NORMAL -> {
                // Normal
                binding.ivMapStyleDefault.setPadding(1, 1, 1, 1)
                binding.tvMapStyleNormal.setTextColor(resources.getColor(R.color.colorAccent))

                // Night
                binding.ivMapStyleNight.setPadding(0, 0, 0, 0)
                binding.tvMapStyleNight.setTextColor(resources.getColor(R.color.colorTextSecondary))

                // Silver
                binding.ivMapStyleSilver.setPadding(0, 0, 0, 0)
                binding.tvMapStyleSilver.setTextColor(resources.getColor(R.color.colorTextSecondary))
            }
            MapStyle.NIGHT -> {
                // Normal
                binding.ivMapStyleDefault.setPadding(0, 0, 0, 0)
                binding.tvMapStyleNormal.setTextColor(resources.getColor(R.color.colorTextSecondary))

                // Night
                binding.ivMapStyleNight.setPadding(1, 1, 1, 1)
                binding.tvMapStyleNight.setTextColor(resources.getColor(R.color.colorAccent))

                // Silver
                binding.ivMapStyleSilver.setPadding(0, 0, 0, 0)
                binding.tvMapStyleSilver.setTextColor(resources.getColor(R.color.colorTextSecondary))
            }
            MapStyle.SILVER -> {
                // Normal
                binding.ivMapStyleDefault.setPadding(0, 0, 0, 0)
                binding.tvMapStyleNormal.setTextColor(resources.getColor(R.color.colorTextSecondary))

                // Night
                binding.ivMapStyleNight.setPadding(0, 0, 0, 0)
                binding.tvMapStyleNight.setTextColor(resources.getColor(R.color.colorTextSecondary))

                // Terrain
                binding.ivMapStyleSilver.setPadding(1, 1, 1, 1)
                binding.tvMapStyleSilver.setTextColor(resources.getColor(R.color.colorAccent))
            }
        }
    }
}