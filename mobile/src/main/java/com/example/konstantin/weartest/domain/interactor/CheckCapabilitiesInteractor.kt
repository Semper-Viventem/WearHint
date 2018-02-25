package com.example.konstantin.weartest.domain.interactor

import com.example.konstantin.weartest.domain.gateway.CapabilitiesGateway

/**
 * @author Kulikov Konstantin
 * @since 25.02.2018.
 */
class CheckCapabilitiesInteractor(
    private val capabilitiesGateway: CapabilitiesGateway
) {

    fun execute() = capabilitiesGateway.checkCapabilities()
}