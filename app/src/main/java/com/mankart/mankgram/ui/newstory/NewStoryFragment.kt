package com.mankart.mankgram.ui.newstory

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.mankart.mankgram.*
import com.mankart.mankgram.databinding.FragmentNewStoryBinding
import java.io.File

class NewStoryFragment : Fragment() {
    private var _binding: FragmentNewStoryBinding? = null
    private lateinit var result: Bitmap

    private lateinit var navView: View

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewStoryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startCameraX()
        navView = requireActivity().findViewById(R.id.nav_view)
        navView.visibility = View.GONE

        binding.previewImage.setOnClickListener { startCameraX() }

        binding.uploadStory.setOnClickListener {
            val description = binding.descriptionEditText.text.toString()
            if (description.isNotEmpty()) {
                uploadStory(result, description)
            } else {
                val msg = getString(R.string.enter_description)
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startCameraX() {
        val intent = Intent(activity, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun uploadStory(image: Bitmap, description: String) {
        Toast.makeText(activity, "$description + $image", Toast.LENGTH_SHORT).show()
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            result = reduceBitmap(result)

            binding.uploadStory.isEnabled = true
            binding.previewImage.setImageBitmap(result)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        navView.visibility = View.VISIBLE
        result.recycle()
    }

    companion object {
        const val CAMERA_X_RESULT = 200
    }

}