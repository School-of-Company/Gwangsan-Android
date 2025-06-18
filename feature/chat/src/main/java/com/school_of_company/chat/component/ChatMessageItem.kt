package com.school_of_company.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.chat.view.ChatMessage
import com.school_of_company.design_system.R
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
fun ChatMessageItem(
    message: ChatMessage,
    modifier: Modifier = Modifier
) {
    GwangSanTheme { colors, typography ->
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = if (message.isMine) Arrangement.End else Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            if (!message.isMine) {
                Image(
                    painter = painterResource(id = R.drawable.rectangle),
                    contentDescription = "상대 프로필",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(10.dp))
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .then(
                        if (message.isMine) Modifier.background(colors.subYellow500)
                        else Modifier
                            .background(colors.white)
                            .border(1.dp, colors.gray500, RoundedCornerShape(12.dp))
                    )
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Text(
                    text = message.text,
                    style = typography.label,
                    color = if (message.isMine) colors.white else colors.black
                )
            }
        }
    }
}

@Preview
@Composable
fun MyMessagePreview() {
    ChatMessageItem(message = ChatMessage("1", "지금 거래 가능하나요?", true))
}

@Preview
@Composable
fun OpponentMessagePreview() {
    ChatMessageItem(message = ChatMessage("2", "네 거래 가능합니다", false))
}