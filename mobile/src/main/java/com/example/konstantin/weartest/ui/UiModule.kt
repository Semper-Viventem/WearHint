package com.example.konstantin.weartest.ui

import com.example.konstantin.weartest.ui.hintitem.HintItemPm
import com.example.konstantin.weartest.ui.hintlist.HintListPm
import com.example.konstantin.weartest.ui.main.MainPm
import org.koin.android.module.AndroidModule
import org.koin.dsl.context.Context


class UiModule : AndroidModule() {

    companion object {
        const val PROPERTY_HINT_ITEM = "hint_item_property"
        const val PROPERTY_HINT_ITEM_EDITABLE = "hint_item_editable"
    }

    override fun context(): Context = applicationContext {
        provideFactory { MainPm() }
        provideFactory { HintListPm(get()) }
        provideFactory { HintItemPm(getProperty(PROPERTY_HINT_ITEM), getProperty(PROPERTY_HINT_ITEM_EDITABLE), get()) }
    }
}