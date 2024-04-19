package com.forgblord.todo_prototype.fragments.tasklist

import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.forgblord.todo_prototype.NavBottombarDirections
import com.forgblord.todo_prototype.utils.popOnBackPress

class ProjectFragment: TaskListFragment() {
    private val args: ProjectFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popOnBackPress(true)
    }

    override fun openTask(taskId: Int) {
        findNavController().navigate(NavBottombarDirections.openTask(taskId))
    }

    override fun onPause() {
        super.onPause()
        Log.d("LIST", "VIEW HAS BEEN PAUSED")
    }

    override fun initializeList() {
        Log.d("PROJECT", "${args.projectId}")
        taskListViewModel.getAllByProjectId(args.projectId)
    }
}