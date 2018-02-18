package com.example.konstantin.weartest.system

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import com.example.konstantin.weartest.BuildConfig
import java.io.File


/**
 * Helps to start out-oriented intents
 */
class OutIntentsHelper {

    private var context: Activity? = null

    fun attach(activity: Activity) {
        context = activity
    }

    fun dettach() {
        context = null
    }

    fun openGallery(resultCode: Int) = checkAndStartActivityForResult(
            Intent(Intent.ACTION_PICK)
                .apply {
                    type = "image/"
                },
            resultCode
    )

    fun openCamera(resultCode: Int, photoFile: File) = checkAndStartActivityForResult(
            Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .apply {
                    val photoUri = FileProvider.getUriForFile(context!!, BuildConfig.APPLICATION_ID + ".provider", photoFile)
                    putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                },
            resultCode
    )

    fun openDialer(phone: String) = checkAndStartActivity(
            Intent(Intent.ACTION_DIAL)
                .apply {
                    data = Uri.fromParts("tel", phone, null)
                }
                .addOutFlags()
    )

    private fun Intent.addOutFlags(clearTop: Boolean = false, notForRecents: Boolean = false): Intent {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK

        if (clearTop) addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        if (notForRecents) addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)

        return this
    }

    private fun checkAndStartActivity(activityIntent: Intent): Boolean =
        if (activityIntent.resolveActivity(context!!.packageManager) != null) {
            context!!.startActivity(activityIntent)
            true
        } else {
            false
        }

    private fun checkAndStartActivityForResult(activityIntent: Intent, resultCode: Int): Boolean =
        if (activityIntent.resolveActivity(context!!.packageManager) != null) {
            context!!.startActivityForResult(activityIntent, resultCode)
            true
        } else {
            false
        }
}