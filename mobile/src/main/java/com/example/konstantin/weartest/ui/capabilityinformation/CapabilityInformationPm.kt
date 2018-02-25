package com.example.konstantin.weartest.ui.capabilityinformation

import com.example.konstantin.weartest.domain.interactor.CheckCapabilitiesInteractor
import com.example.konstantin.weartest.ui.OpenHintList
import com.example.konstantin.weartest.ui.common.ScreenPm
import me.dmdev.rxpm.bindProgress
import timber.log.Timber

/**
 * @author Kulikov Konstantin
 * @since 25.02.2018.
 */
class CapabilityInformationPm(
    private val checkCapabilitiesInteractor: CheckCapabilitiesInteractor
) : ScreenPm() {

    val progress = State(false)
    val updateClicks = Action<Unit>()

    override fun onCreate() {
        super.onCreate()

        updateClicks.observable
            .subscribe { checkCapabilities() }
            .untilDestroy()

        checkCapabilities()
    }

    private fun checkCapabilities() {
        checkCapabilitiesInteractor.execute()
            .bindProgress(progress.consumer)
            .subscribe({ nodeExist ->
                           if (nodeExist)
                               sendNavigationMessage(OpenHintList())
                       }, {
                           Timber.e(it)
                       })
            .untilDestroy()
    }
}