package com.forgblord.todo_prototype.fragments.tasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.forgblord.todo_prototype.NavBottombarDirections
import com.forgblord.todo_prototype.R
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.data.viewmodels.TaskViewModel
import com.forgblord.todo_prototype.databinding.FragmentInboxBinding
import com.forgblord.todo_prototype.fragments.tasklist.adapter.TaskListAdapter
import kotlinx.coroutines.launch

class InboxFragment: TaskListFragment() {

    override fun openTask(taskId: Int) {
        findNavController().navigate(NavBottombarDirections.openTask(taskId))
    }

    override fun initializeList() {
        taskListViewModel.getInbox()
    }

}

/*
class InboxFragment: Fragment()  {
    private var _binding: FragmentInboxBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    private val taskListViewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInboxBinding.inflate(inflater, container, false)
        binding.rvInboxList.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                taskListViewModel.taskList.collect { list ->
                    binding.rvInboxList.adapter = TaskListAdapter(list,
                        {task -> updateOnCheck(task)},
                        { taskId -> findNavController().navigate(InboxFragmentDirections.openTask(taskId)) }
                    )
                }
            }
        }
    }

    private fun updateOnCheck(task: Task) {
        taskListViewModel.updateTask(task)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}*/
