package com.forgblord.todo_prototype.data.repository

import android.content.Context
import androidx.room.Room
import com.forgblord.todo_prototype.data.TodoDatabase
import com.forgblord.todo_prototype.data.models.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import java.util.Date

private const val DATABASE_NAME = "todo-database"

class TaskRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope,
) {
    private val database: TodoDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java,
            DATABASE_NAME
        )
        .build()

    fun getAllTasks(): Flow<List<Task>> = database.taskDao().getAllTasks()

    suspend fun getTaskById(id: Int): Task = database.taskDao().getTask(id)

    fun updateTask(task: Task) {
        coroutineScope.launch {
            database.taskDao().updateTask(task)
        }
    }

    fun getAllDueToday(): Flow<List<Task>> = database.taskDao().getAllDueToday()

    suspend fun addTask(task: Task) {
        database.taskDao().addTask(task)
    }

    companion object {
        private var INSTANCE: TaskRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TaskRepository(context)
            }
        }

        fun get(): TaskRepository {
            return INSTANCE?:
            throw IllegalStateException("TaskRepository must be initialized")
        }
    }
}