package com.forgblord.todo_prototype.fragments.browse

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.forgblord.todo_prototype.R
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.data.viewmodels.ProjectListViewModel
import com.forgblord.todo_prototype.databinding.FragmentBrowseBinding
import com.forgblord.todo_prototype.databinding.FragmentInboxBinding
import com.forgblord.todo_prototype.databinding.ItemProjectBinding
import com.forgblord.todo_prototype.fragments.browse.adapter.ProjectListAdapter
import com.forgblord.todo_prototype.interfaces.BaseFragment

class BrowseFragment: Fragment() {
    private var _binding: FragmentBrowseBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val projectListViewModel: ProjectListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBrowseBinding.inflate(inflater, container, false)
        val projects = projectListViewModel.getAllProjects()

        for (project in projects) {
            val projectView = populateProject(project, container)
            binding.tabContainer.addView(projectView)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun populateProject(project: Project, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context).inflate(R.layout.item_project, parent, false)

        val projectTitle: TextView = inflater.findViewById(R.id.project_title)
        projectTitle.text = project.title

        val projectIcon: ImageView = inflater.findViewById(R.id.project_color)
        projectIcon.setColorFilter(project.colorCode)

        return inflater
    }
}