package com.example.scratch.list.di

import com.example.scratch.list.data.CombinatedListRepository
import com.example.scratch.list.data.ListRepository
import com.example.scratch.list.data.RemoteListService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit


@Module
@InstallIn(ViewModelComponent::class)
abstract class ListModule {

    @Binds
    abstract fun bindListRepository(impl: CombinatedListRepository): ListRepository
}

@Module
@InstallIn(ViewModelComponent::class)
object ListDataModule {

    @Provides
    fun provideRemoteListService(retrofit: Retrofit) =
        retrofit.create(RemoteListService::class.java)
}