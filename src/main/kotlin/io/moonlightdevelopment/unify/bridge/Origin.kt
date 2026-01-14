package io.moonlightdevelopment.unify.bridge

import com.hypixel.hytale.server.core.Message
import kotlinx.serialization.Serializable
import java.awt.Color


@Serializable
enum class Origin(val styled: Message) {
    DISCORD(Message.raw("Discord").color(Color.BLUE)),
    HYTALE(Message.raw("Hytale").color(Color.GREEN)),
}