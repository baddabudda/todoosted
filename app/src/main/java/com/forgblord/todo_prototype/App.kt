package com.forgblord.todo_prototype

import android.app.Application
import com.forgblord.todo_prototype.data.repository.TaskRepository

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        TaskRepository.initialize(this)
    }
}