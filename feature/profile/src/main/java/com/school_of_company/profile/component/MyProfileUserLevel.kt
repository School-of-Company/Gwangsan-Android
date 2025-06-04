package com.school_of_company.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.icons.EllipseIcon
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
internal fun MyProfileUserLevel(
    modifier: Modifier = Modifier,
    name: String,
    coverImage: String?,
    description: String,
    level: Int,
) {
    GwangSanTheme { colors, typography ->

        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(colors.white)
        ) {
            if (coverImage.isNullOrEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_image),
                        contentDescription = "이미지",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.5f)
                    ) {
                        EllipseIcon()
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${name}단계",
                            style = typography.body5,
                        )

                        Text(
                            text = description,
                            color = colors.gray500,
                            style = typography.body5
                        )
                    }

                    Text(
                        text = "${level}단계",
                        color = colors.subYellow500, // 노란-주황 계열
                        style = typography.body5,
                    )
                }
            }
            else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_image), // 이미지 리소스 추가 필요
                        contentDescription = "바퀴벌레 이미지",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Box(modifier = Modifier.size(40.dp)) {
                        EllipseIcon()
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // 이름과 설명
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "${name}단계",
                            style = typography.body5,
                        )

                        Spacer(modifier = Modifier.padding(5.dp))

                        Text(text = description, color = colors.gray500, style = typography.body5)
                    }


                    Text(
                        text = "${level}단계",
                        color = colors.subYellow500, // 노란-주황 계열
                        style = typography.body5,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun UserLevelCardPreview()
{
    MyProfileUserLevel(
        name = "모태환",
        coverImage = "https://image.dongascience.com/Photo/2019/12/fb4f7da04758d289a466f81478f5f488.jpg",
        description = "바퀴벌레",
        level = 1
    )
}