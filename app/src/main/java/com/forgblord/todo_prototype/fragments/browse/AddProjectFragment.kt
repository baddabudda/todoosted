package com.forgblord.todo_prototype.fragments.browse

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.data.viewmodels.ProjectCRUD
import com.forgblord.todo_prototype.databinding.FragmentAddProjectSheetBinding
import com.forgblord.todo_prototype.fragments.dialogs.ColorPickerDialog
import com.forgblord.todo_prototype.utils.ProjectColors
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class AddProjectFragment : BottomSheetDialogFragment() {
    private val args: AddProjectFragmentArgs by navArgs()
    private var editMode: Boolean = false
    private var project: Project? = null

    private var _binding: FragmentAddProjectSheetBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val projectCRUD: ProjectCRUD by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        project = args.project
        editMode = project != null
        super.onCreate(savedInstanceState)
    }

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

        if (editMode) {
            binding.projectName.setText(project!!.title)
            binding.chipColor.chipIconTint = ColorStateList.valueOf(project!!.colorCode)
        }

        binding.apply {
            var title = this.projectName.text.toString()
            var color = this.chipColor.chipIconTint!!.defaultColor

            btnAdd.setOnClickListener {
                title = this.projectName.text.toString()
                color = this.chipColor.chipIconTint!!.defaultColor

                saveProject(title, color)
                dismiss()
            }

            chipColor.text = ProjectColors(requireContext()).getName(color)
            chipColor.setOnClickListener {
                findNavController().navigate(AddProjectFragmentDirections.actionAddProjectToColorPicker())
            }

            setFragmentResultListener(ColorPickerDialog.REQUEST_KEY_COLOR) { _, bundle ->
                val colorName = bundle.getString(ColorPickerDialog.BUNDLE_KEY_COLORTITLE)
                val colorId = bundle.getInt(ColorPickerDialog.BUNDLE_KEY_COLORID)

                chipColor.chipIconTint = ColorStateList.valueOf(colorId)
                chipColor.text = colorName
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveProject(title: String, color: Int) {
        val projectId = if (editMode) project!!.project_id
                        else 0

        val project = Project(
            project_id=projectId,
            title=title,
            colorCode=color
        )

        if (editMode) projectCRUD.updateProject(project)
        else projectCRUD.addProject(project)
    }
}