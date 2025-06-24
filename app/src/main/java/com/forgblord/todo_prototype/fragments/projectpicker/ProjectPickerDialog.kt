package com.forgblord.todo_prototype.fragments.projectpicker

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.data.viewmodels.BrowseViewModel
import com.forgblord.todo_prototype.databinding.FragmentProjectPickBinding
import com.forgblord.todo_prototype.fragments.projectpicker.adapter.ProjectListAdapter
import kotlinx.coroutines.launch

class ProjectPickerDialog: DialogFragment() {
    private val dummyProjects = listOf("Biba", "Boba", "Adoba")

    private var _binding: FragmentProjectPickBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val browseViewModel: BrowseViewModel by viewModels()

    override fun onStart() {
        super.onStart()

        val density = requireContext().resources.displayMetrics.density

        val dialogWidth = 300 // specify a value here
        val dialogHeight = 300 // specify a value here

        dialog?.window?.apply {
            setLayout((dialogWidth * density).toInt(), (dialogHeight * density).toInt())
            setGravity(Gravity.CENTER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProjectPickBinding.inflate(inflater, container, false)
        binding.rvProjects.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                browseViewModel.projectList.collect { list ->
                    binding.rvProjects.adapter = ProjectListAdapter(list) { project -> onProjectPicked(project) }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onProjectPicked(project: Project) {
//        Toast.makeText(context, "Picked project", Toast.LENGTH_SHORT).show()
        setFragmentResult(REQUEST_KEY_PROJECT, bundleOf(BUNDLE_KEY_ID to project.project_id,
                                                                BUNDLE_KEY_TITLE to project.title))
        dismiss()
    }

    companion object {
        const val REQUEST_KEY_PROJECT = "REQUEST_KEY_PROJECT"
        const val BUNDLE_KEY_ID = "BUNDLE_KEY_ID"
        const val BUNDLE_KEY_TITLE = "BUNDLE_KEY_TITLE"
    }
}