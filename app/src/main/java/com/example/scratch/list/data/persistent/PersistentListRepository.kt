package com.example.scratch.list.data.persistent

import com.example.scratch.list.data.ListItemDTO
import com.example.scratch.list.data.ListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * To switch between remote and local repository check ListModule.kt
 */
class PersistentListRepository @Inject constructor(
    private val persistentService: PersistentListService
) : ListRepository {

    override suspend fun init() {
        persistentService.init()
    }

    override suspend fun observeList(): Flow<List<ListItemDTO>> {
        return persistentService.fetchAll()
    }

    override suspend fun addNewItem() {
        persistentService.add()
    }

    override suspend fun deleteItem(id: Long) {
        persistentService.delete(id)
    }
}