package com.example.scratch.list.data

import kotlinx.coroutines.flow.Flow

interface ListRepository {
    fun addNewItem()
    suspend fun observeList(): Flow<List<ListItemDTO>>
    fun deleteItem(id: Long)
}