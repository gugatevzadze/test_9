package com.example.test_9.presentation.screen.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.test_9.databinding.FragmentMainBinding
import com.example.test_9.presentation.base.BaseFragment
import com.example.test_9.presentation.extension.showSnackBar
import com.example.test_9.presentation.interfaces.OnChoosePictureButton
import com.example.test_9.presentation.interfaces.OnTakePictureButton
import com.example.test_9.presentation.screen.bottom_sheet.BottomSheetFragment
import java.io.ByteArrayOutputStream

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate), OnChoosePictureButton,
    OnTakePictureButton {


    override fun setUp() {
    }

    override fun setClickListeners() {
        onAddImageButtonClicked()
    }

    override fun bindObservers() {
    }

    private fun onAddImageButtonClicked() {
        binding.btnAddPhoto.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)
        }
    }

    //interface implementations
    override fun onChoosePictureOptionSelected() {
        if (checkReadStoragePermission()) {
            openGallery()
        } else {
            requestReadStoragePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    override fun onTakePictureOptionSelected() {
        if (checkCameraPermission()) {
            openCamera()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    //taking picture functionality
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageBitmap = data?.extras?.get("data") as Bitmap?
            imageBitmap?.let {
                val compressedBitmap = compressBitmap(it)
                binding.ivPhoto.setImageBitmap(compressedBitmap)
            }
        } else {
            binding.root.showSnackBar("Failed to capture image")
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            openCamera()
        } else {
            binding.root.showSnackBar("Camera permission denied")
        }
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureLauncher.launch(takePictureIntent)
    }

    //choose picture functionality
    private val choosePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data
            imageUri?.let {
                val imageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, it)
                val compressedBitmap = compressBitmap(imageBitmap)
                binding.ivPhoto.setImageBitmap(compressedBitmap)
            }
        } else {
            binding.root.showSnackBar("Failed to choose image")
        }
    }

    private val requestReadStoragePermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            openGallery()
        } else {
            binding.root.showSnackBar("Read storage permission denied")
        }
    }

    private fun checkReadStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        choosePictureLauncher.launch(intent)
    }

    //compressor - i dont know if i should have used this kind of compressor or workmanager
    private fun compressBitmap(bitmap: Bitmap): Bitmap {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}
