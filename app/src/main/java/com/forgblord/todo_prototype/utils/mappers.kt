package com.forgblord.todo_prototype.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.forgblord.todo_prototype.R

fun priorityToColor(context: Context, id: Int): Int = when (id) {
    1 -> ContextCompat.getColor(context, R.color.red_500)
    2 -> ContextCompat.getColor(context, R.color.yellow_500)
    3 -> ContextCompat.getColor(context, R.color.blue_500)
    else -> ContextCompat.getColor(context, R.color.gray)
}

fun priorityToString(context: Context, id: Int): String = when (id) {
    1 -> ContextCompat.getString(context, R.string.short_priority_1)
    2 -> ContextCompat.getString(context, R.string.short_priority_2)
    3 -> ContextCompat.getString(context, R.string.short_priority_3)
    else -> ContextCompat.getString(context, R.string.short_priority_4)
}

fun priorityToIcon(context: Context, id: Int): Drawable? = if (id == 4) {
    ContextCompat.getDrawable(context, R.drawable.ic_priority_none)
} else {
    ContextCompat.getDrawable(context, R.drawable.ic_priority)
}

fun menuToPriority(context: Context, menuString: String): Int = when (menuString) {
    ContextCompat.getString(context, R.string.menu_priority_1) -> 1
    ContextCompat.getString(context, R.string.menu_priority_2) -> 2
    ContextCompat.getString(context, R.string.menu_priority_3) -> 3
    else -> 4
}