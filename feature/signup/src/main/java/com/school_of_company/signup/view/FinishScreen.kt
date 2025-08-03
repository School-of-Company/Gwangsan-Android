package com.school_of_company.signup.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.button.GwangSanButton
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.ui.previews.GwangsanPreviews

@Composable
internal fun FinishRoute(
    onClickGoToLogin: () -> Unit,
){
    FinishScreen(onClickGoToLogin = onClickGoToLogin)
}


@Composable
fun FinishScreen(
    modifier: Modifier = Modifier,
    onClickGoToLogin: () -> Unit
) {
    GwangSanTheme { colors, typography ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = com.school_of_company.design_system.R.drawable.gwangsan),
                    contentDescription = "회원가입 완료 이미지",
                    modifier = Modifier.size(300.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "회원가입이\n완료되었습니다",
                    color = colors.subBlue500,
                    style = typography.titleMedium2
                )
            }

            GwangSanButton(
                text = "로그인 페이지로 돌아가기",
                color = colors.main500,
                textColor = colors.white,
                onClick = onClickGoToLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            )
        }
    }
}

@GwangsanPreviews
@Composable
fun FinishScreenPreview() {
    FinishScreen(onClickGoToLogin = {})
}
