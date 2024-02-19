package com.example.test_9.data.repository

import android.graphics.Bitmap
import com.example.test_9.domain.repository.FirebaseImageRepository
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class FirebaseImageRepositoryImpl @Inject constructor(
    private val firebaseStorage: FirebaseStorage
) : FirebaseImageRepository {

    override suspend fun uploadImage(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val storageRef: StorageReference = firebaseStorage.reference
        val imagesRef: StorageReference =
            storageRef.child("images/${System.currentTimeMillis()}.jpg")

        val uploadTask = imagesRef.putBytes(data)
        uploadTask.await()

        return imagesRef.downloadUrl.await().toString()
    }

}