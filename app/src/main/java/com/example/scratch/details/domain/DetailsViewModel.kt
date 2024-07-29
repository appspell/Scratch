package com.example.scratch.details.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scratch.details.data.DetailsDTO
import com.example.scratch.details.data.DetailsRepository
import com.example.scratch.details.data.DetailsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsViewState(isLoading = true))
    val state: StateFlow<DetailsViewState> = _state

    fun init(elementId: Long) {
        viewModelScope.launch {
            repository.fetch(id = elementId)
                .onEach { dto ->
                    _state.value = dto.mapToViewState()
                }
                .collect()
        }
    }
}

private fun DetailsDTO.mapToViewState() = DetailsViewState(
    data = DetailsViewState.Data(
        id = id,
        name = name,
        date = date,
        image = image,
        type = when (type) {
            DetailsDTO.Type.BOAT -> DetailsViewState.Type.BOAT
            DetailsDTO.Type.RAFT -> DetailsViewState.Type.RAFT
        }
    ),
    isLoading = false,
    isError = false,
)