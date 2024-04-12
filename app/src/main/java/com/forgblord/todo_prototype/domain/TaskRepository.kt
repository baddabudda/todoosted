package com.forgblord.todo_prototype.domain

import com.forgblord.todo_prototype.data.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    fun getAllDueToday(): Flow<List<Task>>

    fun getTaskById(id: Int): Task
    fun addTask(newTask: Task)
    fun updateTask(updatedTask: Task)
    fun deleteTask(deletedTask: Task)
}