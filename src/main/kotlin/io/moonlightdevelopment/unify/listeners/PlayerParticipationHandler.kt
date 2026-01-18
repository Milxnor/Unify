package io.moonlightdevelopment.unify.listeners

import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent
import io.moonlightdevelopment.unify.UnifyPlugin
import io.moonlightdevelopment.unify.bridge.BridgePayload
import io.moonlightdevelopment.unify.bridge.Origin
import io.moonlightdevelopment.unify.bridge.Target
import io.moonlightdevelopment.unify.bridge.UnifyChatBridge

class PlayerParticipationHandler(
    plugin: UnifyPlugin,
    private val chatBridge: UnifyChatBridge,
) {

    init {
        plugin.eventRegistry.registerGlobal(PlayerReadyEvent::class.java, ::onReady)
        plugin.eventRegistry.registerGlobal(PlayerDisconnectEvent::class.java, ::onDisconnect)
    }

    fun onReady(event: PlayerReadyEvent) {
        val player = event.player;

        chatBridge.sendTo(
            BridgePayload(
                author = "Server",
                message = player.displayName + " has joined!",
                origin = Origin.HYTALE
            ),
            Target.DISCORD
        )
    }

    fun onDisconnect(event: PlayerDisconnectEvent) {
        val player = event.playerRef;

        chatBridge.sendTo(
            BridgePayload(
                author = "Server",
                message = player.username + " has left!",
                origin = Origin.HYTALE
            ),
            Target.DISCORD
        )
    }
}