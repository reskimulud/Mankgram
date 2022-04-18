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
            highlightSwitcher(MapType.NORMAL)
            Toast.makeText(context, "Default", Toast.LENGTH_SHORT).show()
//            dismiss()
        }

        binding.cvMapSatellite.setOnClickListener {
            highlightSwitcher(MapType.SATELLITE)
            Toast.makeText(context, "Satellite", Toast.LENGTH_SHORT).show()
//            dismiss()
        }

        binding.cvMapTerrain.setOnClickListener {
            highlightSwitcher(MapType.TERRAIN)
            Toast.makeText(context, "Terrain", Toast.LENGTH_SHORT).show()
//            dismiss()
        }
    }

    private fun highlightSwitcher(type: MapType) {
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
}