package com.forgblord.todo_prototype.data.viewmodels

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TodayViewModel : TaskListViewModel() {
    init {
        viewModelScope.launch {
            todoRepository.getAllDueToday().collect {
                _taskList.value = it
            }
        }
    }
}