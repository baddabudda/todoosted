package com.forgblord.todo_prototype.data.models

import java.util.UUID

data class Project(
    val id: UUID,
    val title: String,
    val colorCode: Int,
)
