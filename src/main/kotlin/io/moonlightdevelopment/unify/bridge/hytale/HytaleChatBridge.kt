package io.moonlightdevelopment.unify.bridge.hytale

import com.hypixel.hytale.server.core.Message
import com.hypixel.hytale.server.core.universe.PlayerRef
import com.hypixel.hytale.server.core.universe.Universe
import io.moonlightdevelopment.unify.UnifyPlugin
import io.moonlightdevelopment.unify.bridge.BridgePayload
import io.moonlightdevelopment.unify.bridge.ChatBridge
import java.awt.Color

class HytaleChatBridge(val instance: UnifyPlugin) : ChatBridge {


    fun getEligibleRecipients(): List<PlayerRef> {
        return Universe.get().players
    }

    override fun relayMessage(bridgePayload: BridgePayload) {
        getEligibleRecipients().forEach {
            if (bridgePayload.raw != null) {
                it.sendMessage(Message.raw(bridgePayload.raw))
                return@forEach
            }
            if (bridgePayload.message.isNullOrEmpty()) {
                return@forEach
            }
            it.sendMessage(
                Message.raw("[").insert(bridgePayload.origin.styled).insert(Message.raw("] "))
                    .insert(Message.raw(bridgePayload.author).color(Color.RED)).insert(Message.raw(" » "))
                    .insert(Message.raw(bridgePayload.message))
            )
        }
    }

    override fun shutdown() {
        // No-op
    }
}