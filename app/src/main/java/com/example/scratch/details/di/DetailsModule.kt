package com.example.scratch.details.di

import com.example.scratch.details.data.DetailsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object DetailsModule {

    @Provides
    fun provideDetailsDao(retrofit: Retrofit) = retrofit.create(DetailsService::class.java)
}