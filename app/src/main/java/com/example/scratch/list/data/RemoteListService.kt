package com.example.scratch.list.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteListService {

    @GET("appspell/Scratch/jetpack-compose-mvvm/server-response/mock_list_response.json")
    suspend fun getList(@Query("nextPage") nextPage: String = ""): ResponseList

}