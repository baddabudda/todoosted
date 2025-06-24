package com.forgblord.todo_prototype

import android.app.Application
import com.forgblord.todo_prototype.data.TodoDatabase

private const val DATABASE_NAME = "todo-database"

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        TodoDatabase.initialize(this)
    }
}