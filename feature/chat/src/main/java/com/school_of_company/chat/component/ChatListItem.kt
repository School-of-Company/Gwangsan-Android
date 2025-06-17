package com.school_of_company.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.school_of_company.chat.view.ChatRoom
import com.school_of_company.design_system.R
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
fun ChatListItem(
    item: ChatRoom,
    modifier: Modifier = Modifier
) {
    GwangSanTheme { colors, typography ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.rectangle),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text (
                    text = item.name,
                    style = typography.label,
                    color = if (item.unreadCount > 0) colors.black else colors.gray400
                )
                Text(
                    text = item.lastMessage,
                    style = typography.label,
                    color = if (item.unreadCount > 0) colors.black else colors.gray400
                )
            }

            if (item.unreadCount > 0) {
                Spacer(modifier = Modifier.width(8.dp))
                UnreadBadge()
            }
        }
    }
}