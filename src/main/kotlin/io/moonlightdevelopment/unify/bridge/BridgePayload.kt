package io.moonlightdevelopment.unify.bridge

import kotlinx.serialization.Serializable

@Serializable
data class BridgePayload(
    val author: String,
    val message: String? = null,
    val origin: Origin,
    val raw: String? = null,
    val avatar: String? = null
)
