package com.example.scratch.list.domain

data class ListState(
    val list: List<Item>
) {
    data class Item(
        val id: Long,
        val name: String,
        val dateTime: String,
        val type: Type,
        val isSelected: Boolean
    ) {
        enum class Type {
            BOAT, RAFT
        }
    }
}