package com.forgblord.todo_prototype.data.viewmodels

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CompletedViewModel : TaskListViewModel() {
    init {
        viewModelScope.launch {
            todoRepository.getCompleted().collect {
                _taskList.value = it
            }
        }
    }
}