package com.forgblord.todo_prototype.data.viewmodels

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.ViewModel
import com.forgblord.todo_prototype.data.models.Project
import java.util.UUID
import kotlin.random.Random

class ProjectListViewModel: ViewModel() {
    private val colors: List<Int> = listOf(
        Color.parseColor("#BA68C8"),
        Color.parseColor("#4DD0E1"),
        Color.parseColor("#FFD54F")
    )
    private lateinit var projects: MutableList<Project>

    init {
        for (i in 0 until 3) {
            val project = Project(
                id=UUID.randomUUID(),
                title="Project #$i",
                colorCode=colors[randomColorPicker()]
            )

            Log.d("PROJECT VIEWMODEL", "${project.title}")

            projects += project
        }
    }

    private fun randomColorPicker(): Int { return Random.nextInt(0, 3) }

    fun getAllProjects(): List<Project> {
        return projects.toList()
    }

    fun sayHello() {
        Log.d("PROJECT VIEWMODEL", "HELLO!")
    }
}