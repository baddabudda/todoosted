package com.forgblord.todo_prototype.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.data.models.TaskProject
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task WHERE completed=0")
    fun getAllTasks(): Flow<List<Task>>

    /*@Query("SELECT * FROM task WHERE task_id=(:id)")
    suspend fun getTaskById(id: Int): Task*/
    @Query(
        "SELECT task.*, project.title AS project_name FROM task LEFT JOIN project " +
        "ON project.project_id = task.proj_id " +
        "WHERE task.task_id=:id"
    )
    suspend fun getTaskById(id: Int): TaskProject

    @Update
    suspend fun updateTask(task: Task)

    @Insert
    suspend fun addTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query(
        "SELECT task.*, project.title AS project_name FROM task LEFT JOIN project " +
        "ON project.project_id = task.proj_id " +
        "WHERE date(task.date, 'unixepoch', 'localtime') = date('now', 'localtime') AND task.completed=0"
    )
    fun getAllDueToday(): Flow<List<TaskProject>>

    @Query(
        "SELECT task.*, project.title AS project_name FROM task LEFT JOIN project " +
        "ON task.proj_id = project.project_id " +
        "WHERE task.completed=1"
    )
    fun getCompleted(): Flow<List<TaskProject>>

    @Query(
        "SELECT task.*, project.title AS project_name FROM task JOIN project " +
        "ON project.project_id = task.proj_id " +
        "WHERE project.project_id=:id AND task.completed=0"
    )
    fun getAllTasksByProjectId(id: Int): Flow<List<TaskProject>>

    @Query("SELECT * FROM task WHERE proj_id IS NULL AND completed=0")
    fun getInbox(): Flow<List<TaskProject>>

}