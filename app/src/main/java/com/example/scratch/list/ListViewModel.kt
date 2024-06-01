package com.example.scratch.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: ListRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ListState(list = emptyList()))
    val state: StateFlow<ListState> = _state

    init {
        viewModelScope.launch {
            repository.observeList()
                .collect() { list ->
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

    fun addNew() {
        repository.addNewItem()
    }

    fun onSelect(index: Int) {
        _state.update {
            //deselect others
            val list = it.list.toMutableList().map { it.copy(isSelected = false) }
            val selectedItem = list[index]
            it.copy(list = list.toMutableList().apply {
                set(index, selectedItem.copy(isSelected = !selectedItem.isSelected))
            })
        }
    }

    fun onDelete(index: Int) {
        val id = state.value.list.get(index = index).id
        repository.deleteItem(id)
    }

}