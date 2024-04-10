package com.forgblord.todo_prototype.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.databinding.FragmentTodayBinding
import java.util.UUID

class TodayFragment: TaskListFragment<FragmentTodayBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTodayBinding
        get() = FragmentTodayBinding::inflate
    override val getRecyclerView: (FragmentTodayBinding) -> RecyclerView
        get() = FragmentTodayBinding::rvTodayList
    override val getOnClickNavigation: (id: UUID) -> Unit
        get() = { id ->
            findNavController().navigate(
                InboxFragmentDirections.openTask(id)
            )

        }
    override val data: MutableList<Task>
        get() = taskListViewModel.getAllDueToday()
}