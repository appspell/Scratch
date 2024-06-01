package com.example.scratch.list.di

import com.example.scratch.list.data.InMemoryListRepository
import com.example.scratch.list.data.ListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class ListModule {

    @Binds
    abstract fun bindListRepository(impl: InMemoryListRepository): ListRepository
}