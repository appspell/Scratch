package com.example.scratch.list.domain

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scratch.list.data.ListItemDTO
import com.example.scratch.list.data.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: ListRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ListState(list = emptyList()))
    val state: StateFlow<ListState> = _state

    init {
        viewModelScope.launch {
            repository.init()

            repository.observeList()
                .collect { list ->
                    _state.update {
                        ListState(
                            list = list.map { dto ->
                                ListState.Item(
                                    id = dto.id,
                                    name = dto.name,
                                    type = when (dto.type) {
                                        ListItemDTO.Type.BOAT -> ListState.Item.Type.BOAT
                                        ListItemDTO.Type.RAFT -> ListState.Item.Type.RAFT
                                    },
                                    dateTime = SimpleDateFormat("mm-dd-YYYY").format(dto.date),
                                    isSelected = false
                                )
                            }
                        )
                    }
                }
        }
    }

    fun addNew() = viewModelScope.launch(Dispatchers.IO) {
        repository.addNewItem()
    }

    fun onSelect(index: Int) = viewModelScope.launch(Dispatchers.IO) {
        _state.update {
            //deselect others
            val list = it.list.toMutableList().map { it.copy(isSelected = false) }
            val selectedItem = list[index]
            it.copy(list = list.toMutableList().apply {
                set(index, selectedItem.copy(isSelected = !selectedItem.isSelected))
            })
        }
    }

    fun onDelete(index: Int) = viewModelScope.launch(Dispatchers.IO) {
        val id = state.value.list.get(index = index).id
        repository.deleteItem(id)
    }

}