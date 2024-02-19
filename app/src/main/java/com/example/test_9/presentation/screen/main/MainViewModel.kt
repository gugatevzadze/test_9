package com.example.test_9.presentation.screen.main

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_9.domain.usecase.UploadImageUseCase
import com.example.test_9.presentation.state.main.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val uploadImageUseCase: UploadImageUseCase
): ViewModel() {

    private val _uploadState = MutableStateFlow(MainState())
    val uploadState: SharedFlow<MainState> get() = _uploadState

    fun uploadImage(bitmap: Bitmap) {
        viewModelScope.launch {
            val result = uploadImageUseCase(bitmap)
            _uploadState.value = MainState(imageUrl = result)
        }
    }
}