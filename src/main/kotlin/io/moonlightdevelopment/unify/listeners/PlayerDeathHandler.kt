package io.moonlightdevelopment.unify.listeners

import com.hypixel.hytale.component.Archetype
import com.hypixel.hytale.component.CommandBuffer
import com.hypixel.hytale.component.Ref
import com.hypixel.hytale.component.Store
import com.hypixel.hytale.component.query.Query
import com.hypixel.hytale.server.core.entity.entities.Player
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent
import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore
import io.moonlightdevelopment.unify.UnifyPlugin
import io.moonlightdevelopment.unify.bridge.BridgePayload
import io.moonlightdevelopment.unify.bridge.Origin
import io.moonlightdevelopment.unify.bridge.Target
import io.moonlightdevelopment.unify.bridge.UnifyChatBridge

class PlayerDeathHandler(
    plugin: UnifyPlugin,
    private val chatBridge: UnifyChatBridge,
) : DeathSystems.OnDeathSystem() {

    init {
        plugin.entityStoreRegistry.registerSystem(this)
    }


    override fun onComponentAdded(
        ref: Ref<EntityStore>,
        component: DeathComponent,
        store: Store<EntityStore>,
        commandBuffer: CommandBuffer<EntityStore>
    ) {
        val player =
            store.getComponent(ref, Player.getComponentType()) ?: // Not a player or component missing
            return

        val ansi = component.deathMessage?.ansiMessage ?: return

        chatBridge.sendTo(
            BridgePayload(
                author = player.displayName + " (Server)",
                raw = ansi,
                origin = Origin.HYTALE
            ),
            Target.DISCORD
        )
    }

    override fun getQuery(): Query<EntityStore?>? {
        return Archetype.empty()
    }
}