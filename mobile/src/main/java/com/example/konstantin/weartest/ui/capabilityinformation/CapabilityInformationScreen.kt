package com.example.konstantin.weartest.ui.capabilityinformation

import android.os.Bundle
import android.view.View
import com.example.konstantin.weartest.R
import com.example.konstantin.weartest.extensions.visible
import com.example.konstantin.weartest.ui.common.Screen
import com.jakewharton.rxbinding2.view.clicks
import kotlinx.android.synthetic.main.screen_capability_information.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.standalone.StandAloneContext

/**
 * @author Kulikov Konstantin
 * @since 25.02.2018.
 */
class CapabilityInformationScreen : Screen<CapabilityInformationPm>() {

    override val screenLayout: Int = R.layout.screen_capability_information

    override fun providePresentationModel(): CapabilityInformationPm =
        StandAloneContext.koinContext.get()

    override fun onInitView(view: View, savedViewState: Bundle?) {
        super.onInitView(view, savedViewState)

        view.toolbar.setTitle(R.string.app_name)
    }

    override fun onBindPresentationModel(view: View, pm: CapabilityInformationPm) {
        pm.progress.observable bindTo { progress ->
            view.progress.visible(progress)
            view.updateButton.visible(!progress)
        }
        view.updateButton.clicks() bindTo pm.updateClicks.consumer
    }
}