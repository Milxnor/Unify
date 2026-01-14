package io.moonlightdevelopment.unify.jda.listeners

import com.hypixel.hytale.server.core.universe.Universe
import io.moonlightdevelopment.unify.bridge.BridgePayload
import io.moonlightdevelopment.unify.bridge.Origin
import io.moonlightdevelopment.unify.bridge.Target
import io.moonlightdevelopment.unify.bridge.UnifyChatBridge
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageHandler(
    private val chatChannelId: () -> Long, private val chatBridge: UnifyChatBridge
) : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (event.channel.idLong != chatChannelId()) return
        if (event.author.isBot) return

        val message = event.message
        val author = event.author

        message.addReaction(Emoji.fromFormatted("✅"))

        if (message.contentStripped == $$"$online") {
            val embed = EmbedBuilder()
            embed.setTitle("The following players are online:")
            val players = Universe.get().players
            if (players.isEmpty()) {
                embed.setDescription("No players are currently online.")
            } else {
                val playerNames = players.joinToString(", ") { it.username }
                embed.setDescription(playerNames)
            }
            message.replyEmbeds(embed.build()).queue()
        } else {
            chatBridge.sendTo(
                BridgePayload(
                    author = author.effectiveName, message = message.contentStripped, origin = Origin.DISCORD
                ), Target.HYTALE
            )
        }
    }
}