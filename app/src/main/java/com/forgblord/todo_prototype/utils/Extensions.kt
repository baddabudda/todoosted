package com.forgblord.todo_prototype.utils

import android.app.Activity
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.forgblord.todo_prototype.MainActivity

fun Fragment.popOnBackPress(flag: Boolean) {
    val callback = (activity as? AppCompatActivity)?.onBackPressedDispatcher?.addCallback(this) {
        findNavController().popBackStack()
    }
    callback?.isEnabled = true
}

fun Activity.getSupportActionBar() {

}