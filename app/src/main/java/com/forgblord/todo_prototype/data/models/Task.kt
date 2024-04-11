package com.forgblord.todo_prototype.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID
@Entity
data class Task (
    @PrimaryKey (autoGenerate = true)
    val id: Int,
    val title: String,
    var completed: Boolean = false,
    val date: Date?,
)