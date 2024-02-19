package com.example.test_9.presentation.state.main

data class MainState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val imageUrl: String? = null
)