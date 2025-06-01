package com.example.post.view.service

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.progress.GwangSanTopBarProgress
import com.school_of_company.design_system.componet.button.GwangSanEnableButton
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.componet.button.state.ButtonState
import com.school_of_company.design_system.componet.icon.AddImageButton
import com.school_of_company.design_system.componet.icon.CloseIcon
import com.school_of_company.design_system.componet.icon.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.color.GwangSanColor

@Composable
fun FinalPostScreen(
    subject: String,
    content: String,
    price: String,
    imageContent: @Composable () -> Unit,
    onEditClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onBackClick: () -> Unit,
    onCloseClick: () -> Unit
) {
    GwangSanTheme { colors, typography ->
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(52.dp))

            GwangSanSubTopBar(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 24.dp),
                startIcon = {
                    DownArrowIcon(
                        modifier = Modifier
                            .width(8.dp)
                            .height(14.dp)
                            .clickable(onClick = onBackClick)
                    )
                },
                betweenText = "해주세요",
                endIcon = {
                    CloseIcon(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(onClick = onCloseClick)
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            GwangSanTopBarProgress(
                modifier = Modifier.fillMaxWidth(),
                progressRatio = 1.0f
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = "다시 한번 확인해주세요.",
                    style = typography.titleSmall,
                    color = colors.black
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(text = "주제", style = typography.body5, color = colors.black)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colors.white, shape = RoundedCornerShape(8.dp))
                        .border(1.dp, GwangSanColor.subYellow500, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(text = subject, style = typography.body5, color = colors.black)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "내용", style = typography.body5, color = colors.black)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colors.white, shape = RoundedCornerShape(8.dp))
                        .border(1.dp, GwangSanColor.subYellow500, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(text = content, style = typography.body5, color = colors.black)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "사진첨부", style = typography.body5, color = colors.black)
                Spacer(modifier = Modifier.height(12.dp))
                Row { imageContent() }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "광산", style = typography.body5, color = colors.black)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colors.white, shape = RoundedCornerShape(8.dp))
                        .border(1.dp, GwangSanColor.subYellow500, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(text = price, style = typography.body5, color = colors.black)
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GwangSanEnableButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .border(1.dp, GwangSanColor.main500, shape = RoundedCornerShape(8.dp)),
                        text = "수정",
                        textColor = GwangSanColor.main500,
                        backgroundColor = GwangSanColor.white,
                        onClick = onEditClick
                    )

                    GwangSanStateButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        text = "완료",
                        state = ButtonState.Enable,
                        onClick = onSubmitClick
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FinalPostScreenPreview() {
    FinalPostScreen(
        subject = "디자인 작업 도와주실 분 찾습니다",
        content = "간단한 포스터 디자인이나 카드뉴스 제작 도와주실 분을 찾고 있어요.",
        price = "4000",
        imageContent = {
            AddImageButton(
                onClick = {},
                rippleColor = GwangSanColor.gray300
            )
        },
        onEditClick = { println("수정 클릭") },
        onSubmitClick = { println("완료 클릭") },
        onBackClick = { println("뒤로가기 클릭") },
        onCloseClick = { println("닫기 클릭") }
    )
}