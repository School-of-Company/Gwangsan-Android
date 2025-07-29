package com.school_of_company.chat.ui.model

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.PersistentList

/**
 * Compose recomposition 최적화를 위해 @Stable 어노테이션 명시.
 * 외부 PersistentList 타입(images)을 property로 가질 때,
 * Compose 컴파일러가 자동으로 안정성을 판단하지 못하므로 명시적으로 선언함.
 *
 * @Stable: PersistentList로 인한 Compose 불안정성 해결
 */

@Stable
data class ChatMessageUi(
    val messageId: Long,
    val roomId: Long,
    val content: String,
    val messageType: String,
    val createdAt: String,
    val images: PersistentList<MessageImageUi>?,
    val senderNickname: String,
    val senderId: String,
    val checked: Boolean,
    val isMine: Boolean
)

@Stable
data class MessageImageUi(
    val imageId: Long,
    val imageUrl: String
)