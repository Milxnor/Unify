# Unify

Unify is a Discord chat bridge for Hytale servers.  
It links in-game chat and a Discord channel so players and community members can communicate naturally across platforms.

Simple, lightweight, and focused on doing one thing well.

---

## Features

- Bidirectional chat between Hytale and Discord
- Player chat messages synced in real time
- Death messages forwarded to Discord
- Automatic webhook creation and management
- Clean Discord output using webhooks instead of bot spam

---

## Requirements

You will need:

- A Discord bot application
- A bot token
- The bot added to your Discord server
- Permission to manage webhooks in the target channel

### Required Discord Permissions

Make sure the bot has:

- View Channels  
- Send Messages  
- Manage Webhooks  
- Read Message History  

Without webhook permissions, Unify cannot function.

---

## Installation

1. Place the Unify plugin into your server’s plugins folder
2. Start the server once to generate the config file
3. Stop the server
4. Edit the config
5. Start the server again

---

## Configuration

Unify generates a config file that must be filled out.  
Everything is required except the webhook URL. That part is handled automatically.

```kotlin
token = "YOUR_BOT_TOKEN_HERE"
guildId = 123456789012345678
chatChannelId = 123456789012345678
chatWebhookUrl = "AUTO_GENERATED"
```

### Config Options

#### `token`
Your Discord bot token.  
Keep this private. If it leaks, regenerate it immediately.

#### `guildId`
The ID of the Discord server Unify should connect to.

#### `chatChannelId`
The Discord channel where chat and death messages will appear.

#### `chatWebhookUrl`
Do not modify this value.  
Unify creates and maintains the webhook automatically.

---

## What Gets Synced

From Hytale to Discord:
- Player chat messages
- Death messages

From Discord to Hytale:
- Regular text messages sent in the configured channel

---

## Troubleshooting

**Nothing appears in Discord**
- Verify the bot is online
- Check guild and channel IDs
- Confirm webhook permissions

**Webhook errors**
- Ensure the bot can manage webhooks
- Try deleting the webhook manually and restarting the server

**Duplicate messages**
- Make sure only one Unify instance is running
- Remove other chat bridge plugins

---

## Notes

Unify uses Discord webhooks to keep chat readable and avoid cluttering the channel with bot messages.  
Logs usually explain issues clearly if something goes wrong.

---

Enjoy unified chat.
