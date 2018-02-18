package com.example.konstantin.weartest.ui.hintitem.dialog

import android.content.Context
import android.support.v7.app.AlertDialog
import com.example.konstantin.weartest.R
import com.example.konstantin.weartest.extansions.inflate
import kotlinx.android.synthetic.main.dialog_image_source.view.*


class ImageSourceDialog {
    companion object {
        fun getInstance(context: Context, fromCamera: () -> Unit, fromGallery: () -> Unit): AlertDialog {
            val view = context.inflate(R.layout.dialog_image_source)
            with(view) {
                fromCameraButton.setOnClickListener { fromCamera.invoke() }
                fromGalleryButton.setOnClickListener { fromGallery.invoke() }
            }
            return AlertDialog.Builder(context)
                .setTitle(R.string.select_image_source)
                .setView(view)
                .create()
        }
    }
}