package com.mankart.mankgram.ui.mainmenu.newstory

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mankart.mankgram.*
import com.mankart.mankgram.databinding.FragmentNewStoryBinding
import com.mankart.mankgram.ui.ViewModelFactory
import com.mankart.mankgram.ui.authentication.AuthenticationViewModel
import com.mankart.mankgram.ui.mainmenu.MainActivity
import com.mankart.mankgram.utils.reduceFileImage
import com.mankart.mankgram.utils.rotateBitmap
import com.mankart.mankgram.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class NewStoryFragment : Fragment() {
    private var _binding: FragmentNewStoryBinding? = null
    private lateinit var factory: ViewModelFactory
    private val newStoryViewModel: NewStoryViewModel by activityViewModels { factory }
    private val authenticationViewModel: AuthenticationViewModel by activityViewModels { factory }
    private lateinit var result: Bitmap
    private lateinit var navView: View
    private var getFile: File? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    // Precise location access granted.
                    getMyLastLocation()
                }
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    // Only approximate location access granted.
                    getMyLastLocation()
                }
                else -> {
                    // No location access granted.
                    binding.cbShareLoc.isChecked = false
                    binding.cbShareLoc.isEnabled = false
                }
            }
        }

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

        factory = ViewModelFactory.getInstance(requireActivity())
        navView = requireActivity().findViewById(R.id.nav_view)
        navView.visibility = View.GONE

        binding.previewImage.setOnClickListener { startCameraX() }
        binding.btnCamera.setOnClickListener { startCameraX() }
        binding.btnGallery.setOnClickListener { startGallery() }

        initObserve()

        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_new_story_to_navigation_home)
        }

        binding.uploadStory.setOnClickListener {
            val description = binding.descriptionEditText.text.toString()
            if (description.isNotEmpty()) {
                loading(true)
                uploadStory(description)
            } else {
                val msg = getString(R.string.enter_description)
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        binding.cbShareLoc.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    private fun initObserve() {
        newStoryViewModel.loading.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                loading(it)
            }
        }
        newStoryViewModel.error.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { error ->
                if (!error) {
                    Toast.makeText(activity, getString(R.string.upload_success), Toast.LENGTH_LONG).show()
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()
                }
            }
        }
        newStoryViewModel.message.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { msg ->
                val message = getString(R.string.upload_failed)
                Toast.makeText(activity, "$msg: $message", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startCameraX() {
        val intent = Intent(activity, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun uploadStory(description: String) {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)

            val requestDescription = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

            authenticationViewModel.getUserToken().observe(viewLifecycleOwner) { token ->
                newStoryViewModel.myLocation.observe(viewLifecycleOwner) {
                    if (it != null) {
                        newStoryViewModel.uploadStory(imageMultipart, requestDescription, token, it.latitude.toFloat(), it.longitude.toFloat())
                    } else {
                        newStoryViewModel.uploadStory(imageMultipart, requestDescription, token)
                    }
                }
            }
        }
    }

    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding.uploadStory.isEnabled = false
            binding.tvUploading.visibility = View.VISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.uploadStory.isEnabled = true
            binding.tvUploading.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            binding.uploadStory.isEnabled = true
            binding.previewImage.setImageBitmap(result)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            val myFile = uriToFile(selectedImg, requireActivity())

            getFile = myFile

            binding.uploadStory.isEnabled = true
            binding.previewImage.setImageURI(selectedImg)
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getMyLastLocation() {
        if     (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ){
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    newStoryViewModel.saveMyLocation(location)
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        navView.visibility = View.VISIBLE
    }

    companion object {
        const val CAMERA_X_RESULT = 200
    }

}