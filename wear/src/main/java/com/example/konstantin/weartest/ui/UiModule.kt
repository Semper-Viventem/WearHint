package com.example.konstantin.weartest.ui

import com.example.konstantin.weartest.ui.hintdetails.HintDetailsPm
import com.example.konstantin.weartest.ui.main.MainPm
import org.koin.android.module.AndroidModule
import org.koin.dsl.context.Context


class UiModule : AndroidModule() {

    companion object {
        const val HINT_PROPERTY = "hint_property"
    }

    override fun context(): Context = applicationContext {
        provideFactory { MainPm(get()) }
        provideFactory { HintDetailsPm(getProperty(HINT_PROPERTY)) }
    }
}