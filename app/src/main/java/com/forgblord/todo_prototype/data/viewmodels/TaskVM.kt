package com.forgblord.todo_prototype.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.data.repository.TodoRepository
import kotlinx.coroutines.launch

open class TaskVM: ViewModel() {
    private val todoRepository: TodoRepository = TodoRepository.getInstance()

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

    fun deleteTask(task: Task) {
        viewModelScope.launch{
            todoRepository.deleteTask(task)
        }
    }

}