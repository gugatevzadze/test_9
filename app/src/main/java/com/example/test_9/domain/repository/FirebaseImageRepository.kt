package com.example.test_9.domain.repository

import android.graphics.Bitmap

interface FirebaseImageRepository {
    suspend fun uploadImage(bitmap: Bitmap): String
}