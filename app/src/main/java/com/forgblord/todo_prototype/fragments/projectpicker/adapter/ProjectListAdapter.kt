package com.forgblord.todo_prototype.fragments.projectpicker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.databinding.ItemProjectBinding

class ProjectViewholder (
    private val binding: ItemProjectBinding,
): RecyclerView.ViewHolder(binding.root) {
    fun bind(project: Project, onProjectPicked: (project: Project) -> Unit) {
        binding.apply {
            projectTitle.text = project.title
            root.setOnClickListener {
                onProjectPicked(project)
            }
        }
    }
}

class ProjectListAdapter (
    private val projects: List<Project>,
    private val onProjectPicked: (project: Project) -> Unit,
): RecyclerView.Adapter<ProjectViewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewholder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProjectBinding.inflate(inflater, parent, false)
        return ProjectViewholder(binding)
    }

    override fun getItemCount(): Int {
        return projects.size
    }

    override fun onBindViewHolder(holder: ProjectViewholder, position: Int) {
        val project = projects[position]
        holder.bind(project, onProjectPicked)
    }

}