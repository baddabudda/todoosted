package com.forgblord.todo_prototype.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forgblord.todo_prototype.data.models.TaskRecord
import com.forgblord.todo_prototype.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecordListViewModel: ViewModel() {
    private val todoRepository = TodoRepository.getInstance()

    private val _recordList = MutableStateFlow<List<TaskRecord>>(emptyList())
    val recordList: StateFlow<List<TaskRecord>>
        get() = _recordList.asStateFlow()

    init {
        viewModelScope.launch {
            todoRepository.getAllRecord().collect {
                _recordList.value = it
            }
        }
    }
}