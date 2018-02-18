package com.example.konstantin.weartest.domain

import org.koin.android.module.AndroidModule
import org.koin.dsl.context.Context


class InteractorModule : AndroidModule() {
    override fun context(): Context = applicationContext {
        provide { GetHintsInteractor(get()) }
        provide { SaveHintInteractor(get()) }
        provide { RemoveHintInteractor(get()) }
    }

}