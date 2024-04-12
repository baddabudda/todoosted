package com.forgblord.todo_prototype.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.forgblord.todo_prototype.data.models.Task
import kotlinx.coroutines.flow.Flow
import java.util.Date

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

    @Query("SELECT id, title, completed, date FROM task where date(date, 'unixepoch', 'localtime') = date('now', 'localtime')")
    fun getAllDueToday(): Flow<List<Task>>
}