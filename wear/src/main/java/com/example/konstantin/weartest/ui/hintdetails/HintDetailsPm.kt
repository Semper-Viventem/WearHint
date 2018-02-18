package com.example.konstantin.weartest.ui.hintdetails

import me.dmdev.rxpm.PresentationModel
import ru.semper_viventem.common.dto.BaseHintItem
import ru.semper_viventem.common.dto.Hint

/**
 * @author Kulikov Konstantin
 * @since 21.01.2018.
 */
class HintDetailsPm(
    hint: Hint
): PresentationModel() {

    val hintItems = State<List<BaseHintItem>>(hint.items)
    val title = State(hint.title)

    override fun onCreate() {
        super.onCreate()

    }
}