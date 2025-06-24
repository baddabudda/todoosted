package com.forgblord.todo_prototype.data.viewmodels.legacy

import androidx.lifecycle.ViewModel

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