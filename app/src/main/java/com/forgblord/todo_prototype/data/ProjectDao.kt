package com.forgblord.todo_prototype.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.forgblord.todo_prototype.data.models.Project
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDao {
    @Query("SELECT * FROM project")
    fun getAllProjects(): Flow<List<Project>>

    @Insert
    suspend fun addProject(project: Project)
}