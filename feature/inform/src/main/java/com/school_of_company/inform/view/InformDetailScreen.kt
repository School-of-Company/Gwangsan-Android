package com.school_of_company.inform.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
fun InformDetailScreen(
    title: String,
    branchName: String,
    description: String,
    imageUrl: String? = null,
    onBackClick: () -> Unit = {},
) {
    GwangSanTheme { colors, typography ->
        Scaffold(
            containerColor = colors.white,
            topBar = {
                GwangSanSubTopBar(
                    modifier = Modifier.padding(top = 56.dp),
                    startIcon = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.width(24.dp))
                            DownArrowIcon(
                                modifier = Modifier
                                    .padding(start = 16.dp)
                                    .size(width = 8.dp, height = 14.dp)
                                    .GwangSanClickable { onBackClick() }
                            )
                        }
                    },
                    betweenText = "공지",
                    endIcon = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.width(24.dp))
                            CloseIcon(
                                modifier = Modifier
                                    .padding(end = 24.dp)
                                    .size(24.dp)
                                    .GwangSanClickable { onBackClick() }
                            )
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                if (imageUrl != null) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "공지 이미지",
                        placeholder = painterResource(id = R.drawable.gwangsan),
                        error = painterResource(id = R.drawable.gwangsan),
                        fallback = painterResource(id = R.drawable.gwangsan),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 24.dp)) {
                    Text(
                        text = title,
                        style = typography.titleMedium2
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = branchName,
                        style = typography.body3,
                        color = colors.black
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = description,
                        style = typography.body4,
                        color = colors.black
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InformDetailScreenPreviewWithImage() {
    InformDetailScreen(
        title = "당분간 거래중지입니다",
        branchName = "첨단 1동",
        description = "어떤 이의 거래 중지 의사로 인해 요청사항을 받아들이다 보니 이렇게 거래중지를 하게 되었습니다. 양해 부탁드립니다.",
        imageUrl = "https://your.cdn.com/sample.jpg"
    )
}

@Preview(showBackground = true)
@Composable
fun InformDetailScreenPreviewWithoutImage() {
    InformDetailScreen(
        title = "당분간 거래중지입니다",
        branchName = "첨단 1동",
        description = "어떤 이의 거래 중지 의사로 인해 요청사항을 받아들이다 보니 이렇게 거래중지를 하게 되었습니다. 양해 부탁드립니다.",
        imageUrl = null
    )
}
