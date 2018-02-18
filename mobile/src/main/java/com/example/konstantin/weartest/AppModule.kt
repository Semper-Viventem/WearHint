package com.example.konstantin.weartest

import com.example.konstantin.weartest.system.OutIntentsHelper
import com.google.android.gms.wearable.Wearable
import org.koin.android.module.AndroidModule
import org.koin.dsl.context.Context


class AppModule : AndroidModule() {
    override fun context(): Context = applicationContext {
        provide { Wearable.getDataClient(get<android.content.Context>()) }
        provide { OutIntentsHelper() }
    }
}