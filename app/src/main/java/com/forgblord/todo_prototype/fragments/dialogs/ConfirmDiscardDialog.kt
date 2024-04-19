package com.forgblord.todo_prototype.fragments.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmDiscardDialog: DialogFragment() {
    interface ConfirmationListener {
        fun confirmAction()
        fun cancelAction()
    }

    private lateinit var listener: ConfirmationListener

    /*override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Discard changes?")
                .setMessage("The changes you've made will not be saved.")
                .setPositiveButton("Discard") {
                        dialog, _ ->  ConfirmationListener
                }
                .setNegativeButton("Cancel") {

                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }*/
}