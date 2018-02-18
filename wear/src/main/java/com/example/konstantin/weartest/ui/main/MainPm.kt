package com.example.konstantin.weartest.ui.main

import com.example.konstantin.weartest.domain.GetHintsInteractor
import me.dmdev.rxpm.PresentationModel
import me.dmdev.rxpm.bindProgress
import ru.semper_viventem.common.dto.Hint


class MainPm(
    private val getHintsInteractor: GetHintsInteractor
) : PresentationModel() {

    val hints = State<List<Hint>>(emptyList())
    val progress = State(false)

    override fun onBind() {
        super.onBind()

        getHintsInteractor.execute()
            .bindProgress(progress.consumer)
            .subscribe(hints.consumer)
            .untilDestroy()
    }
}