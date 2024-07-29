package com.example.scratch.details.data

import com.google.gson.annotations.SerializedName

data class DetailsResponse(
    val id: Long,
    val name: String,
    val type: Type,
    val date: String,
    val image: String
) {
    enum class Type {
        @SerializedName("boat")
        BOAT,

        @SerializedName("raft")
        RAFT
    }
}