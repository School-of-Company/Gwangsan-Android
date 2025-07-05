package com.school_of_company.profile.component

import android.widget.ProgressBar
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.color.GwangSanColor

data class Review(
    val content: String,
    val light: Int,
    val reviewedId: Int,
    val productId: Int,
    val createdAt: String
)

@Composable
internal fun MyProfileReviewListItem(
    modifier: Modifier = Modifier,
    data: Review,
    onClick: () -> Unit = {}
) {
    GwangSanTheme { colors, typography ->
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            onClick = onClick
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MyProfileReviewProgressBar(
                    progress = (data.light / 100f).coerceIn(0f, 1f),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = data.content,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = data.createdAt,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }
        }
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

        drawRoundRect(
            color = GwangSanColor.gray200,
            topLeft = Offset(0f, 0f),
            size = Size(canvasWidth, canvasHeight),
            cornerRadius = CornerRadius(cornerRadius, cornerRadius)
        )

        val progressWidth = canvasWidth * progress.coerceIn(0f, 1f)
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

@Preview
@Composable
private fun MyProfileReviewListPreview() {
    MyProfileReviewListItem(
        data = Review(
            content = "뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨",
            light = 50,
            reviewedId = 1,
            productId = 123,
            createdAt = "2023-10-01"
        )
    )
}