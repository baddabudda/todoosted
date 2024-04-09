package com.forgblord.todo_prototype.adapters

import android.icu.text.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.forgblord.todo_prototype.Task
import com.forgblord.todo_prototype.databinding.ItemTaskBinding
import com.forgblord.todo_prototype.interfaces.TaskInterface
import java.util.UUID

class TaskListViewHolder (
    private val binding: ItemTaskBinding,
    private val onCheckListener: (position: Int, id: UUID) -> Unit,
): RecyclerView.ViewHolder(binding.root) {
    fun bind(task: Task, onTaskClicked: (id: UUID) -> Unit) {
        binding.apply {
            checkBox.isChecked = task.completed
            taskTitle.text = task.title
            taskDate.visibility = View.GONE

            if (task.date != null) {
                taskDate.text = DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH_WEEKDAY_DAY).format(task.date)
                taskDate.visibility = View.VISIBLE
            }

            taskItemViewgroup.setOnClickListener {
                onTaskClicked(task.id)
            }

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) onCheckListener(bindingAdapterPosition, task.id)
            }
        }
    }
}

class TaskListAdapter (
    private val tasks: MutableList<Task>,
    private val onCheckListener: TaskInterface,
    private val onItemClickListener: (id: UUID) -> Unit,
): RecyclerView.Adapter<TaskListViewHolder>() {
    private var onBind: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskListViewHolder(binding) { position: Int, id: UUID -> removeOnceCompleted(position, id) }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val task = tasks[position]
        onBind = true
        holder.bind(task, onItemClickListener)
        onBind = false
    }

    private fun removeOnceCompleted(position: Int, id: UUID) {
        if (!onBind) {
            tasks.removeAt(tasks.indexOf(tasks.find{it.id == id}))
            onCheckListener.removeById(id)
            notifyItemRemoved(position)
        }
    }

}