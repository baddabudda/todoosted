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
    private val projects: MutableList<Project> = generateProjects()

    private fun randomColorPicker(): Int { return Random.nextInt(0, 3) }

    private fun generateProjects(): MutableList<Project> {
        val result = mutableListOf<Project>()

        for (i in 0 until 3) {
            val project = Project(
                id=UUID.randomUUID(),
                title="Project #$i",
                colorCode=colors[randomColorPicker()]
            )

            Log.d("PROJECT VIEWMODEL", "${project.title}")

            result += project
        }

        return result
    }

    fun getAllProjects(): List<Project> {
        return projects.toList()
    }

    fun sayHello() {
        Log.d("PROJECT VIEWMODEL", "HELLO!")
    }
}