package com.yourpackage.design_system.component.textField

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import com.school_of_company.design_system.componet.icon.SearchIcon
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.color.GwangSanColor
import kotlinx.coroutines.delay

@Composable
fun GwangSanTextField(
    modifier: Modifier = Modifier,
    value: String? = null,
    label: String,
    placeHolder: String,
    errorText: String = "",
    isError: Boolean = false,
    isDisabled: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onTextChange: (String) -> Unit,
    icon: @Composable () -> Unit = {},
    isReadOnly: Boolean = false
) {
    var text by remember { mutableStateOf(value ?: "") }
    val isFocused = remember { mutableStateOf(false)}

    GwangSanTheme { colors, typography ->

        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = label,
                color = if (isError) GwangSanColor.error else GwangSanColor.black,
                style = typography.body5
            )

            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                    onTextChange(it)
                },
                visualTransformation = visualTransformation,
                textStyle = typography.body5,
                maxLines = 1,
                keyboardOptions = keyboardOptions,
                readOnly = isReadOnly,
                enabled = !isDisabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isFocused.value = it.isFocused }
                    .border(
                        width = 1.dp,
                        color = when {
                            isError -> GwangSanColor.error
                            isFocused.value -> GwangSanColor.subYellow500
                            text.isNotEmpty() -> GwangSanColor.subYellow500
                            else -> GwangSanColor.gray400
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box {
                            if (text.isEmpty()) {
                                Text(
                                    text = placeHolder,
                                    color = if (isError) GwangSanColor.error else GwangSanColor.gray400,
                                    style = typography.body5
                                )
                            }
                            innerTextField()
                        }

                        icon()
                    }
                }
            )

            if (isError) {
                Row(horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = errorText,
                        color = GwangSanColor.error,
                        style = typography.label
                    )
                }
            }
        }
    }
}
@Composable
fun GwangSanSearchTextField(
    modifier: Modifier = Modifier,
    debounceTime: Long = 300L,
    placeHolder: String,
    readOnly: Boolean = false,
    isError: Boolean = false,
    focusManager: FocusManager = LocalFocusManager.current,
    focusRequester: FocusRequester = FocusRequester(),
    setText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit = {},
    onSearchTextChange: (String) -> Unit = {}
) {

    GwangSanTheme { colors, typography ->
        val isFocused = remember { mutableStateOf(false) }

        DisposableEffect(Unit) {
            onDispose {
                focusManager.clearFocus()
            }
        }

        LaunchedEffect(setText) {
            delay(debounceTime)
            onSearchTextChange(setText)
        }

        Column {
            TextField(
                value = setText,
                onValueChange = {
                    val filteredText = it.filterNot { text -> text.isWhitespace() }
                    onValueChange(filteredText)
                },
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                placeholder = {
                    Text(
                        text = placeHolder,
                        style = typography.body5,
                        color = colors.gray400
                    )
                },
                modifier = modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colors.white)
                    .border(
                        width = 1.dp,
                        color = when {
                            isError -> GwangSanColor.error
                            isFocused.value -> GwangSanColor.subYellow500
                            setText.isNotEmpty() -> GwangSanColor.subYellow500
                            else -> GwangSanColor.gray400
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .onFocusChanged {
                        isFocused.value = it.isFocused
                    },
                maxLines = maxLines,
                singleLine = singleLine,
                textStyle = typography.titleMedium2,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = GwangSanColor.black,
                    unfocusedTextColor = GwangSanColor.black,
                    focusedPlaceholderColor = GwangSanColor.gray400,
                    unfocusedPlaceholderColor = GwangSanColor.gray400,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = GwangSanColor.subYellow500,
                    focusedIndicatorColor = GwangSanColor.subYellow500,
                    unfocusedIndicatorColor = GwangSanColor.gray400,
                    disabledIndicatorColor = GwangSanColor.gray400
                ),
                trailingIcon = {
                    SearchIcon()
                },
                readOnly = readOnly,
                visualTransformation = visualTransformation
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GwangSanTextFieldPreview() {
    var text by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        GwangSanTextField(
            label = "이름",
            value = text,
            onTextChange = { text = it },
            placeHolder = "이름을 입력하세요"
        )

        GwangSanTextField(
            label = "이메일",
            value = text,
            onTextChange = { text = it },
            placeHolder = "이메일을 입력하세요",
            isError = true,
            errorText = "유효하지 않은 이메일입니다"
        )

        GwangSanTextField(
            label = "전화번호",
            value = "010-1234-5678",
            onTextChange = {},
            placeHolder = "전화번호를 입력하세요"
        )
    }
}