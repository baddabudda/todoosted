package com.forgblord.todo_prototype.data.repository

import com.forgblord.todo_prototype.data.ProjectDao
import com.forgblord.todo_prototype.data.TaskDao
import com.forgblord.todo_prototype.data.TodoDatabase
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.data.models.ProjectAndTasks
import com.forgblord.todo_prototype.data.models.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TodoRepository private constructor(
    private val taskDao: TaskDao = TodoDatabase.get().taskDao(),
    private val projectDao: ProjectDao = TodoDatabase.get().projectDao(),
    private val coroutineScope: CoroutineScope = GlobalScope,
) {
    /*private val database: TodoDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java,
            DATABASE_NAME
        )
        .build()*/

    companion object {
        private val INSTANCE: TodoRepository = TodoRepository()
        fun getInstance(): TodoRepository {
            return INSTANCE
        }
    }

//    fun getAllTasks(): Flow<List<Task>> = database.taskDao().getAllTasks()
    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

//    suspend fun getTaskById(id: Int): Task = database.taskDao().getTaskById(id)
    suspend fun getTaskById(id: Int): Task = taskDao.getTaskById(id)

    /*fun updateTask(task: Task) {
        coroutineScope.launch {
            database.taskDao().updateTask(task)
        }
    }*/
    fun updateTask(task: Task) {
        coroutineScope.launch {
            taskDao.updateTask(task)
        }
    }

//    fun getAllDueToday(): Flow<List<Task>> = database.taskDao().getAllDueToday()
    fun getAllDueToday(): Flow<List<Task>> = taskDao.getAllDueToday()

    /*suspend fun addTask(task: Task) {
        database.taskDao().addTask(task)
    }*/
    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    /*suspend fun deleteTask(task: Task) {
        database.taskDao().deleteTask(task)
    }*/
    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    fun getCompleted(): Flow<List<Task>> = taskDao.getCompleted()

    fun getAllProjects(): Flow<List<Project>> = projectDao.getAllProjects()

    suspend fun addProject(project: Project) {
        projectDao.addProject(project)
    }

    fun getAllTasksByProjectId(id: Int): Flow<List<Task>> = taskDao.getAllTasksByProjectId(id)

    suspend fun deleteProject(project: Project) {
        projectDao.deleteProject(project)
    }

    fun getInbox(): Flow<List<Task>> = taskDao.getInbox()

}