package com.school_of_company.profile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.profile.component.BrightnessProgressBar
import com.school_of_company.profile.component.GwangSanMoney
import com.school_of_company.profile.component.MyInformation
import com.school_of_company.profile.component.MyProfileExerciseButton
import com.school_of_company.profile.component.MyProfileReviewListItem
import com.school_of_company.profile.component.Review

@Composable
internal fun MyProfileRoute(
    onMyWritingClick: () -> Unit,
    onMyReviewClick: () -> Unit,
    onTransactionHistoryClick: () -> Unit
) {
    MyProfileScreen(
        brightnessLevel = 6,
        miningAmount = 0,
        onMyWritingClick = onMyWritingClick,
        onMyReviewClick = onMyReviewClick,
        onTransactionHistoryClick = onTransactionHistoryClick,
        item = listOf(
            Review(
                content = "뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨",
                light = 4,
                reviewedId = 1,
                productId = 123,
                createdAt = "2023-10-01"
            ),
            Review(
                content = "뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨",
                light = 4,
                reviewedId = 1,
                productId = 123,
                createdAt = "2023-10-01"
            ),
            Review(
                content = "뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨",
                light = 4,
                reviewedId = 1,
                productId = 123,
                createdAt = "2023-10-01"
            ),
            Review(
                content = "뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨",
                light = 4,
                reviewedId = 1,
                productId = 123,
                createdAt = "2023-10-01"
            )
        )
    )
}

@Composable
private fun MyProfileScreen(
    modifier: Modifier = Modifier,
    brightnessLevel: Int,
    onMyWritingClick: () -> Unit,
    onMyReviewClick: () -> Unit,
    onTransactionHistoryClick: () -> Unit,
    miningAmount: Int,
    item: List<Review>
) {
    GwangSanTheme { colors, typography ->

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
        ) {
            item {
                GwangSanSubTopBar(
                    startIcon = { Box(modifier = Modifier.size(24.dp)) },
                    betweenText = "프로필",
                    modifier = Modifier.padding(24.dp),
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item { MyInformation(onModifyClick = {}) }

            item {
                HorizontalDivider(
                    thickness = 12.dp,
                    color = colors.gray200
                )
            }

            item {
                BrightnessProgressBar(
                    brightnessLevel = brightnessLevel,
                    maxLevel = 10,
                    modifier = Modifier.padding(24.dp),
                )
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }

            item {
                GwangSanMoney(
                    miningAmount = miningAmount,
                    modifier = Modifier.padding(24.dp),
                )
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            item {
                HorizontalDivider(
                    thickness = 12.dp,
                    color = colors.gray200
                )
            }

            item {
                Text(
                    text = "내 활동",
                    style = typography.body1,
                    color = colors.black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                )
            }

            item {
                Row(modifier = Modifier.padding(horizontal = 24.dp)) {
                    MyProfileExerciseButton(
                        modifier = Modifier.weight(1f),
                        onClick = { onMyWritingClick() },
                        buttonText = "내 글"
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    MyProfileExerciseButton(
                        modifier = Modifier.weight(1f),
                        onClick = { onTransactionHistoryClick() },
                        buttonText = "거래내역"
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    MyProfileExerciseButton(
                        onClick = { onMyReviewClick() },
                        buttonText = "내가 작성한 후기",
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 52.dp)
                    )
                }
            }

            item {
                HorizontalDivider(
                    thickness = 12.dp,
                    color = colors.gray200
                )
            }

            item {
                Text(
                    text = "내 후기",
                    style = typography.body1,
                    color = colors.black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                )
            }

            items(
                items = item,
                key = { it.reviewedId },
            ) { reviewItem ->
                MyProfileReviewListItem(
                    data = reviewItem
                )
            }
        }
    }
}

@Preview
@Composable
private fun MyProfileScreenPreview() {
    MyProfileScreen(
        brightnessLevel = 6,
        miningAmount = 0,
        onMyWritingClick = {},
        onMyReviewClick = {},
        onTransactionHistoryClick = {},
        item = listOf(
            Review(
                content = "뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨",
                light = 4,
                reviewedId = 1,
                productId = 123,
                createdAt = "2023-10-01"
            ),
            Review(
                content = "뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨",
                light = 4,
                reviewedId = 2,
                productId = 123,
                createdAt = "2023-10-01"
            ),
            Review(
                content = "뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨",
                light = 4,
                reviewedId = 3,
                productId = 123,
                createdAt = "2023-10-01"
            ),
            Review(
                content = "뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨뀨",
                light = 4,
                reviewedId = 4,
                productId = 123,
                createdAt = "2023-10-01"
            )
        )
    )
}