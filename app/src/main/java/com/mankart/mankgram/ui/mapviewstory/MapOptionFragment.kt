package com.mankart.mankgram.ui.mapviewstory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mankart.mankgram.R
import com.mankart.mankgram.databinding.FragmentMapOptionBinding

class MapOptionFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentMapOptionBinding? = null

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

        onClickListener()
    }

    private fun onClickListener() {
        binding.ivClose.setOnClickListener {
            dismiss()
        }

        binding.cvMapDefault.setOnClickListener {
            highlightMapTypeSwitcher(MapType.NORMAL)
            Toast.makeText(context, "Default", Toast.LENGTH_SHORT).show()
//            dismiss()
        }

        binding.cvMapSatellite.setOnClickListener {
            highlightMapTypeSwitcher(MapType.SATELLITE)
            Toast.makeText(context, "Satellite", Toast.LENGTH_SHORT).show()
//            dismiss()
        }

        binding.cvMapTerrain.setOnClickListener {
            highlightMapTypeSwitcher(MapType.TERRAIN)
            Toast.makeText(context, "Terrain", Toast.LENGTH_SHORT).show()
//            dismiss()
        }

        binding.cvMapStyleDefault.setOnClickListener {
            highlightMapStyleSwitcher(MapStyle.NORMAL)
            Toast.makeText(context, "Default", Toast.LENGTH_SHORT).show()
//            dismiss()
        }

        binding.cvMapStyleNight.setOnClickListener {
            highlightMapStyleSwitcher(MapStyle.NIGHT)
            Toast.makeText(context, "Night", Toast.LENGTH_SHORT).show()
//            dismiss()
        }

        binding.cvMapStyleSilver.setOnClickListener {
            highlightMapStyleSwitcher(MapStyle.SILVER)
            Toast.makeText(context, "Silver", Toast.LENGTH_SHORT).show()
//            dismiss()
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