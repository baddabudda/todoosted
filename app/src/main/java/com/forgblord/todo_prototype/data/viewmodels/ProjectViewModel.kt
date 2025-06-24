package com.forgblord.todo_prototype.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.data.models.ProjectState
import com.forgblord.todo_prototype.data.models.TaskProject
import com.forgblord.todo_prototype.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ProjectViewModel(
    private val projectId: Int,
): ViewModel() {
    private val todoRepository: TodoRepository = TodoRepository.getInstance()

    private var _projectState: MutableStateFlow<ProjectState> = MutableStateFlow(ProjectState(emptyList(), Project(title="", colorCode=0)))
    val projectState: StateFlow<ProjectState>
        get() = _projectState

    init {
        viewModelScope.launch {
            todoRepository.getProjectById(projectId)
                .combine(todoRepository.getAllTasksByProjectId(projectId)) { project, list ->
                    _projectState.value = ProjectState(list, project)
                }.collect()
        }
    }
}

class ProjectViewModelFactory(
    private val projectId: Int,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProjectViewModel(projectId) as T
    }
}