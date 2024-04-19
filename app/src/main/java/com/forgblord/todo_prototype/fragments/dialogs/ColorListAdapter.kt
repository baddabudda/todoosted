package com.forgblord.todo_prototype.fragments.dialogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.forgblord.todo_prototype.databinding.ItemColorBinding

class ColorViewHolder(
    private val binding: ItemColorBinding,
): RecyclerView.ViewHolder(binding.root) {
    fun bind(colorPair: Pair<String, Int>, onProjectPicked: (Pair<String, Int>) -> Unit) {
        binding.apply {
            colorTitle.text = colorPair.first
            color.setColorFilter(colorPair.second)

            root.setOnClickListener {
                onProjectPicked(colorPair)
            }
        }
    }
}

class ColorListAdapter (
    private val colorMap: List<Pair<String, Int>>,
    private val onColorPicked: (colorPair: Pair<String, Int>) -> Unit
): RecyclerView.Adapter<ColorViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemColorBinding.inflate(inflater, parent, false)
        return ColorViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return colorMap.size
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        val colorPair = colorMap[position]
        holder.bind(colorPair, onColorPicked)
    }

}