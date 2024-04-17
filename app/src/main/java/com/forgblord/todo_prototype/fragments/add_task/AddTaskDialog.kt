package com.forgblord.todo_prototype.fragments.add_task

import android.icu.text.DateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.data.viewmodels.TaskViewModel
import com.forgblord.todo_prototype.databinding.FragmentAddTaskSheetBinding
import com.forgblord.todo_prototype.fragments.datepicker.DatePickerFragment
import com.forgblord.todo_prototype.fragments.projectpicker.ProjectPickerDialog
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Date

class AddTaskDialog: BottomSheetDialogFragment() {
    private var _binding: FragmentAddTaskSheetBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val taskListViewModel: TaskViewModel by viewModels()

    var date: Date? = null
    var projectId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTaskSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            chipDate.text = "Not set"
            btnAdd.setOnClickListener {
                val title = this.taskName.text.toString()
                val newTask = Task(
                    task_id = 0,
                    proj_id = projectId,
                    title=title,
                    date=date)
                taskListViewModel.addTask(newTask)
                dismiss()
            }

            chipDate.setOnClickListener {
                findNavController().navigate(AddTaskDialogDirections.selectDate())
            }

            chipProject.setOnClickListener {
                findNavController().navigate(AddTaskDialogDirections.actionAddTaskToProjectPicker())
            }

            /*btnCancel.setOnClickListener {
                dismiss()
            }*/

        }

        setFragmentResultListener(DatePickerFragment.REQUEST_KEY_DATE) { _, bundle ->
            date = bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date

            if (date != null) {
                binding.chipDate.text = DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH_WEEKDAY_DAY).format(date)
            }
        }

        setFragmentResultListener(ProjectPickerDialog.REQUEST_KEY_PROJECT) { _, bundle ->
            val projId = bundle.getInt(ProjectPickerDialog.BUNDLE_KEY_ID)
            val projectTitle = bundle.getString(ProjectPickerDialog.BUNDLE_KEY_TITLE)
            Log.d("DIALOG", projectTitle.toString())

            projectId = projId
            binding.chipProject.text = projectTitle.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}