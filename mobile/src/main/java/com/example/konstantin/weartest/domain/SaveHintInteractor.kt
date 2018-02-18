package com.example.konstantin.weartest.domain

import com.example.konstantin.weartest.domain.dto.Hint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.semper_viventem.common.HintGateway


class SaveHintInteractor(
    private val hintGateway: HintGateway
) {

    fun execute(hint: Hint) = hintGateway.saveHint(hint)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}