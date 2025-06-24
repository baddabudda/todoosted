package com.forgblord.todo_prototype.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.data.repository.TodoRepository
import kotlinx.coroutines.launch

class ProjectCRUD: ViewModel() {
    private val todoRepository: TodoRepository = TodoRepository.getInstance()

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