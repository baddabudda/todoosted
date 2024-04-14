package com.forgblord.todo_prototype.utils

import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.setActivityTitle(title: String) {
    (activity as? AppCompatActivity)?.supportActionBar?.title = title
}

fun Fragment.popOnBackPress(flag: Boolean) {
    val callback = (activity as? AppCompatActivity)?.onBackPressedDispatcher?.addCallback(this) {
        findNavController().popBackStack()
    }
    callback?.isEnabled = true
}

