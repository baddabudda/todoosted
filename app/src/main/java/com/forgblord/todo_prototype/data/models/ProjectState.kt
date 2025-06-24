package com.forgblord.todo_prototype.data.models

import androidx.room.ColumnInfo

data class ProjectState(
    val projectList: List<TaskProject>,
    @ColumnInfo(name="project_name")
    val project: Project,
)
