package com.school_of_company.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.school_of_company.chat.ui.model.GetChatRoomResponseUi
import com.school_of_company.chat.ui.model.GetMemberResponseUi
import com.school_of_company.chat.ui.model.GetProductResponseUi
import com.school_of_company.chat.util.formatChatTime
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.chat.response.GetChatRoomResponseModel
import com.school_of_company.model.chat.response.GetMemberResponseModel
import com.school_of_company.model.chat.response.GetProductResponseModel
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun ChatListItem(
    modifier: Modifier = Modifier,
    onClick: (Long) -> Unit,
    item: GetChatRoomResponseUi,
) {
    GwangSanTheme { colors, typography ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .GwangSanClickable { onClick(item.product.productId) }
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
        ) {

            if (item.product.images.isNullOrEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.gwangsan),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
            } else {
                AsyncImage(
                    model = item.product.images.first().imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "\" ${item.product.title} \"",
                    style = typography.body4,
                    color = if (item.unreadMessageCount > 0) colors.black else colors.gray400
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = item.member.nickname,
                    style = typography.body3,
                    color = if (item.unreadMessageCount > 0) colors.black else colors.gray400
                )

                Spacer(modifier = Modifier.height(5.dp))

                val messagePreview = when (item.lastMessageType) {
                    "IMAGE" -> "이미지를 보냈습니다."
                    else -> item.lastMessage
                }

                Text(
                    text = messagePreview,
                    style = typography.label,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = if (item.unreadMessageCount > 0L) colors.black else colors.gray400
                )
            }

            Column {

                Text(
                    text = formatChatTime(item.lastMessageTime),
                    style = typography.caption,
                    color = colors.gray400
                )

                Spacer(modifier = Modifier.height(4.dp))

                if (item.unreadMessageCount > 0L) {
                    UnreadBadge(
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatListItemPreview() {
    val dummyItem = GetChatRoomResponseUi(
        roomId = 1L,
        member = GetMemberResponseUi(
            memberId = 42L,
            nickname = "홍길동"
        ),
        messageId = 100L,
        lastMessage = "안녕하세요, 채팅 테스트입니다!",
        lastMessageType = "TEXT",
        lastMessageTime = "2025-07-22T12:34:56",
        unreadMessageCount = 2L,
        product = GetProductResponseUi(
            productId = 1000L,
            title = "상품 제목",
            images = persistentListOf()
        )
    )

    ChatListItem(
        item = dummyItem,
        onClick = {},
    )
}
