package com.example.konstantin.weartest.domain.gateway

import io.reactivex.Single

/**
 * @author Kulikov Konstantin
 * @since 25.02.2018.
 */
interface CapabilitiesGateway {

    fun checkCapabilities(): Single<Boolean>
}