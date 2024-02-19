package com.example.test_9.di

import com.example.test_9.data.repository.FirebaseImageRepositoryImpl
import com.example.test_9.domain.repository.FirebaseImageRepository
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideFirebaseImageRepository(firebaseStorage: FirebaseStorage): FirebaseImageRepository {
        return FirebaseImageRepositoryImpl(firebaseStorage)
    }
}