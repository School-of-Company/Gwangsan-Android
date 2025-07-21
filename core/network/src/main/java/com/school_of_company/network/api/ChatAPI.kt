package com.school_of_company.network.api

import com.school_of_company.network.dto.chat.request.ReadMessageRequest
import com.school_of_company.network.dto.chat.response.GetChatMessageResponse
import com.school_of_company.network.dto.chat.response.GetChatRoomResponse
import com.school_of_company.network.dto.chat.response.JoinChatResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ChatAPI {

    @POST("/api/chat/room/{product_id}")
    suspend fun createChatRoom(
        @Path("product_id") productId: Long
    ) : JoinChatResponse

    @GET("/api/chat/room/{product_id}")
    suspend fun joinChatRoom(
        @Path("product_id") productId: Long
    ) : JoinChatResponse

    @GET("/api/chat/rooms")
    suspend fun getChatRoomList() : List<GetChatRoomResponse>

    @GET("/api/chat/{room_id}")
    suspend fun getChatMessageList(
        @Path("roomId") roomId: Long,
        @Query("lastCreatedAt") lastCreatedAt: String? = null,
        @Query("lastMessageId") lastMessageId: Long? = null,
        @Query("limit") limit: Int = 20
    ) : List<GetChatMessageResponse>

    @PATCH("/api/chat/read")
    suspend fun readChatMessage(
        @Body body: ReadMessageRequest
    )
}