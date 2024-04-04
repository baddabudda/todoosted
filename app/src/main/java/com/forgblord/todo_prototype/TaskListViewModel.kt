package com.forgblord.todo_prototype

import android.text.format.DateUtils
import androidx.lifecycle.ViewModel
import java.util.Date
import java.util.UUID
import kotlin.random.Random

class TaskListViewModel: ViewModel() {
    private val tasks = generateTasks()

    private fun generateTasks(): List<Task> {
        val result = mutableListOf<Task>()

        for (i in 0 until 50) {
            val task = Task(
                id = UUID.randomUUID(),
                title = "Task #$i",
                completed = i % 2 == 0,
                date = dateByProb()
            )

            result += task
        }
        return result
    }

    private fun dateByProb(): Date? {
        val prob = Random.nextBoolean()
        if (prob) return Date()
        return null
    }

    fun getAllTasks(): List<Task> {
        return tasks
    }

    fun getAllDueToday(): List<Task> {
        val result = mutableListOf<Task>()

        for (task in tasks) {
            if (task.date != null && DateUtils.isToday(task.date.time)) result += task
        }
        return result
    }
}