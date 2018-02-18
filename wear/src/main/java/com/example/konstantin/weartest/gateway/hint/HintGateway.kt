package com.example.konstantin.weartest.gateway.hint

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.konstantin.weartest.domain.dto.BaseHintItem
import com.example.konstantin.weartest.domain.dto.Hint
import com.example.konstantin.weartest.gateway.DataItemNotFound
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File


class HintGateway(
    private val dataClient: DataClient,
    private val context: Context
) {

    companion object {
        private val HINT_LIST_KEY = "ru.semper-viventem.wear.key.hints"
        private val HINT_LIST_PATH = "/hints/6"
    }

    fun getAllHints() = getDataItem()
        .flatMap { dataItem ->
            Single.create<List<Hint>> { emitter ->
                val dataMap = DataMapItem.fromDataItem(dataItem)
                val wearHints = dataMap!!.dataMap.getStringArrayList(HINT_LIST_KEY).mapTo(mutableListOf()) { Hint.fromJson(it) }
                wearHints.forEach { hint ->
                    hint.items.forEach { item ->
                        if (item is BaseHintItem.ImageHintItem) {
                            val asset = dataMap.dataMap.getAsset(item.uri)

                            item.uri = saveFileFromAsset(asset, item.uri)
                        }
                    }
                }
                emitter.onSuccess(wearHints)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    private fun getDataItem() = Single.create<DataItem> { emitter ->
        dataClient.dataItems.addOnSuccessListener {
            val dataItem = it.find { it.uri.path == HINT_LIST_PATH }

            if (dataItem != null) {
                emitter.onSuccess(dataItem)
            } else {
                dataClient.putDataItem(PutDataMapRequest.create(HINT_LIST_PATH).apply {
                    dataMap.putStringArrayList(HINT_LIST_KEY, arrayListOf())
                }.asPutDataRequest())
                    .addOnCompleteListener {
                        emitter.onSuccess(it.result)
                    }
                    .addOnFailureListener {
                        emitter.onError(DataItemNotFound())
                    }
            }
        }
    }

    private fun saveFileFromAsset(asset: Asset, name: String): String {

        val imageFile = File(context.filesDir, name)

        if (!imageFile.exists()) {
            val inputStream = Tasks.await(dataClient.getFdForAsset(asset)).inputStream
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageFile.outputStream())
        }

        return imageFile.absolutePath
    }
}