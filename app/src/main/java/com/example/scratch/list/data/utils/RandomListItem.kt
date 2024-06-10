package com.example.scratch.list.data.utils

import com.example.scratch.list.data.ListItemDTO
import java.util.Date
import kotlin.random.Random

object RandomListItem {

    private var indexCounter = 1L

    fun updateIndexCounter(startIndex: Long) {
        indexCounter = startIndex
    }

    fun createRandomItem(): ListItemDTO =
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