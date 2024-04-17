package com.forgblord.todo_prototype.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel: ViewModel() {
    private val todoRepository: TodoRepository = TodoRepository.getInstance()

    private val _taskList: MutableStateFlow<List<Task>> = MutableStateFlow(emptyList())
    val taskList: StateFlow<List<Task>>
        get() = _taskList.asStateFlow()

    /*private val _dueToday: MutableStateFlow<List<Task>> = MutableStateFlow(emptyList())
    val dueToday: StateFlow<List<Task>>
        get() = _dueToday.asStateFlow()*/

    /*init {
        viewModelScope.launch {
            todoRepository.getAllTasks().collect {
                _taskList.value = it
            }
        }

        viewModelScope.launch {
            todoRepository.getAllDueToday().collect {
                _dueToday.value = it
            }
        }
    }*/

    fun getAllTasks() {
        viewModelScope.launch {
            todoRepository.getAllTasks().collect {
                _taskList.value = it
            }
        }
    }

    fun getAllDueToday() {
        viewModelScope.launch {
            todoRepository.getAllDueToday().collect {
                _taskList.value = it
            }
        }
    }

    fun getAllByProjectId(id: Int) {
        viewModelScope.launch {
            todoRepository.getAllTasksByProjectId(id).collect {
                Log.d("VIEWMODEL", "$it")
                _taskList.value = it
            }
        }
    }

    fun getAllCompleted() {
        viewModelScope.launch {
            todoRepository.getCompleted().collect {
                _taskList.value = it
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            Log.d("TASK", "$task")
            todoRepository.addTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            todoRepository.updateTask(task)
        }
    }

    fun getInbox() {
        viewModelScope.launch {
            todoRepository.getInbox().collect {
                Log.d("VIEWMODEL", "$it")
                _taskList.value = it
            }
        }
    }
}