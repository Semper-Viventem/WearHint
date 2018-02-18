package com.example.konstantin.weartest.domain

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.semper_viventem.common.HintGateway


class GetHintsInteractor(
    private val hintGateway: HintGateway
) {

    fun execute() = hintGateway.getAllHints()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}