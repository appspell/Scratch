package com.example.scratch.details.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    val service: DetailsService
) {

    suspend fun fetch(id: Long): Flow<DetailsDTO> = flow {
        val result = service.fetchDetails(id = id)
        emit(result.mapToDTO())
    }
}

private fun DetailsResponse.mapToDTO() = DetailsDTO(
    id = id,
    name = name,
    date = date,
    image = image,
    type = when (type) {
        DetailsResponse.Type.BOAT -> DetailsDTO.Type.BOAT
        DetailsResponse.Type.RAFT -> DetailsDTO.Type.RAFT
    }
)