package com.forgblord.todo_prototype.fragments.add_task

import android.icu.text.DateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.data.viewmodels.TaskViewModel
import com.forgblord.todo_prototype.databinding.FragmentAddTaskSheetBinding
import com.forgblord.todo_prototype.fragments.datepicker.DatePickerFragment
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
        var date: Date? = null

        binding.apply {
            chipDate.text = "Not set"
            btnAdd.setOnClickListener {
                val title = this.taskName.text.toString()
                val newTask = Task(
                    0,
                    title,
                    false,
                    date)
                taskListViewModel.addTask(newTask)
                dismiss()
            }

            btnCancel.setOnClickListener {
                dismiss()
            }

            chipDate.setOnClickListener {
                findNavController().navigate(AddTaskDialogDirections.selectDate())
            }
        }

        setFragmentResultListener(DatePickerFragment.REQUEST_KEY_DATE) { _, bundle ->
            date = bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date

            if (date != null) {
                binding.chipDate.text = DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH_WEEKDAY_DAY).format(date)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}