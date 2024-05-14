package com.forgblord.todo_prototype.fragments.browse

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.forgblord.todo_prototype.data.viewmodels.BrowseViewModel
import com.forgblord.todo_prototype.databinding.FragmentBrowseBinding
import com.forgblord.todo_prototype.fragments.browse.adapter.ProjectListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val PREDEFINED_CHILDREN_COUNT = 3

class BrowseFragment: Fragment() {
    private var _binding: FragmentBrowseBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val browseViewModel: BrowseViewModel by viewModels()

    private var prevSize: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBrowseBinding.inflate(inflater, container, false)
        binding.projectList.layoutManager = LinearLayoutManager(context)

        setupDefaultTabNavigation()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("BROWSE", "onViewCreated")

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                browseViewModel.projectList.collectLatest { list ->
                    binding.projectList.adapter = ProjectListAdapter(list) { project ->
                        findNavController().navigate(BrowseFragmentDirections.actionBrowseToProject(project))
                    }

//                    val curSize = binding.tabContainer.childCount - PREDEFINED_CHILDREN_COUNT

                    /*for (project in list.slice(curSize until list.size)) {
                        val projectView = inflateProject(project, binding.tabContainer)
                        binding.tabContainer.addView(projectView)
                    }*/
                }
            }
        }

        binding.apply {
            addProject.setOnClickListener {
                findNavController().navigate(BrowseFragmentDirections.actionBrowseToAddProject(null))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*private fun inflateProject(project: Project, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context).inflate(R.layout.item_project, parent, false)

        val projectTitle: TextView = inflater.findViewById(R.id.project_title)
        projectTitle.text = project.title

        val projectIcon: ImageView = inflater.findViewById(R.id.project_color)
        projectIcon.setColorFilter(project.colorCode)

        val projectDelete: Button = inflater.findViewById(R.id.btn_delete)
        projectDelete.setOnClickListener {
            projectViewModel.deleteProject(project)
            binding.tabContainer.removeView(inflater)
        }

        inflater.rootView.setOnClickListener {
            findNavController().navigate(BrowseFragmentDirections.actionBrowseToProject(project))
        }

        return inflater
    }*/

    private fun setupDefaultTabNavigation() {
        binding.completed.setOnClickListener {
            findNavController().navigate(BrowseFragmentDirections.actionBrowseToCompleted())
        }
    }
}