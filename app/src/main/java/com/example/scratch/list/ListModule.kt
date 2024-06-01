package com.example.scratch.list

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class ListModule {

    @Binds
    abstract fun bindListRepository(impl: ListRepositoryImpl): ListRepository
}