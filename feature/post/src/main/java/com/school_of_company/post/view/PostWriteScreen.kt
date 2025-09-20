package com.school_of_company.post.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.design_system.component.button.GwangSanStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.textfield.GwangSanSelectTextField
import com.school_of_company.design_system.component.textfield.GwangSanTextField
import com.school_of_company.design_system.component.toast.makeToast
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.post.viewmodel.PostViewModel

@Composable
internal fun PostWriteRoute(
    onBackClick: () -> Unit,
    onNextClick: (String, String) -> Unit,
    viewModel: PostViewModel? = null,
) {
    val actualViewModel = viewModel ?: hiltViewModel()

    val subject by actualViewModel.title.collectAsStateWithLifecycle()
    val content by actualViewModel.content.collectAsStateWithLifecycle()

    val selectedType by actualViewModel.type.collectAsStateWithLifecycle()
    val selectedMode by actualViewModel.mode.collectAsStateWithLifecycle()


    PostWriteScreen(
        subject = subject,
        content = content,
        onSubjectChange = actualViewModel::onTitleChange,
        onContentChange = actualViewModel::onContentChange,
        onNextClick = onNextClick,
        onBackClick = onBackClick,
        selectedType = selectedType,
        selectedMode = selectedMode,
        onTypeSelect = actualViewModel::setType,
        onModeSelect = actualViewModel::setMode
    )
}

@Composable
private fun PostWriteScreen(
    modifier: Modifier = Modifier,
    subject: String,
    content: String,
    onSubjectChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onNextClick: (String, String) -> Unit,
    onBackClick: () -> Unit,
    selectedType: Type,
    selectedMode: Mode,
    onTypeSelect: (Type) -> Unit,
    onModeSelect: (Mode) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    val typeOptions = mapOf(Type.OBJECT to "물건", Type.SERVICE to "서비스")


    val modeOptionsForType: List<Pair<Mode, String>> = remember(selectedType) {
        if (selectedType == Type.SERVICE) {
            listOf(
                Mode.RECEIVER to "해주세요",
                Mode.GIVER to "할 수 있어요",
            )
        } else {
            listOf(
                Mode.GIVER to "팔아요",
                Mode.RECEIVER to "필요해요"
            )
        }
    }


    val modeLabel by remember(selectedType, selectedMode) {
        mutableStateOf(
            when (selectedType) {
                Type.SERVICE -> if (selectedMode == Mode.RECEIVER) "해주세요" else "할 수 있어요"
                Type.OBJECT  -> if (selectedMode == Mode.RECEIVER) "필요해요" else "팔아요"
            }
        )
    }

    val isNextEnabled = subject.isNotBlank() && content.isNotBlank()

    var typeExpanded by remember { mutableStateOf(false) }
    var modeExpanded by remember { mutableStateOf(false) }

    GwangSanTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
                .padding(horizontal = 24.dp)
                .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
        ) {
            GwangSanSelectTextField(
                label = "카테고리",
                value = typeOptions[selectedType] ?: "",
                placeHolder = "카테고리를 선택해주세요",
                onClick = { typeExpanded = true },
                onTextChange = { /* NO-OP */ }
            )
            DropdownMenu(
                expanded = typeExpanded,
                onDismissRequest = { typeExpanded = false }
            ) {
                typeOptions.forEach { (enumValue, displayText) ->
                    DropdownMenuItem(
                        text = { Text(displayText) },
                        onClick = {
                            onTypeSelect(enumValue)
                            typeExpanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            GwangSanSelectTextField(
                label = "유형",
                value = modeLabel,
                placeHolder = "유형을 선택해주세요",
                onClick = { modeExpanded = true },
                onTextChange = { /* NO-OP */ }
            )
            DropdownMenu(
                expanded = modeExpanded,
                onDismissRequest = { modeExpanded = false }
            ) {
                modeOptionsForType.forEach { (enumValue, displayText) ->
                    DropdownMenuItem(
                        text = { Text(displayText) },
                        onClick = {
                            onModeSelect(enumValue)
                            modeExpanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            GwangSanTextField(
                value = subject,
                onTextChange = onSubjectChange,
                label = "주제",
                placeHolder = "주제를 작성해주세요"
            )

            Spacer(modifier = Modifier.height(28.dp))

            GwangSanTextField(
                value = content,
                onTextChange = onContentChange,
                label = "내용",
                placeHolder = "내용을 작성해주세요",
                singleLine = false,
                maxLines = Int.MAX_VALUE,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.weight(1f, fill = true))

            GwangSanStateButton(
                text = "다음",
                state = if (isNextEnabled) ButtonState.Enable else ButtonState.Disable,
                onClick = { onNextClick(subject, content) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}