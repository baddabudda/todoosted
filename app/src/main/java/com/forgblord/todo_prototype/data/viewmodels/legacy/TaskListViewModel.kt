package com.forgblord.todo_prototype.data.viewmodels.legacy

import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date
import java.util.GregorianCalendar
import java.util.UUID
import kotlin.random.Random

class TaskListViewModel: ViewModel() {
    /*
    ======= DEPRECATED: MIGRATING TO DATABASE =======

    private fun generateTasks(): MutableList<Task> {
        val result = mutableListOf<Task>()

        for (i in 0 until 40) {
            val task = Task(
                id = UUID.randomUUID(),
                title = "Task #$i",
                completed = false,
                date = dateByProb()
            )

            result += task
        }
        return result
    }

    private fun dateByProb(): Date? {
        val prob = Random.nextBoolean()
        if (prob) {
            val start_date = GregorianCalendar(2024, 3, 4).timeInMillis
            val end_date = GregorianCalendar(2024, 3, 15).timeInMillis
            val date = Random.nextLong(start_date, end_date)

            return Date(date)
        }
        return null
    }

    fun getAllTasks(): MutableList<Task> {
        val result = mutableListOf<Task>()
//        Log.d("TASK VIEWMODEL", "ENTERING ARRAY OF TASKS")
        for (task in tasks) {
            result += task
        }
        return result
    }

    fun getAllDueToday(): MutableList<Task> {
        val result = mutableListOf<Task>()
//        Log.d("TASK VIEWMODEL", "ENTERING ARRAY OF TASKS")
        for (task in tasks) {
            if (task.date != null && DateUtils.isToday(task.date.time)) result += task
        }
        return result
    }

    fun removeTaskById(id: Int) {
        tasks.removeAt(tasks.indexOf(tasks.find{it.id == id}))
    }

    fun getTaskById(id: Int): Task {
        return tasks[tasks.indexOf(tasks.find { it.id == id })]
    }

    fun addTask(title: String, date: Date?) {
        val task = Task(
            id = UUID.randomUUID(),
            title = title,
            completed = false,
            date = date
        )

        tasks += task
    }

    ======= END OF DEPRICATION ===
     */
}