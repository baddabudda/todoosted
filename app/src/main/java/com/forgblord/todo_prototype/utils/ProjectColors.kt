package com.forgblord.todo_prototype.utils

import android.content.Context
import com.forgblord.todo_prototype.R

data class ProjectColors(
    val context: Context,
    val colorMap: Map<String, Int> = mapOf(
        "Magenta" to context.getColor(R.color.magenta_300),
        "Cyan" to context.getColor(R.color.cyan_300),
        "Purple" to context.getColor(R.color.purple_300),
        "Blue" to context.getColor(R.color.blue_300),
        "Orange" to context.getColor(R.color.orange_300),
        "Yellow" to context.getColor(R.color.yellow_300),
        "Red" to context.getColor(R.color.red_300),
        "Green" to context.getColor(R.color.green_300),
        "Black" to context.getColor(R.color.black)
    )
//    val colorMapper: List<Pair<String, Int>> = listOf(
//        "Magenta" to context.getColor(R.color.magenta_300),
//        "Cyan" to context.getColor(R.color.cyan_300),
//        "Purple" to context.getColor(R.color.purple_300),
//        "Blue" to context.getColor(R.color.blue_300),
//        "Orange" to context.getColor(R.color.orange_300),
//        "Yellow" to context.getColor(R.color.yellow_300),
//        "Red" to context.getColor(R.color.red_300),
//        "Green" to context.getColor(R.color.green_300),
//        "Black" to context.getColor(R.color.black)
//    )
) {
    fun getList(): List<Pair<String, Int>> {
        return colorMap.toList()
    }

    fun getName(color: Int): String {
        return colorMap.filterValues { it == color }.keys.first()
    }
}
