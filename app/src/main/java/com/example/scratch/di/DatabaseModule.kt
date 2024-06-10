package com.example.scratch.di

import android.content.Context
import androidx.room.Room
import com.example.scratch.db.Database
import com.example.scratch.list.data.persistent.PersistentListDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context): Database =
        Room.databaseBuilder(
            context = applicationContext,
            klass = Database::class.java,
            name = "Database"
        )
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    fun providePersistentListDAO(database: Database): PersistentListDAO =
        database.persistentListDAO()

}