package com.forgblord.todo_prototype.fragments.browse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.forgblord.todo_prototype.data.models.Project
import com.forgblord.todo_prototype.databinding.ItemProjectBinding

class ProjectListViewHolder (
    private val binding: ItemProjectBinding,
): RecyclerView.ViewHolder(binding.root) {
    fun bind(project: Project) {
        binding.apply {
            projectColor.setColorFilter(project.colorCode)
            projectTitle.text = project.title
        }
    }
}

class ProjectListAdapter (
    private val projects: List<Project>
): RecyclerView.Adapter<ProjectListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProjectBinding.inflate(inflater, parent, false)
        return ProjectListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return projects.size
    }

    override fun onBindViewHolder(holder: ProjectListViewHolder, position: Int) {
        val project = projects[position]
        holder.bind(project)
    }

}