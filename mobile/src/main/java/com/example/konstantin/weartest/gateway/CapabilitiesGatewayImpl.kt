package com.example.konstantin.weartest.gateway

import com.example.konstantin.weartest.domain.gateway.CapabilitiesGateway
import com.google.android.gms.wearable.CapabilityClient
import io.reactivex.Single
import ru.semper_viventem.common.toSingle

/**
 * @author Kulikov Konstantin
 * @since 25.02.2018.
 */
class CapabilitiesGatewayImpl(
    private val capabilityClient: CapabilityClient
) : CapabilitiesGateway {

    companion object {
        private const val CAPABILITY_HINT = "capability_hint"
    }

    override fun checkCapabilities(): Single<Boolean> =
        capabilityClient
            .getCapability(CAPABILITY_HINT, CapabilityClient.FILTER_REACHABLE)
            .toSingle()
            .map { it.nodes.isNotEmpty() }
}