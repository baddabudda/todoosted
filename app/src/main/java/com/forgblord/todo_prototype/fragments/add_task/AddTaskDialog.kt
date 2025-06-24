package com.forgblord.todo_prototype.fragments.add_task

import android.annotation.SuppressLint
import android.graphics.drawable.InsetDrawable
import android.icu.text.DateFormat
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MenuRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.forgblord.todo_prototype.R
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.data.viewmodels.TaskCRUD
import com.forgblord.todo_prototype.databinding.FragmentAddTaskSheetBinding
import com.forgblord.todo_prototype.fragments.datepicker.DatePickerFragment
import com.forgblord.todo_prototype.fragments.dialogs.PriorityDialog
import com.forgblord.todo_prototype.fragments.projectpicker.ProjectPickerDialog
import com.forgblord.todo_prototype.utils.menuToPriority
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Date

class AddTaskDialog: BottomSheetDialogFragment() {
    private var _binding: FragmentAddTaskSheetBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val taskCRUD: TaskCRUD by viewModels()

    var date: Date? = null
    var projectId: Int? = null
    var priorityId: Int = 4

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
                    title = title,
                    date = date,
                    priority = priorityId)
                taskCRUD.addTask(newTask)
                findNavController().navigateUp()
            }

            chipDate.setOnClickListener {
                findNavController().navigate(AddTaskDialogDirections.selectDate())
            }

            chipProject.setOnClickListener {
                findNavController().navigate(AddTaskDialogDirections.actionAddTaskToProjectPicker())
            }

            chipPriority.setOnClickListener {
                showMenu(it, R.menu.menu_priority)
//                findNavController().navigate(AddTaskDialogDirections.actionAddTaskToPriorityDialog())
            }

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

            projectId = projId
            binding.chipProject.text = projectTitle.toString()
        }

        /*setFragmentResultListener(PriorityDialog.REQUEST_KEY_PRIORITY) { _, bundle ->
            priorityId = bundle.getInt(PriorityDialog.BUNDLE_KEY_ID)
            binding.chipPriority.text = bundle.getString(PriorityDialog.BUNDLE_KEY_TITLE)
        }*/
    }

    private fun showMenu(view: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), view)
        popup.menuInflater.inflate(menuRes, popup.menu)
        popup.setForceShowIcon(true)

        popup.setOnMenuItemClickListener {
            binding.chipPriority.text = it.title.toString().substringBefore(" ")
            binding.chipPriority.chipIcon = it.icon
            binding.chipPriority.chipIconTint = it.iconTintList
            priorityId = menuToPriority(binding.root.context, it.title.toString())
            popup.dismiss()
            true
        }

        popup.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}