package com.example.scratch.list.data

import kotlinx.coroutines.flow.Flow

interface ListRepository {

    suspend fun init()
    suspend fun addNewItem()
    suspend fun observeList(): Flow<List<ListItemDTO>>
    suspend fun deleteItem(id: Long)
}