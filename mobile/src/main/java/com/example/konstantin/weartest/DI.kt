package com.example.konstantin.weartest

import com.example.konstantin.weartest.domain.InteractorModule
import com.example.konstantin.weartest.gateway.GatewayModule
import com.example.konstantin.weartest.ui.UiModule


fun allModules() = listOf(
        AppModule(),
        UiModule(),
        InteractorModule(),
        GatewayModule()
)