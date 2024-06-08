package com.example.scratch.list.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random

class CombinatedListRepository @Inject constructor(
    private val remoteListService: RemoteListService
) : ListRepository {

    private var indexCounter = 0L
    private val list = MutableStateFlow<List<ListItemDTO>>(emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun observeList(): Flow<List<ListItemDTO>> =
        flow {
            val remoteList = remoteListService.getList().list
            emit(remoteList)
        }
            .flatMapLatest { remoteList ->
                list.update { remoteList }
                list
            }

    override fun deleteItem(id: Long) {
        list.update {
            it.toMutableList().filter { it.id != id }
        }
    }

    override fun addNewItem() {
        list.update { it.plus(createRandomItem()) }
    }

    private fun createRandomItem(): ListItemDTO =
        ListItemDTO(
            id = indexCounter++,
            name = generateRandomName(),
            date = Date(System.currentTimeMillis() - Random.nextLong(0, 10000000)),
            type = ListItemDTO.Type.entries.random()
        )

    private fun generateRandomName(): String {
        val words =
            listOf("name", "adventurer", "knots", "unsinkable", "king", "queen", "sea", "conquer")
        var result = ""
        for (i in 0..Random.nextInt(1, 4)) {
            result += words.random()
            result += " "
        }
        return result
    }
}