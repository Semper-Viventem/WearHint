package com.example.konstantin.weartest.ui.hintitem.dialog

import android.content.Context
import android.support.v7.app.AlertDialog
import com.example.konstantin.weartest.R
import com.example.konstantin.weartest.extensions.inflate
import kotlinx.android.synthetic.main.dialog_add_text.view.*


class AddTextDialog {

    companion object {
        fun getInstance(context: Context, onTextChanged: (text: String) -> Unit): AlertDialog {
            val view = context.inflate(R.layout.dialog_add_text)
            return AlertDialog.Builder(context)
                .setView(view)
                .setNegativeButton(R.string.cancel, { _, _ -> })
                .setPositiveButton(R.string.save, { _, _ -> onTextChanged.invoke(view.editText.text.toString()) })
                .create()
        }
    }
}