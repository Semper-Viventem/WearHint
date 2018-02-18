package com.example.konstantin.weartest.domain

import ru.semper_viventem.common.HintGateway


class GetHintsInteractor(
    private val hintGateway: HintGateway
) {

    fun execute() = hintGateway.getAllHints()
}