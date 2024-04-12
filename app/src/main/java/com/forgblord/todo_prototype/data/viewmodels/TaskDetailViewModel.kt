package com.forgblord.todo_prototype.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    taskId: Int,
): ViewModel() {
    private val taskRepository = TaskRepository.get()

    private val _task: MutableStateFlow<Task?> = MutableStateFlow(null)
    val task: StateFlow<Task?> = _task.asStateFlow()

    init {
        viewModelScope.launch {
            _task.value = taskRepository.getTaskById(taskId)
        }
    }

    override fun onCleared() {
        super.onCleared()

        task.value?.let { taskRepository.updateTask(it) }
    }

    fun updateTask(onUpdate: (Task) -> Task) {
        _task.update { oldTask ->
            oldTask?.let { onUpdate(it) }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch{
            taskRepository.deleteTask(task)
        }
    }
}

class TaskDetailViewModelFactory(
    private val taskId: Int,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskDetailViewModel(taskId) as T
    }
}