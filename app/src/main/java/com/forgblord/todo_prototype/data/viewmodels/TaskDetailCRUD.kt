package com.forgblord.todo_prototype.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.forgblord.todo_prototype.data.models.TaskProject
import com.forgblord.todo_prototype.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskDetailCRUD(
    taskId: Int,
): TaskCRUD() {
    private val todoRepository: TodoRepository = TodoRepository.getInstance()

    private val _task: MutableStateFlow<TaskProject?> = MutableStateFlow(null)
    val task: StateFlow<TaskProject?> = _task.asStateFlow()

    init {
        viewModelScope.launch {
            _task.value = todoRepository.getTaskById(taskId)
        }
    }

    override fun onCleared() {
        super.onCleared()

        task.value?.let { todoRepository.updateTask(it.task) }
    }

    fun updateTask(onUpdate: (TaskProject) -> TaskProject) {
        _task.update { oldTask ->
            oldTask?.let { onUpdate(it) }
        }
    }
}

class TaskDetailViewModelFactory(
    private val taskId: Int,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskDetailCRUD(taskId) as T
    }
}