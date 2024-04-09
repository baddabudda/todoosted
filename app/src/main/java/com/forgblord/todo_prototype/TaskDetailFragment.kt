package com.forgblord.todo_prototype

import android.icu.text.DateFormat
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.forgblord.todo_prototype.databinding.FragmentTaskDetailsBinding
import java.util.UUID

private const val TAG: String = "TaskDetailFragment"

class TaskDetailFragment: Fragment() {
    private val args: TaskDetailFragmentArgs by navArgs()
    private var _binding: FragmentTaskDetailsBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val taskListViewModel: TaskListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Log.d(TAG, "Task id: ${args.taskId}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val task: Task = taskListViewModel.getTaskById(args.taskId)
        binding.apply {
            taskDetailProject.text = "Default Project"
            taskDetailTitle.text = task.title
            taskDetailDate.text = if (task.date == null) "Not set"
                                    else DateFormat
                                        .getPatternInstance(DateFormat.YEAR_ABBR_MONTH_WEEKDAY_DAY)
                                        .format(task.date)

            taskDetailCheckbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    onChecked(task.id)
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onChecked(id: UUID) {
        taskListViewModel.removeTaskById(id)
    }
}