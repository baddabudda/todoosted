package com.forgblord.todo_prototype.fragments.tasklist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.forgblord.todo_prototype.MainActivity
import com.forgblord.todo_prototype.R
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.data.viewmodels.ProjectCRUD
import com.forgblord.todo_prototype.data.viewmodels.ProjectViewModel
import com.forgblord.todo_prototype.data.viewmodels.ProjectViewModelFactory
import com.forgblord.todo_prototype.fragments.tasklist.adapter.TaskListAdapter
import com.forgblord.todo_prototype.utils.popOnBackPress
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class ProjectFragment: TaskListFragment() {
    private val args: ProjectFragmentArgs by navArgs()
    private lateinit var project: Project

    private val projectCRUD: ProjectCRUD by viewModels()

    private val projectViewModel: ProjectViewModel by viewModels {
        ProjectViewModelFactory(args.project.project_id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
        popOnBackPress(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_default, menu)
        inflater.inflate(R.menu.menu_project, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                projectViewModel.projectState.filter {
                    projectState -> projectState.project.project_id != 0
                }.collect { projectState ->
                    project = projectState.project
                    changeTitle(projectState.project.title)

                    binding.rvTaskList.adapter = TaskListAdapter(projectState.projectList,
                        { task -> updateOnCheck(task)},
                        { taskId -> openTask(taskId) }
                    )
                }
            }
        }
    }

    private fun changeTitle(title: String) {
        (activity as MainActivity).supportActionBar!!.title = title
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
                projectCRUD.deleteProject(project)
                findNavController().popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}