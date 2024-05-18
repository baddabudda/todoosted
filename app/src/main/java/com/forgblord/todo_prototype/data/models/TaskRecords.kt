package com.forgblord.todo_prototype.data.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation

data class TaskRecords(
    @Embedded val record: TimeRecord,
    @ColumnInfo(name="task_title")
    val taskTitle: String,
)
