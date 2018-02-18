package com.example.konstantin.weartest.domain

import org.koin.android.module.AndroidModule
import org.koin.dsl.context.Context


class DomainModule : AndroidModule() {
    override fun context(): Context = applicationContext {
        provide { GetHintsInteractor(get()) }
    }
}