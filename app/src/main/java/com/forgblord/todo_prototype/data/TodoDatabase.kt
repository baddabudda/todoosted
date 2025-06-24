package com.forgblord.todo_prototype.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.data.models.TimeRecord
import java.lang.IllegalStateException

@Database(entities = [Task::class, Project::class, TimeRecord::class], version=8)
@TypeConverters(TaskTypeConverter::class)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun projectDao(): ProjectDao
    abstract fun recordDao(): RecordDao

    companion object {
        @Volatile var INSTANCE: TodoDatabase? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = Room
                    .databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java,
                        "todo-db"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }

        fun get(): TodoDatabase {
            return INSTANCE?:
            throw IllegalStateException("TaskRepository must be initialized")
        }
    }
}