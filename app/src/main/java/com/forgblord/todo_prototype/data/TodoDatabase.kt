package com.forgblord.todo_prototype.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.forgblord.todo_prototype.data.models.Task

@Database(entities = [Task::class], version=1)
@TypeConverters(TaskTypeConverter::class)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}