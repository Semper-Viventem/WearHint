package com.example.konstantin.weartest.ui.main

import com.example.konstantin.weartest.domain.interactor.CheckCapabilitiesInteractor
import com.example.konstantin.weartest.ui.OpenCapabilitiesInformationScreen
import com.example.konstantin.weartest.ui.OpenHintList
import com.example.konstantin.weartest.ui.common.ScreenPm
import timber.log.Timber


class MainPm(
    private val checkCapabilitiesInteractor: CheckCapabilitiesInteractor
) : ScreenPm() {

    override fun onBind() {
        super.onBind()

        checkCapabilitiesInteractor.execute()
            .subscribe({ nodeExist ->
                           if (nodeExist)
                               sendNavigationMessage(OpenHintList())
                           else
                               sendNavigationMessage(OpenCapabilitiesInformationScreen())
                       },
                       { error ->
                           Timber.e(error)
                       })
            .untilDestroy()
    }
}