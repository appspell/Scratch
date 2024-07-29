package com.example.scratch.details.data

import retrofit2.http.GET
import retrofit2.http.Query


interface DetailsService {

    @GET("appspell/Scratch/jetpack-compose-mvvm/server-response/mock_detail_response.json")
    suspend fun fetchDetails(@Query("details_id") id: Long): DetailsResponse
}