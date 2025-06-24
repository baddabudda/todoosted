package com.forgblord.todo_prototype.fragments.tasklist.adapter

import android.content.res.ColorStateList
import android.graphics.Paint
import android.icu.text.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.data.models.TaskProject
import com.forgblord.todo_prototype.databinding.ItemTaskBinding
import com.forgblord.todo_prototype.utils.priorityToColor

class TaskListViewHolder (
    private val binding: ItemTaskBinding,
    private val onCheckListener: (task: Task) -> Unit,
    private val onTrackClicked: (taskId: Int) -> Unit,
): RecyclerView.ViewHolder(binding.root) {
    fun bind(taskProject: TaskProject, onTaskClicked: (id: Int) -> Unit, updateList: (position: Int) -> Unit) {
        binding.apply {
            taskTitle.text = taskProject.task.title
            taskDate.visibility = View.GONE
            projectTitle.text = taskProject.projectName ?: "Inbox"

            if (taskProject.task.date != null) {
                taskDate.text = DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH_WEEKDAY_DAY).format(taskProject.task.date)
                taskDate.visibility = View.VISIBLE
            }

            if (taskProject.task.completed) {
                checkBox.isChecked = taskProject.task.completed
                this.taskTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }

            taskItemViewgroup.setOnClickListener {
                onTaskClicked(taskProject.task.task_id)
            }

            checkBox.buttonTintList = ColorStateList.valueOf(priorityToColor(root.context,
                                                                            taskProject.task.priority))

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                this.taskTitle.paintFlags = if (isChecked) Paint.STRIKE_THRU_TEXT_FLAG else 0
                taskProject.task.completed = isChecked
                updateList(absoluteAdapterPosition)
                onCheckListener(taskProject.task)
            }

            btnStartTrack.setOnClickListener {
                onTrackClicked(taskProject.task.task_id)
            }
        }
    }
}

class TaskListAdapter (
    private val tasks: List<TaskProject>,
    private val onCheckListener: (task: Task) -> Unit,
    private val onItemClickListener: (id: Int) -> Unit,
    private val onTrackClicked: (taskId: Int) -> Unit,
): RecyclerView.Adapter<TaskListViewHolder>() {
    private var onBind: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskListViewHolder(binding, onCheckListener, onTrackClicked)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val task = tasks[position]
        onBind = true
        holder.bind(task, onItemClickListener, { position -> removeOnceCompleted(position) })
        onBind = false
    }

    private fun removeOnceCompleted(position: Int) {
        if (!onBind) notifyItemRemoved(position)
    }
}