package com.example.scratch.details.domain

import com.google.gson.annotations.SerializedName

data class DetailsViewState(
    val data: Data? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false
) {
    data class Data(
        val id: Long,
        val name: String,
        val type: Type,
        val date: String,
        val image: String,
    )

    enum class Type {
        @SerializedName("boat")
        BOAT,

        @SerializedName("raft")
        RAFT
    }
}