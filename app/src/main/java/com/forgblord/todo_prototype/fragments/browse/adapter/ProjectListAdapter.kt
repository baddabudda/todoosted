package com.forgblord.todo_prototype.fragments.browse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.forgblord.todo_prototype.R
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.databinding.ItemProjectBinding
import com.forgblord.todo_prototype.fragments.browse.BrowseFragmentDirections

class ProjectViewholder (
    private val binding: ItemProjectBinding,
    private val onProjectClickListener: (project: Project) -> Unit
): RecyclerView.ViewHolder(binding.root) {
    fun bind(project: Project) {
        binding.apply {
            projectColor.setColorFilter(project.colorCode)
            projectTitle.text = project.title

            root.setOnClickListener {
                onProjectClickListener(project)
            }
        }
    }
}

class ProjectListAdapter (
    private val projects: List<Project>,
    private val onProjectClickListener: (project: Project) -> Unit,
): RecyclerView.Adapter<ProjectViewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewholder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProjectBinding.inflate(inflater, parent, false)
        return ProjectViewholder(binding, onProjectClickListener)
    }

    override fun getItemCount(): Int {
        return projects.size
    }

    override fun onBindViewHolder(holder: ProjectViewholder, position: Int) {
        val project = projects[position]
        holder.bind(project)
    }

}