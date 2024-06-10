package com.example.scratch.list.data.remote

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ResponseList(
    val list: List<Item>,
    val nextPage: String
) {

    data class Item(
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
}