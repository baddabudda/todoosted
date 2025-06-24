package com.forgblord.todo_prototype.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class RecordTask (
    @Embedded val record: TimeRecord,
    @Relation(
        entity = Task::class,
        parentColumn = "task_id",
        entityColumn = "task_id"
    )
    val task: Task
)