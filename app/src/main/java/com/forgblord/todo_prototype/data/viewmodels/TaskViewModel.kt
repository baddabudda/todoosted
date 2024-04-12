package com.forgblord.todo_prototype.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel: ViewModel() {
    private val taskRepository = TaskRepository.get()

    private val _taskList: MutableStateFlow<List<Task>> = MutableStateFlow(emptyList())
    val taskList: StateFlow<List<Task>>
        get() = _taskList.asStateFlow()

    private val _dueToday: MutableStateFlow<List<Task>> = MutableStateFlow(emptyList())
    val dueToday: StateFlow<List<Task>>
        get() = _dueToday.asStateFlow()

    init {
        viewModelScope.launch {
            taskRepository.getAllTasks().collect {
                _taskList.value = it
            }
        }

        viewModelScope.launch {
            taskRepository.getAllDueToday().collect {
                _dueToday.value = it
            }
        }
        Log.d("VIEWMODEL", "INITIALIZED STUFF")
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            taskRepository.addTask(task)
        }
    }

/*    fun getAllTasks() {
        viewModelScope.launch {
            taskRepository.getAllTasks()
        }
    }*/

/*    fun getAllDueToday() {
        viewModelScope.launch {
            taskRepository.getAllDueToday()
        }
    }*/
}