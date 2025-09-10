package com.school_of_company.signup.view

import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.component.button.GwangSanButton
import com.school_of_company.design_system.component.button.GwangSanStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.icons.DownArrowIcon
import com.school_of_company.design_system.component.topbar.GwangSanTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.signup.viewmodel.SignUpViewModel
import com.school_of_company.design_system.component.textfield.GwangSanSelectTextField
import androidx.hilt.navigation.compose.hiltViewModel
import com.school_of_company.ui.previews.GwangsanPreviews
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun IntroduceRoute(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onErrorToast: (throwable: Throwable?, message: Int?) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(
        viewModelStoreOwner = LocalContext.current.let { context ->
            var ctx = context
            while (ctx is ContextWrapper) {
                if (ctx is ComponentActivity) return@let ctx
                ctx = ctx.baseContext
            }
            ctx as ComponentActivity
        }
    )
) {

    val specialty by viewModel.specialty.collectAsStateWithLifecycle()
    val specialtyText by viewModel.specialtyText.collectAsStateWithLifecycle()
    val specialtyOptionList by viewModel.specialtyOptions.collectAsStateWithLifecycle()

    val isDropdownVisible by viewModel.specialtyDropdownVisible.collectAsStateWithLifecycle()

    IntroduceScreen(
        specialty = specialty.toPersistentList(),
        specialtyText = specialtyText,
        specialtyOptionList = specialtyOptionList.toPersistentList(),
        removeSpecialty = viewModel::removeSpecialty,
        isDropdownVisible = isDropdownVisible,
        onBackClick = onBackClick,
        onNextClick = onNextClick,
        onSpecialtyTextChange = viewModel::onSpecialtyTextChange,
        onSpecialtyChange = viewModel::onSpecialtyListChange,
        addSpecialty = viewModel::addSpecialty
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun IntroduceScreen(
    modifier: Modifier = Modifier,
    specialty: PersistentList<String>,
    specialtyText: String,
    removeSpecialty: (String) -> Unit,
    specialtyOptionList: PersistentList<String>,
    onSpecialtyTextChange: (String) -> Unit,
    addSpecialty: () -> Unit,
    isDropdownVisible: Boolean,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onSpecialtyChange: (List<String>) -> Unit
) {
    val scrollState = rememberScrollState()

    GwangSanTheme { colors, typography ->

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = colors.white)
                .imePadding()
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(scrollState),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 80.dp,
                            bottom = 32.dp
                        ),
                    horizontalArrangement = Arrangement.Start
                ) {
                    GwangSanTopBar(
                        startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
                        betweenText = "뒤로"
                    )
                }

                Text(
                    text = "회원가입",
                    style = typography.titleMedium2,
                    color = colors.black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "자신을 소개하는 글을 작성해주세요",
                    style = typography.label,
                    color = colors.black.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    GwangSanSelectTextField(
                        label = "특기",
                        value = specialtyText,
                        placeHolder = "특기 입력",
                        onClick = {},
                        onTextChange = onSpecialtyTextChange,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    GwangSanButton(
                        text = "추가",
                        textColor = colors.white,
                        color = colors.main500,
                        onClick = { addSpecialty() },
                        modifier = Modifier
                            .height(56.dp)
                            .width(60.dp)
                    )
                }

                FlowRow(modifier = Modifier.padding(top = 12.dp)) {
                    specialty.forEach { item ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                text = item,
                                color = colors.gray700
                            )
                            IconButton(onClick = { removeSpecialty(item) }) {
                                Icon(
                                    Icons.Default.Close,
                                    tint = colors.gray700,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                MultiSelectDropdown(
                    options = specialtyOptionList,
                    selectedOptions = specialty,
                    onSelectionChange = onSpecialtyChange,
                    onDismissRequest = {},
                    modifier = Modifier.fillMaxWidth()
                )


                Spacer(modifier = Modifier.height(40.dp))
            }

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        bottom = 64.dp
                    ),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isDropdownVisible) colors.gray200 else colors.white)
                ) {
                    if (isDropdownVisible) {
                        GwangSanButton(
                            text = "다음",
                            color = colors.gray400,
                            textColor = colors.gray500,
                            onClick = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                        )
                    } else {
                        GwangSanStateButton(
                            text = "다음",
                            state = if (specialty.isNotEmpty()) ButtonState.Enable else ButtonState.Disable,
                            onClick = { onNextClick() },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@GwangsanPreviews
@Composable
private fun IntroduceScreenPreview() {
    IntroduceScreen(
        specialty = persistentListOf(),
        specialtyText = "",
        isDropdownVisible = false,
        onBackClick = {},
        onNextClick = {},
        onSpecialtyChange = {},
        onSpecialtyTextChange = {},
        addSpecialty = {},
        specialtyOptionList = persistentListOf(),
        removeSpecialty = {}
    )
}