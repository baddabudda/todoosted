package com.forgblord.todo_prototype.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Project::class,
            parentColumns = arrayOf("project_id"),
            childColumns = arrayOf("proj_id"),
            onUpdate = ForeignKey.NO_ACTION,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Task (
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "task_id")
    val task_id: Int,
    var proj_id: Int? = null,
    val title: String,
    var completed: Boolean = false,
    val date: Date?,
    val priority: Int = 4,
)
