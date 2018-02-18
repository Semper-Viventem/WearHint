package com.example.konstantin.weartest.ui.hintitem

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.konstantin.weartest.*
import com.example.konstantin.weartest.extansions.visible
import com.example.konstantin.weartest.system.OutIntentsHelper
import com.example.konstantin.weartest.ui.UiModule
import com.example.konstantin.weartest.ui.common.Screen
import com.example.konstantin.weartest.ui.hintitem.adapter.HintItemsAdapter
import com.example.konstantin.weartest.ui.hintitem.dialog.AddTextDialog
import com.example.konstantin.weartest.ui.hintitem.dialog.ImageSourceDialog
import kotlinx.android.synthetic.main.screen_hint_item.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.standalone.StandAloneContext
import ru.semper_viventem.common.dto.Hint
import java.io.File


class HintItemScreen(args: Bundle) : Screen<HintItemPm>(args) {

    companion object {
        private const val KEY_HINT = "key_hint"
        private const val KEY_EDITABLE = "key_editable"

        fun newInstance(hint: Hint = Hint(), editable: Boolean = false) = HintItemScreen(
                Bundle().apply {
                    putParcelable(KEY_HINT, hint)
                    putBoolean(KEY_EDITABLE, editable)
                }
        )
    }

    override val screenLayout: Int = R.layout.screen_hint_item

    private val intentHelper = StandAloneContext.koinContext.get<OutIntentsHelper>()
    private var tempPhotoFile: File? = null
    private lateinit var imageSourceDialog: AlertDialog

    private val hintAdapter = HintItemsAdapter(
            onItemSelected = { posotion ->
                posotion passTo presentationModel.removeItem.consumer
            },
            onAddImage = {
                imageSourceDialog.show()
            },
            onAddTitle = {
                AddTextDialog.getInstance(context!!) { text ->
                    text passTo presentationModel.addNewTitle.consumer
                }.show()
            },
            onAddText = {
                AddTextDialog.getInstance(context!!) { text ->
                    text passTo presentationModel.addNewText.consumer
                }.show()
            }
    )

    override fun providePresentationModel(): HintItemPm = StandAloneContext.koinContext
        .apply {
            setProperty(UiModule.PROPERTY_HINT_ITEM, args.getParcelable(KEY_HINT))
            setProperty(UiModule.PROPERTY_HINT_ITEM_EDITABLE, args.getBoolean(KEY_EDITABLE))
        }
        .get()

    override fun onInitView(view: View, savedViewState: Bundle?) {
        super.onInitView(view, savedViewState)

        imageSourceDialog = ImageSourceDialog.getInstance(
                context = context!!,
                fromCamera = this::loadImageFromCamera,
                fromGallery = this::loadImageFromGallery
        )

        with(view.toolbar) {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { passTo(presentationModel.backAction.consumer) }
        }

        with(view.recyclerView) {
            adapter = hintAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                setDrawable(resources.getDrawable(R.drawable.decorator, null))
            })
        }
    }

    override fun onBindPresentationModel(view: View, pm: HintItemPm) {
        pm.items.observable bindTo { hintAdapter.items = it }
        pm.name bindTo view.nameEditText
        pm.toolbarTitle.observable bindTo { view.toolbar.title = it }

        pm.isEditMode.observable bindTo {
            hintAdapter.setEditMode(it)
            view.nameEditText.visible(it)
            if (it) {
                with(view.fab) {
                    setImageDrawable(resources!!.getDrawable(R.drawable.ic_done, null))
                    setOnClickListener { passTo(pm.saveHint.consumer) }
                }
            } else {
                with(view.fab) {
                    setImageDrawable(resources!!.getDrawable(R.drawable.ic_edit, null))
                    setOnClickListener { passTo(pm.setEditMode.consumer) }
                }
            }
        }
    }

    private fun loadImageFromGallery() {
        if (!checkStoragePermisson()) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_STORAGE)
            return
        }

        registerForActivityResult(REQUEST_GALERY)
        intentHelper.openGallery(REQUEST_GALERY)
        imageSourceDialog.dismiss()
    }

    private fun loadImageFromCamera() {
        if (!checkStoragePermisson()) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_STORAGE)
            return
        }
        if (!checkCameraPermission()) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_CAMERA)
            return
        }

        tempPhotoFile = createImageFile(context!!)
        registerForActivityResult(REQUEST_CAMERA)
        intentHelper.openCamera(REQUEST_CAMERA, tempPhotoFile!!)
        imageSourceDialog.dismiss()
    }

    private fun checkCameraPermission(): Boolean =
        ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    private fun checkStoragePermisson(): Boolean =
        ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(context!!, R.string.no_camera_permissions, Toast.LENGTH_LONG).show()
            }
            PERMISSION_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(context!!, R.string.not_storage_permission, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            REQUEST_GALERY ->
                if (data != null) presentationModel.addNewImage.consumer.accept(data.data)
            REQUEST_CAMERA ->
                if (tempPhotoFile != null) presentationModel.addNewImage.consumer.accept(Uri.parse(tempPhotoFile!!.absolutePath))
        }
    }
}