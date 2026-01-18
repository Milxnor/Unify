package io.moonlightdevelopment.unify

import com.hypixel.hytale.server.core.plugin.JavaPlugin
import com.hypixel.hytale.server.core.plugin.JavaPluginInit
import dev.triumphteam.polaris.Config
import dev.triumphteam.polaris.hocon.Hocon
import dev.triumphteam.polaris.hocon.HoconSettings
import dev.triumphteam.polaris.loadConfig
import io.moonlightdevelopment.unify.bridge.UnifyChatBridge
import io.moonlightdevelopment.unify.bridge.discord.DiscordChatBridge
import io.moonlightdevelopment.unify.bridge.hytale.HytaleChatBridge
import io.moonlightdevelopment.unify.jda.JDAManager
import io.moonlightdevelopment.unify.listeners.PlayerChatHandler
import io.moonlightdevelopment.unify.listeners.PlayerDeathHandler
import io.moonlightdevelopment.unify.listeners.PlayerParticipationHandler
import io.moonlightdevelopment.unify.storage.UnifyConfig

class UnifyPlugin(val init: JavaPluginInit) : JavaPlugin(init) {

    val config: Config<UnifyConfig> = loadConfig<UnifyConfig> {
        file = dataDirectory.resolve("config.yaml")
        defaultInstance { UnifyConfig() }
        format = Hocon {
            encodeDefaults = true
            commentStyle = HoconSettings.CommentStyle.HASH_TAG
        }
    }

    lateinit var chatBridge: UnifyChatBridge
        private set

    lateinit var jdaManager: JDAManager
        private set


    override fun start() {
        val hytaleChatBridge = HytaleChatBridge(this)
        val discordBridge = DiscordChatBridge(webhookUrl = { config.get().chatWebhookUrl })

        chatBridge = UnifyChatBridge(hytaleChatBridge, discordBridge)

        jdaManager = JDAManager(this, config, chatBridge)
        jdaManager.initJDA()

        PlayerDeathHandler(this, chatBridge)
        PlayerChatHandler(this, chatBridge)
        PlayerParticipationHandler(this, chatBridge)

        logger.atInfo().log("Unify Plugin starting up.")
    }

    override fun shutdown() {
        if (::jdaManager.isInitialized) jdaManager.shutdown()
        if (::chatBridge.isInitialized) chatBridge.shutdown()
        logger.atInfo().log("Unify Plugin shutting down.")
    }
}