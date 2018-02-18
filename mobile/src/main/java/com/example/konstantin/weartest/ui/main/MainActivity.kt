package com.example.konstantin.weartest.ui.main

import android.os.Bundle
import com.bluelinelabs.conductor.Controller
import com.example.konstantin.weartest.R
import com.example.konstantin.weartest.extansions.back
import com.example.konstantin.weartest.extansions.goTo
import com.example.konstantin.weartest.system.OutIntentsHelper
import com.example.konstantin.weartest.ui.Back
import com.example.konstantin.weartest.ui.OpenAddHintScreen
import com.example.konstantin.weartest.ui.OpenHintItem
import com.example.konstantin.weartest.ui.common.BasePmActivity
import com.example.konstantin.weartest.ui.hintitem.HintItemScreen
import com.example.konstantin.weartest.ui.hintlist.HintListScreen
import me.dmdev.rxpm.navigation.NavigationMessage
import me.dmdev.rxpm.navigation.NavigationMessageHandler
import org.koin.standalone.StandAloneContext

class MainActivity : BasePmActivity<MainPm>(), NavigationMessageHandler {

    override val activityLayout: Int = R.layout.activity_main

    override val containerId: Int = R.id.container

    override val rootScreen: Controller? = HintListScreen()

    override fun providePresentationModel(): MainPm = StandAloneContext.koinContext.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StandAloneContext.koinContext.get<OutIntentsHelper>().attach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        StandAloneContext.koinContext.get<OutIntentsHelper>().dettach()
    }

    override fun onBindPresentationModel(pm: MainPm) {

    }

    override fun handleNavigationMessage(message: NavigationMessage): Boolean {
        when(message) {
            is Back -> if (router.back()) else finish()
            is OpenHintItem -> router.goTo(HintItemScreen.newInstance(message.hint))
            is OpenAddHintScreen -> router.goTo(HintItemScreen.newInstance(editable = true))
        }
        return true
    }

}
