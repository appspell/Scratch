package com.example.scratch.list.data.persistent

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class PersistentListDTO(
    @PrimaryKey
    val id: Long,
    val name: String,
    val date: Date,
    val type: Type,
) {
    enum class Type {
        BOAT,
        RAFT
    }
}