package com.school_of_company.chat.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.school_of_company.chat.ui.model.ChatMessageUi
import com.school_of_company.chat.ui.model.TradeProductUi
import com.school_of_company.content.ui.model.PostUi
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.button.GwangSanEnableButton
import com.school_of_company.design_system.component.button.GwangSanStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.color.GwangSanColor


@Composable
fun TradeActionBubble(
    data: TradeProductUi,
    message: ChatMessageUi,
    modifier: Modifier = Modifier,
    onReserveClick: () -> Unit,
    onDealClick: () -> Unit
) {

    GwangSanTheme { color, typo ->
        // 아바타를 위로 올리기 위해 Column으로 상단 정렬
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = if (message.isMine) Alignment.End else Alignment.Start
        ) {
            // 상대방 메시지일 때만 프로필 노출 (내 메시지는 생략)
            if (!message.isMine) {
                Image(
                    painter = painterResource(id = R.drawable.rectangle),
                    contentDescription = "상대 프로필",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // 본문: 세로 라인 + 카드 콘텐츠
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min), // 세로 라인이 콘텐츠 높이에 맞게
                verticalAlignment = Alignment.Top
            ) {
                // 왼쪽 가장자리 회색 세로선
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                        .background(color.gray300)
                )

                Spacer(Modifier.width(12.dp))

                Column {
                    // 겹치는 이미지 영역
                    Box(modifier = Modifier.padding(bottom = 12.dp)) {
                        val img1 = data.images?.firstOrNull()?.imageUrl
                        val img2 = data.images?.getOrNull(1)?.imageUrl

                        // 큰 이미지
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, color.gray300, RoundedCornerShape(12.dp))
                        ) {
                            if (img1 != null) {
                                AsyncImage(
                                    model = img1,
                                    contentDescription = "제품 이미지(뒤)",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        // 작은 이미지(우상단 겹치기)
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .align(Alignment.BottomEnd)
                                .offset(x = 38.dp, y = (-1).dp)
                                .clip(RoundedCornerShape(12.dp))

                        ) {
                            if (img2 == null) {
                                Box(modifier = Modifier.size(70.dp))

                            } else {
                                AsyncImage(
                                    model = img2,
                                    contentDescription = "제품 이미지(앞)",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }

                    Text(text = data.title, style = typo.body1, color = color.black)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "${message.senderNickname}님께서 거래하기를 누르셨습니다",
                        style = typo.body4,
                        color = color.black
                    )
                    Spacer(Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        GwangSanEnableButton(
                            text = "예약하기",
                            textColor = GwangSanColor.main500,
                            backgroundColor = GwangSanColor.white,
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .border(1.dp, GwangSanColor.main500, RoundedCornerShape(8.dp)),
                            onClick = onReserveClick
                        )

                        GwangSanStateButton(
                            text = "거래하기",
                            onClick = onDealClick,
                            state = if (data.isCompletable && !data.isSeller){
                                ButtonState.Enable
                            } else {
                                ButtonState.Disable
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                        )
                    }
                }
            }
        }
    }
}


