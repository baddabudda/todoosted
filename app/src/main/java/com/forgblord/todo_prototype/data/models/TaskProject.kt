package com.forgblord.todo_prototype.data.models

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class TaskProject(
    @Embedded val task: Task,
    @ColumnInfo(name="project_name")
    val projectName: String?,
)
