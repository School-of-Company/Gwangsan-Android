package com.school_of_company.profile.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.school_of_company.design_system.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.color.GwangSanColor
import com.school_of_company.profile.ui.model.ReviewResponseUi
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun MyProfileReviewListItem(
    modifier: Modifier = Modifier,
    data: ReviewResponseUi,
    onClick: (Long) -> Unit
) {
    GwangSanTheme { _, _ ->
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = modifier
                .GwangSanClickable { onClick(data.productId) }
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    if (data.images.isEmpty()) {
                        Image(
                            painter = painterResource(id = R.drawable.gwangsan),
                            contentDescription = "기본 이미지",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Image(
                            painter = rememberAsyncImagePainter(model = data.images.first().imageUrl),
                            contentDescription = "후기 이미지",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // light: 1..10 -> 진행 비율 0f..1f
                    val progress = (data.light.coerceIn(1, 10) / 10f)

                    MyProfileReviewProgressBar(
                        progress = progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )

                    Text(
                        text = data.content,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = data.reviewerName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun MyProfileReviewProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    height: Float = 8f
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val cornerRadius = canvasHeight / 2f

        // 배경 바
        drawRoundRect(
            color = GwangSanColor.gray200,
            topLeft = Offset(0f, 0f),
            size = Size(canvasWidth, canvasHeight),
            cornerRadius = CornerRadius(cornerRadius, cornerRadius)
        )

        // 진행 바 (0f..1f 보정)
        val clamped = progress.coerceIn(0f, 1f)
        val progressWidth = canvasWidth * clamped
        if (progressWidth > 0f) {
            drawRoundRect(
                color = GwangSanColor.subYellow300,
                topLeft = Offset(0f, 0f),
                size = Size(progressWidth, canvasHeight),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )
        }
    }
}

