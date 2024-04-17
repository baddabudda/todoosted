package com.forgblord.todo_prototype.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.forgblord.todo_prototype.data.models.ProjectAndTasks
import com.forgblord.todo_prototype.data.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE task_id=(:id)")
    suspend fun getTaskById(id: Int): Task

    @Update
    suspend fun updateTask(task: Task)

    @Insert
    suspend fun addTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT task_id, title, completed, date FROM task where date(date, 'unixepoch', 'localtime') = date('now', 'localtime')")
    fun getAllDueToday(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE completed=1")
    fun getCompleted(): Flow<List<Task>>

    @Transaction
    @Query("SELECT * FROM project WHERE project_id=:id")
    fun getAllTasksByProjectId(id: Int): Flow<ProjectAndTasks>
}