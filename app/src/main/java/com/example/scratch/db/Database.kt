package com.example.scratch.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.scratch.list.data.persistent.PersistentListDAO
import com.example.scratch.list.data.persistent.PersistentListDTO

@Database(
    entities = [PersistentListDTO::class],
    version = 1
)
@TypeConverters(value = [DateConverter::class])
abstract class Database : RoomDatabase() {

    abstract fun persistentListDAO(): PersistentListDAO
}