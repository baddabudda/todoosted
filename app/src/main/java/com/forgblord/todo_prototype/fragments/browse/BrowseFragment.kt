package com.forgblord.todo_prototype.fragments.browse

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.forgblord.todo_prototype.R
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.data.viewmodels.ProjectViewModel
import com.forgblord.todo_prototype.databinding.FragmentBrowseBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val PREDEFINED_CHILDREN_COUNT = 3

class BrowseFragment: Fragment() {
    private var _binding: FragmentBrowseBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

//    private val projectListViewModel: ProjectListViewModel by activityViewModels()
    private val projectViewModel: ProjectViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBrowseBinding.inflate(inflater, container, false)

        /*for (project in projects) {
            val projectView = populateProject(project, container)
            binding.tabContainer.addView(projectView)
        }*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                projectViewModel.projectList.collectLatest { list ->
                    val curSize = binding.tabContainer.childCount - PREDEFINED_CHILDREN_COUNT
                    Log.d("BROWSE", curSize.toString())
                    Log.d("BROWSE", list.size.toString())

                    for (project in list.slice(curSize until list.size)) {
                        val projectView = populateProject(project, binding.tabContainer)
                        binding.tabContainer.addView(projectView)
                    }
                }
            }
        }

        binding.apply {
            addProject.setOnClickListener {
                findNavController().navigate(BrowseFragmentDirections.actionAddProject())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("BROWSE FRAGMENT", "DESTROYED")
        _binding = null
    }

    fun populateProject(project: Project, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context).inflate(R.layout.item_project, parent, false)

        val projectTitle: TextView = inflater.findViewById(R.id.project_title)
        projectTitle.text = project.title

        val projectIcon: ImageView = inflater.findViewById(R.id.project_color)
        projectIcon.setColorFilter(project.colorCode)

        inflater.rootView.setOnClickListener {
            findNavController().navigate(BrowseFragmentDirections.openProject(project.id, project.title))
        }

        return inflater
    }
}