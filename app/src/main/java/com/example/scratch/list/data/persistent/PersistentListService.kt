package com.example.scratch.list.data.persistent

import com.example.scratch.list.data.ListItemDTO
import com.example.scratch.list.data.utils.RandomListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.startWith
import javax.inject.Inject

class PersistentListService @Inject constructor(
    private val dao: PersistentListDAO
) {

    suspend fun init() {
        flow {
            emit(dao.getMaxIndex() + 1)
        }
            .flowOn(Dispatchers.IO)
            .collect {
                RandomListItem.updateIndexCounter(it)
            }
    }

    fun fetchAll(): Flow<List<ListItemDTO>> = dao.fetchList()
        .map { list ->
            list.map { it.toListItemDTO() }
        }
        .flowOn(Dispatchers.IO)

    suspend fun delete(id: Long) {
        dao.delete(id)
    }

    suspend fun add() {
        val dto = RandomListItem.createRandomItem()
        dao.insert(dto.toPersistentListDTO())
    }

    private fun PersistentListDTO.toListItemDTO() =
        ListItemDTO(
            id = id,
            name = name,
            date = date,
            type = when (type) {
                PersistentListDTO.Type.BOAT -> ListItemDTO.Type.BOAT
                PersistentListDTO.Type.RAFT -> ListItemDTO.Type.RAFT
            }
        )

    private fun ListItemDTO.toPersistentListDTO() =
        PersistentListDTO(
            id = id,
            name = name,
            date = date,
            type = when (type) {
                ListItemDTO.Type.BOAT -> PersistentListDTO.Type.BOAT
                ListItemDTO.Type.RAFT -> PersistentListDTO.Type.RAFT
            }
        )
}