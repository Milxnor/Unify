package io.moonlightdevelopment.unify.bridge.discord

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import io.moonlightdevelopment.unify.bridge.BridgePayload

object Webhook {

    fun generateMessage(bridgePayload: BridgePayload): JsonObject {
        return JsonObject(mapOf(
            "username" to JsonPrimitive(bridgePayload.author),
            "content" to JsonPrimitive(bridgePayload.message ?: bridgePayload.raw ?: ""),
            "avatar_url" to JsonPrimitive(bridgePayload.avatar ?: "")
        ))
    }
}