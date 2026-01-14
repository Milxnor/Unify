package io.moonlightdevelopment.unify.listeners

import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent
import io.moonlightdevelopment.unify.UnifyPlugin
import io.moonlightdevelopment.unify.bridge.BridgePayload
import io.moonlightdevelopment.unify.bridge.Origin
import io.moonlightdevelopment.unify.bridge.Target
import io.moonlightdevelopment.unify.bridge.UnifyChatBridge

class PlayerChatHandler(
    plugin: UnifyPlugin,
    private val chatBridge: UnifyChatBridge,
) {

    init {
        plugin.eventRegistry.registerGlobal(PlayerChatEvent::class.java, ::onChat)
    }

    fun onChat(event: PlayerChatEvent) {
        val player = event.sender

        chatBridge.sendTo(
            BridgePayload(
                author = player.username,
                message = event.content,
                origin = Origin.HYTALE
            ),
            Target.DISCORD
        )
    }
}