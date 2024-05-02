package com.forgblord.todo_prototype.fragments.tasklist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.navigation.fragment.findNavController
import com.forgblord.todo_prototype.NavBottombarDirections
import com.forgblord.todo_prototype.R

class InboxFragment: TaskListFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun openTask(taskId: Int) {
        findNavController().navigate(NavBottombarDirections.openTask(taskId))
    }

    override fun initializeList() {
        taskListViewModel.getInbox()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_default, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}
