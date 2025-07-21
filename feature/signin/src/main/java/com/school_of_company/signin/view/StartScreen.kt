import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.button.GwangSanEnableButton
import com.school_of_company.design_system.component.button.GwangSanStateButton
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.ui.previews.GwangsanPreviews

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
    onInputLoginClick: () -> Unit,
    focusManager: FocusManager = LocalFocusManager.current,
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
                .navigationBarsPadding()
        ) {
            Column {

                Spacer(modifier = Modifier.height(68.dp))

                Box(
                    modifier = Modifier
                        .pointerInput(Unit) {
                            detectTapGestures { focusManager.clearFocus() }
                        }
                        .weight(1f)
                ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxWidth()
                    ) { page ->
                        Image(
                            painter = painterResource(id = imageList[page]),
                            contentDescription = "Gwangsan Start Images",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(top = 12.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 39.dp)
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

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 25.dp,)
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

                    Spacer(modifier = Modifier.padding(bottom = 55.dp))
                }
            }
        }
    }
}

@GwangsanPreviews
@Composable
private fun StartScreenPreview() {
    StartScreen(
        onInputLoginClick = {},
        onSignUpClick = {}
    )
}