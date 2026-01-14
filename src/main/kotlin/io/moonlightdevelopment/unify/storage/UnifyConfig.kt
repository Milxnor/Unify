package io.moonlightdevelopment.unify.storage

import dev.triumphteam.polaris.annotation.SerialComment
import kotlinx.serialization.Serializable

@Serializable
data class UnifyConfig(
    @SerialComment(["Your Discord bot token."])
    val token: String = "YOUR_BOT_TOKEN_HERE",

    @SerialComment(["Your Discord guild (server) ID."])
    val guildId: Long = 123456789012345678L,

    @SerialComment(["The Discord Channel ID for the Hytale chat integration."])
    val chatChannelId: Long = 123456789012345678L,

    @SerialComment(["The Discord Channel Webhook URL"])
    val chatWebhookUrl: String = "YOUR_WEBHOOK_URL_HERE"
)
