package com.forgblord.todo_prototype.data.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class InboxViewModel: TaskListViewModel()  {
    init {
        viewModelScope.launch {
            todoRepository.getInbox().collect {
                _taskList.value = it
            }
        }
    }
}