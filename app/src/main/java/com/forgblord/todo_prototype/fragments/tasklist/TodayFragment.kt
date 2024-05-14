package com.forgblord.todo_prototype.fragments.tasklist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.forgblord.todo_prototype.data.viewmodels.TodayViewModel
import com.forgblord.todo_prototype.fragments.tasklist.adapter.TaskListAdapter
import kotlinx.coroutines.launch

class TodayFragment: TaskListFragment() {
    private val todayViewModel: TodayViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                todayViewModel.taskList.collect { list ->
                    binding.rvTaskList.adapter = TaskListAdapter(list,
                        { task -> updateOnCheck(task)},
                        { taskId -> openTask(taskId) }
                    )
                }
            }
        }
    }
}
