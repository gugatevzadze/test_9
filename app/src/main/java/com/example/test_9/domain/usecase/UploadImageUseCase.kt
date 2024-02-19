package com.example.test_9.domain.usecase

import android.graphics.Bitmap
import com.example.test_9.domain.repository.FirebaseImageRepository
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val firebaseImageRepository: FirebaseImageRepository
) {

    suspend operator fun invoke(bitmap: Bitmap): String {
        return firebaseImageRepository.uploadImage(bitmap)
    }
}