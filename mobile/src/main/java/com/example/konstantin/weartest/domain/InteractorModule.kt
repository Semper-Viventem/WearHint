package com.example.konstantin.weartest.domain

import com.example.konstantin.weartest.domain.interactor.CheckCapabilitiesInteractor
import com.example.konstantin.weartest.domain.interactor.GetHintsInteractor
import com.example.konstantin.weartest.domain.interactor.RemoveHintInteractor
import com.example.konstantin.weartest.domain.interactor.SaveHintInteractor
import org.koin.android.module.AndroidModule
import org.koin.dsl.context.Context


class InteractorModule : AndroidModule() {
    override fun context(): Context = applicationContext {
        provide { GetHintsInteractor(get()) }
        provide { SaveHintInteractor(get()) }
        provide { RemoveHintInteractor(get()) }
        provide { CheckCapabilitiesInteractor(get()) }
    }

}