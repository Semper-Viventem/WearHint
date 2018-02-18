package ru.semper_viventem.common.dto

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.json.JSONObject
import java.util.*

@Parcelize
@SuppressLint("ParcelCreator")
data class Hint(
    @SerializedName(KEY_ID) var id: Long = 0,
    @SerializedName(KEY_TITLE) var title: String = "",
    @SerializedName(KEY_ITEMS) var items: MutableList<BaseHintItem> = mutableListOf()
) : Parcelable {

    companion object {

        private const val KEY_ID = "id"
        private const val KEY_TITLE = "title"
        private const val KEY_ITEMS = "items"

        fun fromJson(json: String) = JSONObject(json).let { jsonObject ->
            Hint(
                    jsonObject.getLong(KEY_ID),
                    jsonObject.getString(KEY_TITLE),
                    ArrayList<BaseHintItem>().apply {
                        val jsonArray = jsonObject.getJSONArray(KEY_ITEMS)
                        for (index in 0 until jsonArray.length()) {
                            add(BaseHintItem.fromJson(jsonArray.getJSONObject(index)))
                        }
                    }
            )
        }
    }

    fun toJson() = Gson().toJson(this)
}