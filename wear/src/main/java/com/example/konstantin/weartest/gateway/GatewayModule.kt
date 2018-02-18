package com.example.konstantin.weartest.gateway

import org.koin.android.module.AndroidModule
import org.koin.dsl.context.Context
import ru.semper_viventem.common.HintGateway


class GatewayModule : AndroidModule() {
    override fun context(): Context = applicationContext {
        provide { HintGateway(get(), get()) }
    }
}