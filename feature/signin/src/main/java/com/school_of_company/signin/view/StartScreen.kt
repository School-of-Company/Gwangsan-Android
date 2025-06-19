import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.R
import com.school_of_company.design_system.componet.button.GwangSanEnableButton
import com.school_of_company.design_system.componet.button.GwangSanStateButton
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
internal fun StartRoute(
    onSignUpClick: () -> Unit,
    onInputLoginClick: () -> Unit
) {
    StartScreen(
        onSignUpClick = onSignUpClick,
        onInputLoginClick = onInputLoginClick
    )
}

@Composable
private fun StartScreen(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit,
    onInputLoginClick: () -> Unit
) {
    GwangSanTheme { colors, _ ->

        val imageList = listOf(
            R.drawable.start1,
            R.drawable.start2,
            R.drawable.start3
        )

        val pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { imageList.size }
        )

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
                .imePadding()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 60.dp)
                        .weight(1f)
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        Image(
                            painter = painterResource(id = imageList[page]),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 30.dp),
                    ) {
                        repeat(imageList.size) { index ->
                            val isSelected = pagerState.currentPage == index
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 3.dp)
                                    .size(if (isSelected) 12.dp else 6.dp)
                                    .background(
                                        color = if (isSelected) colors.main500 else colors.gray300,
                                        shape = RoundedCornerShape(50)
                                    )
                            )
                        }
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(
                        horizontal = 24.dp,
                        vertical = 12.dp
                    )
                ) {
                    GwangSanEnableButton(
                        text = "회원가입",
                        textColor = colors.main500,
                        backgroundColor = colors.white,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = colors.main500,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        onSignUpClick()
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    GwangSanStateButton(
                        text = "로그인",
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = colors.main500,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        onInputLoginClick()
                    }
                }
            }
        }
    }
}