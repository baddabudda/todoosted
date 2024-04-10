package com.forgblord.todo_prototype.data.models

import java.util.Date
import java.util.UUID

data class Task (
    val id: UUID,
    val title: String,
    var completed: Boolean = false,
    val date: Date?,
)