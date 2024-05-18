package com.forgblord.todo_prototype.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Task::class,
            parentColumns = arrayOf("task_id"),
            childColumns = arrayOf("task_id"),
            onUpdate = ForeignKey.NO_ACTION,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TimeRecord (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "record_id")
    val record_id: Int,
    var task_id: Int,
    val datetime_start: Date,
    val datetime_end: Date? = null,
    val duration: Long? = null,
)
