package com.forgblord.todo_prototype.fragments.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.forgblord.todo_prototype.Task
import com.forgblord.todo_prototype.databinding.FragmentTodayBinding
import com.forgblord.todo_prototype.fragments.base.TaskListFragment

class TodayFragment: TaskListFragment<FragmentTodayBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTodayBinding
        get() = FragmentTodayBinding::inflate
    override val getRecyclerView: (FragmentTodayBinding) -> RecyclerView
        get() = FragmentTodayBinding::rvTodayList
    override val data: MutableList<Task>
        get() = taskListViewModel.getAllDueToday()
}