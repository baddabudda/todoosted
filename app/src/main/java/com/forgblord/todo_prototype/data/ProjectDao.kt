package com.forgblord.todo_prototype.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.data.models.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {
    @Query("SELECT * FROM project")
    fun getAllProjects(): Flow<List<Project>>

    @Insert
    suspend fun addProject(project: Project)

    @Delete
    suspend fun deleteProject(project: Project)

    @Update
    suspend fun updateProject(project: Project)

    @Query(
        "SELECT * FROM project WHERE project_id = :id"
    )
    fun getProjectById(id: Int): Flow<Project>
}