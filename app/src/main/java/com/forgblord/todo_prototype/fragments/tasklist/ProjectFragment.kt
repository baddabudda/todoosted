package com.forgblord.todo_prototype.fragments.tasklist

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.forgblord.todo_prototype.utils.popOnBackPress
import com.forgblord.todo_prototype.utils.setActivityTitle

class ProjectFragment: TaskListFragment() {
    private val args: ProjectFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityTitle(args.projectTitle)
        popOnBackPress(true)
    }
    override fun openTask(taskId: Int) {
        findNavController().navigate(ProjectFragmentDirections.openTask(taskId))
    }

    override fun initializeList() {
        taskListViewModel.getAllByProjectId()
    }
}