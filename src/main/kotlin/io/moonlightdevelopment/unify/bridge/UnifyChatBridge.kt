package io.moonlightdevelopment.unify.bridge

import io.moonlightdevelopment.unify.bridge.discord.DiscordChatBridge
import io.moonlightdevelopment.unify.bridge.hytale.HytaleChatBridge

class UnifyChatBridge(
    private val hytaleChatBridge: HytaleChatBridge,
    private val discord: DiscordChatBridge,
) {

    fun sendTo(bridgePayload: BridgePayload, vararg targets: Target) {
        for (target in targets) {
            when (target) {
                Target.HYTALE -> hytaleChatBridge.relayMessage(bridgePayload)
                Target.DISCORD -> discord.relayMessage(bridgePayload)
            }
        }
    }

    fun shutdown() {
        hytaleChatBridge.shutdown()
        discord.shutdown()
    }
}