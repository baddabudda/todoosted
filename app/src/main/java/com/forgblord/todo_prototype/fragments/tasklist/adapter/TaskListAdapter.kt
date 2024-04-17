package com.forgblord.todo_prototype.fragments.tasklist.adapter

import android.graphics.Paint
import android.icu.text.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.forgblord.todo_prototype.data.models.Task
import com.forgblord.todo_prototype.databinding.ItemTaskBinding

class TaskListViewHolder (
    private val binding: ItemTaskBinding,
    private val onCheckListener: (task: Task) -> Unit,
): RecyclerView.ViewHolder(binding.root) {
    fun bind(task: Task, onTaskClicked: (id: Int) -> Unit, updateList: (position: Int) -> Unit) {
        binding.apply {
            taskTitle.text = task.title
            taskDate.visibility = View.GONE
            taskProject.text = "Inbox"

            if (task.proj_id != null) {
                taskProject.text = task.proj_id.toString()
            }

            if (task.date != null) {
                taskDate.text = DateFormat.getPatternInstance(DateFormat.YEAR_ABBR_MONTH_WEEKDAY_DAY).format(task.date)
                taskDate.visibility = View.VISIBLE
            }

            if (task.completed) {
                checkBox.isChecked = task.completed
                this.taskTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }

            taskItemViewgroup.setOnClickListener {
                onTaskClicked(task.task_id)
            }

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                this.taskTitle.paintFlags = if (isChecked) Paint.STRIKE_THRU_TEXT_FLAG else 0
                task.completed = isChecked
                updateList(absoluteAdapterPosition)
                onCheckListener(task)
            }
        }
    }
}

class TaskListAdapter (
    private val tasks: List<Task>,
    private val onCheckListener: (task: Task) -> Unit,
    private val onItemClickListener: (id: Int) -> Unit,
): RecyclerView.Adapter<TaskListViewHolder>() {
    private var onBind: Boolean = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskListViewHolder(binding, onCheckListener)
//        return TaskListViewHolder(binding) { position: Int, id: UUID -> removeOnceCompleted(position, id) }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val task = tasks[position]
        onBind = true
        holder.bind(task, onItemClickListener, { position -> removeOnceCompleted(position) })
//        holder.bind(task, onItemClickListener)
        onBind = false
    }

    private fun removeOnceCompleted(position: Int) {
        if (!onBind) notifyItemRemoved(position)
    }
}