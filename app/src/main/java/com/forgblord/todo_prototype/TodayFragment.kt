package com.forgblord.todo_prototype

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.forgblord.todo_prototype.databinding.FragmentInboxBinding
import com.forgblord.todo_prototype.databinding.FragmentTodayBinding
import java.util.UUID

class TodayFragment: Fragment(), TaskListAdapter.OnItemCheckedListener {
    private var _binding: FragmentTodayBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val taskListViewModel: TaskListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.no_transition
        )

        enterTransition = animation
        exitTransition = animation
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        binding.rvTodayList.layoutManager = LinearLayoutManager(context)

        val tasks = taskListViewModel.getAllDueToday()
//        for (task in tasks) {
//            Log.d("TODAY FRAGMENT", "${task.title}")
//        }
        binding.rvTodayList.adapter = TaskListAdapter(tasks, this) { taskId ->
            findNavController().navigate(TodayFragmentDirections.openTask(taskId))
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemCheck(id: UUID) {
        taskListViewModel.removeTaskById(id)
    }
}