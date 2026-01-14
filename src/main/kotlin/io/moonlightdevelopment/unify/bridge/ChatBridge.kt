package io.moonlightdevelopment.unify.bridge

interface ChatBridge {

    fun relayMessage(bridgePayload: BridgePayload)

    fun shutdown()
}