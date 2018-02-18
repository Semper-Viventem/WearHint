package com.example.konstantin.weartest.ui.hintlist

import com.example.konstantin.weartest.domain.GetHintsInteractor
import com.example.konstantin.weartest.ui.OpenAddHintScreen
import com.example.konstantin.weartest.ui.OpenHintItem
import com.example.konstantin.weartest.ui.common.ScreenPm
import me.dmdev.rxpm.bindProgress
import me.dmdev.rxpm.widget.clickControl
import ru.semper_viventem.common.dto.Hint
import timber.log.Timber


class HintListPm(
    private val getHintsInteractor: GetHintsInteractor
) : ScreenPm() {

    val progress = State<Boolean>(false)
    val error = Command<Unit>()
    val hintSelected = Action<Hint>()
    val hints = State<List<Hint>>(emptyList())
    val addHintSelected = clickControl()

    override fun onBind() {
        super.onBind()

        getHintsInteractor.execute()
            .bindProgress(progress.consumer)
            .subscribe(
                    {
                        hints.consumer.accept(it)
                    }, { e ->
                        Timber.e(e)
                        error.consumer.accept(Unit)
                    }
            )
            .untilDestroy()
    }

    override fun onCreate() {
        super.onCreate()

        hintSelected.observable
            .subscribe { sendNavigationMessage(OpenHintItem(it)) }
            .untilDestroy()

        addHintSelected.clicks.observable
            .subscribe { sendNavigationMessage(OpenAddHintScreen()) }
            .untilDestroy()
    }
}