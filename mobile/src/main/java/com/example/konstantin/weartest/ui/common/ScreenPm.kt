package com.example.konstantin.weartest.ui.common

import com.example.konstantin.weartest.ui.Back
import me.dmdev.rxpm.PresentationModel
import me.dmdev.rxpm.navigation.NavigationMessage


abstract class ScreenPm : PresentationModel() {

    private val defaultBackAction = Action<Unit>()

    open val backAction = defaultBackAction

    override fun onCreate() {
        super.onCreate()

        defaultBackAction.observable
            .subscribe { sendNavigationMessage(Back()) }
            .untilDestroy()
    }

    protected fun sendNavigationMessage(message: NavigationMessage) {
        navigationMessages.consumer.accept(message)
    }
}