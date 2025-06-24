package com.forgblord.todo_prototype.fragments.tasklist

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.forgblord.todo_prototype.MainActivity
import com.forgblord.todo_prototype.NavBottombarDirections
import com.forgblord.todo_prototype.R
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.data.viewmodels.TaskCRUD
import com.forgblord.todo_prototype.databinding.FragmentTasklistBinding
import com.forgblord.todo_prototype.fragments.track.StopWatch

open class TaskListFragment(): Fragment() {
    private var _binding: FragmentTasklistBinding? = null
    protected val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    protected val taskModel: TaskCRUD by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTasklistBinding.inflate(inflater, container, false)
        binding.rvTaskList.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                taskListViewModel.taskList.collect { list ->
                    binding.rvTaskList.adapter = TaskListAdapter(list,
                        { task -> updateOnCheck(task)},
                        { taskId -> openTask(taskId) }
                    )
                }
            }
        }
    }*/

    protected fun openTask(taskId: Int) {
        findNavController().navigate(NavBottombarDirections.openTask(taskId))
    }

    protected fun updateOnCheck(task: Task) {
        taskModel.updateTask(task)
    }

    protected fun actionToTrack(taskId: Int) {
        (activity as MainActivity).mbinding.bottomNavigation.selectedItemId = R.id.track
        findNavController().navigate(R.id.start_track, bundleOf(StopWatch.taskKey to taskId))
//        findNavController().navigate(Uri.parse("android-app://com.forgblord.todo_prototype/fragments/track/Stopwatch/{$title}"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("LIST", "VIEW HAS BEEN DESTROYED")
        _binding = null
    }
}