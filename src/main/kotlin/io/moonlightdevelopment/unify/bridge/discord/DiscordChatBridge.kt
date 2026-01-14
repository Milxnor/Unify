package io.moonlightdevelopment.unify.bridge.discord

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import io.moonlightdevelopment.unify.bridge.BridgePayload
import io.moonlightdevelopment.unify.bridge.ChatBridge

class DiscordChatBridge(
    private val webhookUrl: () -> String
) : ChatBridge {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    override fun relayMessage(bridgePayload: BridgePayload) {
        val url = webhookUrl()
        if (url.isBlank() || url == "YOUR_WEBHOOK_URL_HERE") return

        scope.launch {
            client.post(url) {
                setBody(Webhook.generateMessage(bridgePayload))
                contentType(ContentType.Application.Json)
            }
        }
    }

    override fun shutdown() {
        client.close()
    }
}