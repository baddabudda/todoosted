package com.forgblord.todo_prototype.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BrowseViewModel: ViewModel() {
    private val todoRepository: TodoRepository = TodoRepository.getInstance()

    private val _projectList: MutableStateFlow<List<Project>> = MutableStateFlow(emptyList())
    val projectList: StateFlow<List<Project>>
        get() = _projectList.asStateFlow()

    init {
        viewModelScope.launch {
            todoRepository.getAllProjects().collect {
                _projectList.value = it
            }
        }
    }
}