package io.moonlightdevelopment.unify.jda

import com.hypixel.hytale.server.core.HytaleServer
import dev.triumphteam.polaris.Config
import io.moonlightdevelopment.unify.UnifyPlugin
import io.moonlightdevelopment.unify.bridge.UnifyChatBridge
import io.moonlightdevelopment.unify.jda.listeners.MessageHandler
import io.moonlightdevelopment.unify.storage.UnifyConfig
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy

class JDAManager(
    private val plugin: UnifyPlugin, private val config: Config<UnifyConfig>, private val chatBridge: UnifyChatBridge
) {

    private lateinit var jda: JDA

    fun initJDA() {
        plugin.logger.atInfo().log("Initializing JDA")

        val cfg = config.get()

        jda = JDABuilder.createLight(
            cfg.token,
            GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.MESSAGE_CONTENT,
        ).setMemberCachePolicy(MemberCachePolicy.ALL).build().awaitReady()

        plugin.logger.atWarning().log("Invite Link: ${jda.getInviteUrl(Permission.MESSAGE_HISTORY)}")

        val guild = jda.getGuildById(cfg.guildId)
        if (guild == null) {
            plugin.logger.atSevere().log("Guild not found. Please check your configuration and restart the server.")
            HytaleServer.get().pluginManager.unload(plugin.identifier)
            return
        } else {
            plugin.logger.atInfo().log("Guild found: ${guild.name} (${guild.id})")
        }

        val channel = guild.getTextChannelById(cfg.chatChannelId)
        if (channel == null) {
            plugin.logger.atSevere().log("Channel not found. Please check your configuration and restart the server.")
            HytaleServer.get().pluginManager.unload(plugin.identifier)
            return
        } else {
            plugin.logger.atInfo().log("Channel found: ${channel.name} (${channel.id})")
        }

        plugin.logger.atWarning().log("Checking webhook")
        if (cfg.chatWebhookUrl == "YOUR_WEBHOOK_URL_HERE") {
            channel.createWebhook("Unify Chat Webhook").queue { webhook ->
                plugin.logger.atFine().log("Webhook created: ${webhook.url}")
                config.save { it.copy(chatWebhookUrl = webhook.url) }
            }
        }
        plugin.logger.atInfo().log("Webhook ready")

        jda.addEventListener(
            MessageHandler(
                chatChannelId = { config.get().chatChannelId }, chatBridge = chatBridge
            )
        )
    }

    fun shutdown() {
        if (!::jda.isInitialized) return
        plugin.logger.atInfo().log("Shutting down JDA")
        jda.shutdown()
    }
}