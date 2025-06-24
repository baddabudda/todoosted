package com.forgblord.todo_prototype.data.repository

import com.forgblord.todo_prototype.data.ProjectDao
import com.forgblord.todo_prototype.data.RecordDao
import com.forgblord.todo_prototype.data.TaskDao
import com.forgblord.todo_prototype.data.TodoDatabase
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.data.models.TaskProject
import com.forgblord.todo_prototype.data.models.RecordTask
import com.forgblord.todo_prototype.data.models.TaskRecord
import com.forgblord.todo_prototype.data.models.TimeRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TodoRepository private constructor(
    private val taskDao: TaskDao = TodoDatabase.get().taskDao(),
    private val projectDao: ProjectDao = TodoDatabase.get().projectDao(),
    private val recordDao: RecordDao = TodoDatabase.get().recordDao(),
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

    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun getTaskById(id: Int): TaskProject = taskDao.getTaskById(id)

    fun updateTask(task: Task) {
        coroutineScope.launch {
            taskDao.updateTask(task)
        }
    }

    fun getAllDueToday(): Flow<List<TaskProject>> = taskDao.getAllDueToday()

    suspend fun addTask(task: Task) {
        taskDao.addTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    fun getCompleted(): Flow<List<TaskProject>> = taskDao.getCompleted()

    fun getAllProjects(): Flow<List<Project>> = projectDao.getAllProjects()

    suspend fun addProject(project: Project) {
        projectDao.addProject(project)
    }

    fun getAllTasksByProjectId(id: Int): Flow<List<TaskProject>> = taskDao.getAllTasksByProjectId(id)

    suspend fun deleteProject(project: Project) {
        projectDao.deleteProject(project)
    }

    fun updateProject(project: Project) {
        coroutineScope.launch {
            projectDao.updateProject(project)
        }
    }

    fun getInbox(): Flow<List<TaskProject>> = taskDao.getInbox()

    fun getProjectById(id: Int): Flow<Project> = projectDao.getProjectById(id)

    suspend fun addRecord(record: TimeRecord) {
        recordDao.addRecord(record)
    }

    suspend fun updateRecord(record: TimeRecord) {
        recordDao.updateRecord(record)
    }

    suspend fun getActiveRecord(): List<RecordTask> = recordDao.getActiveRecord()

    suspend fun deleteRecord(record: TimeRecord) {
        recordDao.deleteRecord(record)
    }

    fun getAllRecord(): Flow<List<TaskRecord>> = recordDao.getAllRecords()

    suspend fun getRecordById(id: Int): TaskRecord = recordDao.getRecordById(id)
}