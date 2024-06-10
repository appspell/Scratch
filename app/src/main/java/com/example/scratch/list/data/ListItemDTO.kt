package com.example.scratch.list.data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ListItemDTO(
    val id: Long,
    val name: String,
    val date: Date,
    val type: Type,
) {
    enum class Type {
        @SerializedName("boat")
        BOAT,

        @SerializedName("raft")
        RAFT
    }
}