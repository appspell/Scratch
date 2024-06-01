package com.example.scratch.list

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random

data class ListItemDTO(
    val id: Long,
    val name: String,
    val date: Date,
    val type: Type,
) {
    enum class Type {
        BOAT, RAFT
    }
}

interface ListRepository {

    fun addNewItem()
    suspend fun observeList(): Flow<List<ListItemDTO>>
    fun deleteItem(id: Long)
}

class ListRepositoryImpl @Inject constructor() : ListRepository {

    private var indexCounter = 0L
    private val list = MutableStateFlow<List<ListItemDTO>>(emptyList())

    override suspend fun observeList(): Flow<List<ListItemDTO>> = list

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