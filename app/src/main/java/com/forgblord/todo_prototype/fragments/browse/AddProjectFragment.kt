package com.forgblord.todo_prototype.fragments.browse

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.forgblord.todo_prototype.R
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.data.viewmodels.ProjectViewModel
import com.forgblord.todo_prototype.databinding.FragmentAddProjectSheetBinding
import com.forgblord.todo_prototype.fragments.dialogs.ColorPickerDialog
import com.forgblord.todo_prototype.utils.ProjectColors
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.random.Random


class AddProjectFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddProjectSheetBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val projectViewModel: ProjectViewModel by viewModels()

    private fun randomColorPicker(): Int { return Random.nextInt(0, 3) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProjectSheetBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            var color: Int = Color.BLACK

            btnAdd.setOnClickListener {
                val title = this.projectName.text.toString()
                val newProject = Project(
                    project_id=0,
                    title=title,
                    colorCode=color
                )

                projectViewModel.addProject(newProject)
                dismiss()
            }

            chipColor.setOnClickListener {
                findNavController().navigate(AddProjectFragmentDirections.actionAddProjectToColorPicker())
            }

            setFragmentResultListener(ColorPickerDialog.REQUEST_KEY_COLOR) { _, bundle ->
                val colorName = bundle.getString(ColorPickerDialog.BUNDLE_KEY_COLORTITLE)
                val colorId = bundle.getInt(ColorPickerDialog.BUNDLE_KEY_COLORID)
                color = colorId

                chipColor.chipIconTint = ColorStateList.valueOf(colorId)
                chipColor.text = colorName
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}