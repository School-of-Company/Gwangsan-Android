package com.school_of_company.network.dto.webhook.message

data class DiscordMessage(
    val username: String = "앱 크래시 봇..",
    val embeds: List<DiscordEmbed>
)

data class DiscordEmbed(
    val title: String,
    val description: String,
    val color: Int,
    val fields: List<DiscordField>,
    val timestamp: String,
    val footer: DiscordFooter
)

data class DiscordField(
    val name: String,
    val value: String,
    val inline: Boolean = false
)

data class DiscordFooter(
    val text: String
)
