package com.forgblord.todo_prototype.data.models

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class TaskRecord(
    @Embedded val record: TimeRecord,
    @ColumnInfo(name="task_title")
    val taskTitle: String,
)
