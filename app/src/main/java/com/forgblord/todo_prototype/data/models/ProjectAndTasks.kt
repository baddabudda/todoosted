package com.forgblord.todo_prototype.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class ProjectAndTasks(
    @Embedded val project: Project,
    @Relation(
        parentColumn = "project_id",
        entityColumn = "proj_id"
    )
    val tasks: List<Task>
)
