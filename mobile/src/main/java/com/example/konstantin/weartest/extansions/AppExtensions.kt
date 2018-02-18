package com.example.konstantin.weartest.extansions

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


inline fun SharedPreferences.edit(changes: SharedPreferences.Editor.() -> SharedPreferences.Editor) {
    edit().changes().apply()
}

 inline fun <reified T: Any> Gson.fromJsonToGeneric(json: String): T {
    val type = object : TypeToken<T>() {}.type
    return fromJson(json, type)
}