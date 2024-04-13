package com.forgblord.todo_prototype.domain

import com.forgblord.todo_prototype.data.models.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    fun getAllDueToday(): Flow<List<Task>>

    suspend fun getTaskById(id: Int): Task
    suspend fun addTask(newTask: Task)
    suspend fun updateTask(updatedTask: Task)
    suspend fun deleteTask(deletedTask: Task)
}