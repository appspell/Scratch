package com.example.scratch.list.data

import java.util.Date

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