package com.example.konstantin.weartest.domain

import com.example.konstantin.weartest.domain.dto.Hint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.semper_viventem.common.HintGateway

/**
 * @author Kulikov Konstantin
 * @since 14.01.2018.
 */
class RemoveHintInteractor(
    private val hintGateway: HintGateway
) {

    fun execute(hint: Hint) = hintGateway.removeHint(hint)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}