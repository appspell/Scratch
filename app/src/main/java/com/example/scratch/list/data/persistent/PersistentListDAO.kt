package com.example.scratch.list.data.persistent

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PersistentListDAO {

    @Query("SELECT * FROM persistentlistdto")
    fun fetchList(): Flow<List<PersistentListDTO>>

    @Query("DELETE FROM persistentlistdto WHERE id = :id")
    suspend fun delete(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PersistentListDTO)

    @Query("SELECT MAX(id) FROM persistentlistdto")
    fun getMaxIndex(): Long
}