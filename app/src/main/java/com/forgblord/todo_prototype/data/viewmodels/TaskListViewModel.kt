package com.forgblord.todo_prototype.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forgblord.todo_prototype.data.models.TaskProject
import com.forgblord.todo_prototype.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class TaskListViewModel: ViewModel() {
    protected val todoRepository: TodoRepository = TodoRepository.getInstance()

    protected val _taskList: MutableStateFlow<List<TaskProject>> = MutableStateFlow(emptyList())
    val taskList: StateFlow<List<TaskProject>>
        get() = _taskList.asStateFlow()


    /*fun getAllByProjectId(id: Int) {
        viewModelScope.launch {
            todoRepository.getAllTasksByProjectId(id).collect {
                Log.d("VIEWMODEL", "$it")
                _taskList.value = it
            }
        }
    }*/
}