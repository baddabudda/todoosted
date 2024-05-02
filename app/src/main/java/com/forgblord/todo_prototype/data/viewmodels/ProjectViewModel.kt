package com.forgblord.todo_prototype.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProjectViewModel: ViewModel() {
    private val todoRepository: TodoRepository = TodoRepository.getInstance()

    private val _projectList: MutableStateFlow<List<Project>> = MutableStateFlow(emptyList())
    val projectList: StateFlow<List<Project>>
        get() = _projectList.asStateFlow()

    private val _projectListSize: MutableStateFlow<Int> = MutableStateFlow(_projectList.value.size)
    val projectListSize: StateFlow<Int>
        get() = _projectListSize.asStateFlow()

    init {
        viewModelScope.launch {
            todoRepository.getAllProjects().collect {
                _projectList.value = it
            }
        }
    }

    fun addProject(project: Project) {
        viewModelScope.launch {
            todoRepository.addProject(project)
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch {
            todoRepository.deleteProject(project)
        }
    }

    fun updateProject(project: Project) {
        viewModelScope.launch {
            todoRepository.updateProject(project)
        }
    }
}