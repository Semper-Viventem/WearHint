package ru.semper_viventem.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.semper_viventem.common.dto.BaseHintItem
import ru.semper_viventem.common.dto.Hint
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

    fun saveHint(hint: Hint) = getDataItem()
        .flatMap { dataItem ->

            val dataMapItem = DataMapItem.fromDataItem(dataItem)
            val wearHintsSet: MutableSet<Hint> = dataMapItem
                .dataMap.getStringArrayList(HINT_LIST_KEY).mapTo(mutableSetOf()) { Hint.fromJson(it) }

            hint.items.forEach {
                if (it is BaseHintItem.ImageHintItem) {
                    val name = File(it.uri).name
                    if (dataMapItem.dataMap.getAsset(name) == null)
                        dataMapItem.dataMap.putAsset(name, Asset.createFromUri(Uri.parse(it.uri)))
                    it.uri = name
                }
            }

            if (wearHintsSet.find { it.id == hint.id } != null) {
                wearHintsSet.removeAll { it.id == hint.id }
                wearHintsSet.add(hint)
            } else {
                hint.id = (wearHintsSet.maxBy { it.id }?.id ?: 0) + 1
                wearHintsSet.add(hint)
            }

            dataMapItem.dataMap.putStringArrayList(HINT_LIST_KEY, wearHintsSet.mapTo(arrayListOf()) { it.toJson() })

            dataClient.putDataItem(PutDataMapRequest.createFromDataMapItem(dataMapItem).asPutDataRequest())
                .completeToSingle()
        }

    fun removeHint(hint: Hint) = getDataItem()
        .flatMap { dataItem ->
            val dataMapItem = DataMapItem.fromDataItem(dataItem)
            val wearHints = dataMapItem.dataMap.getStringArrayList(HINT_LIST_KEY).mapTo(mutableSetOf()) { Hint.fromJson(it) }

            wearHints.remove(hint)

            dataMapItem.dataMap.putStringArrayList(HINT_LIST_KEY, wearHints.mapTo(arrayListOf()) { it.toJson() })

            dataClient.putDataItem(PutDataMapRequest.createFromDataMapItem(dataMapItem).asPutDataRequest())
                .completeToSingle()
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