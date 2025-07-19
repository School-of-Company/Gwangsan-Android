package com.school_of_company.network.api

import com.school_of_company.network.dto.webhook.message.DiscordMessage
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface WebhookAPI {

    @POST
    suspend fun sendDiscordMessage(
        @Url url: String,
        @Body body: DiscordMessage
    )
}