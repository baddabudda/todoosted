package com.forgblord.todo_prototype.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.forgblord.todo_prototype.data.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE id=(:id)")
    suspend fun getTask(id: Int): Task

    @Update
    suspend fun updateTask(task: Task)

    @Insert
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM task WHERE date=DATETIME('now')")
    fun getAllDueToday(): Flow<List<Task>>
}