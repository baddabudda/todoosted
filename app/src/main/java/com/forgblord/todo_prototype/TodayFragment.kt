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
import androidx.recyclerview.widget.LinearLayoutManager
import com.forgblord.todo_prototype.databinding.FragmentInboxBinding
import com.forgblord.todo_prototype.databinding.FragmentTodayBinding

class TodayFragment: Fragment() {
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
        Log.d("TODAY FRAGMENT", "TASKS: ${tasks[0].toString()}")
        binding.rvTodayList.adapter = TaskListAdapter(tasks)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}