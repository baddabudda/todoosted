package com.forgblord.todo_prototype.fragments.tasklist

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.forgblord.todo_prototype.MainActivity
import com.forgblord.todo_prototype.NavBottombarDirections
import com.forgblord.todo_prototype.R
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.data.viewmodels.ProjectViewModel
import com.forgblord.todo_prototype.utils.popOnBackPress

class ProjectFragment: TaskListFragment() {
    private val args: ProjectFragmentArgs by navArgs()
    private lateinit var project: Project

    private val projectViewModel: ProjectViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        project = args.project

        (activity as MainActivity).supportActionBar?.title = project.title

        setHasOptionsMenu(true)
        popOnBackPress(true)
    }

    override fun openTask(taskId: Int) {
        findNavController().navigate(NavBottombarDirections.openTask(taskId))
    }

    override fun initializeList() {
        taskListViewModel.getAllByProjectId(project.project_id)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_default, menu)
        inflater.inflate(R.menu.menu_project, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort -> {
                TODO("Not yet implemented")
            }
            R.id.completed -> {
                TODO("Not yet implemented")
            }
            R.id.edit -> {
                findNavController().navigate(ProjectFragmentDirections.actionProjectToEditflow(project))
                true
            }
            R.id.archive -> {
                TODO("Not yet implemented")
            }
            R.id.delete -> {
                projectViewModel.deleteProject(project)
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}