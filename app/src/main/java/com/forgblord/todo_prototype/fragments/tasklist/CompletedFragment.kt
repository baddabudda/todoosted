package com.forgblord.todo_prototype.fragments.tasklist

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.forgblord.todo_prototype.NavBottombarDirections
import com.forgblord.todo_prototype.utils.popOnBackPress
import com.forgblord.todo_prototype.utils.setActivityTitle

class CompletedFragment: TaskListFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityTitle("Completed")
        popOnBackPress(true)
    }

    override fun openTask(taskId: Int) {
        findNavController().navigate(NavBottombarDirections.openTask(taskId))
    }

    override fun initializeList() {
        taskListViewModel.getAllCompleted()
    }
}