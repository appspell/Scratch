package com.example.scratch.list.data

import com.example.scratch.list.data.persistent.PersistentListDTO
import com.example.scratch.list.data.remote.RemoteListService
import com.example.scratch.list.data.remote.ResponseList
import com.example.scratch.list.data.utils.RandomListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random

/**
 * To switch between remote and local repository check ListModule.kt
 */
class CombinatedListRepository @Inject constructor(
    private val remoteListService: RemoteListService
) : ListRepository {

    private val list = MutableStateFlow<List<ListItemDTO>>(emptyList())

    override suspend fun init() {
        // do nothing
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun observeList(): Flow<List<ListItemDTO>> =
        flow {
            val remoteList = remoteListService.getList()
            emit(remoteList)
        }
            .map { response ->
                response.list.map { it.toItemDTO() }
            }
            .flatMapLatest { remoteList ->
                list.update { remoteList }
                list
            }
            .flowOn(Dispatchers.IO)

    override suspend fun deleteItem(id: Long) {
        list.update {
            it.toMutableList().filter { it.id != id }
        }
    }

    override suspend fun addNewItem() {
        list.update { it.plus(RandomListItem.createRandomItem()) }
    }


    private fun ResponseList.Item.toItemDTO() =
        ListItemDTO(
            id = id,
            name = name,
            date = date,
            type = when (type) {
                ResponseList.Item.Type.BOAT -> ListItemDTO.Type.BOAT
                ResponseList.Item.Type.RAFT -> ListItemDTO.Type.RAFT
            }
        )
}

