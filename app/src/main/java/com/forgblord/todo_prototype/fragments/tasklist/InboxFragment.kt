package com.forgblord.todo_prototype.fragments.tasklist

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.forgblord.todo_prototype.R
import com.forgblord.todo_prototype.data.viewmodels.InboxViewModel
import com.forgblord.todo_prototype.fragments.tasklist.adapter.TaskListAdapter
import kotlinx.coroutines.launch

class InboxFragment: TaskListFragment() {
    private val inboxViewModel: InboxViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                inboxViewModel.taskList.collect { list ->
                    binding.rvTaskList.adapter = TaskListAdapter(list,
                        { task -> updateOnCheck(task)},
                        { taskId -> openTask(taskId) },
                        { taskTitle -> actionToTrack(taskTitle) }
                    )
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_default, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}
