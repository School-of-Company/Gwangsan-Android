package com.yourpackage.design_system.component.textField

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.R
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.icons.PlusIcon
import com.school_of_company.design_system.component.icons.SearchIcon
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
    isReadOnly: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = false
) {
    val isFocused = remember { mutableStateOf(false) }

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
                value = value ?: "",
                onValueChange = onTextChange,
                visualTransformation = visualTransformation,
                textStyle = typography.body5,
                keyboardOptions = keyboardOptions,
                readOnly = isReadOnly,
                enabled = !isDisabled,
                maxLines = maxLines,
                singleLine = singleLine,
                cursorBrush = SolidColor(GwangSanColor.subYellow500),
                modifier = modifier
                    .focusable(true)
                    .fillMaxWidth()
                    .onFocusChanged { isFocused.value = it.isFocused }
                    .border(
                        width = 1.dp,
                        color = when {
                            isError -> GwangSanColor.error
                            isFocused.value -> GwangSanColor.subYellow500
                            (value ?: "").isNotEmpty() -> GwangSanColor.subYellow500
                            else -> GwangSanColor.gray400
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(16.dp),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(Modifier.weight(1f)) {
                            if ((value ?: "").isEmpty()) {
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
                Text(
                    text = errorText,
                    color = GwangSanColor.error,
                    style = typography.label
                )
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
                    onValueChange(it)
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
                textStyle = typography.body5,
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

@Composable
fun ChatInputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onImageClick: () -> Unit = {},
    enabled: Boolean = true
) {
    GwangSanTheme { colors, typography ->
        Row(
            modifier = modifier
                .background(
                    color = if (enabled) colors.gray200 else colors.gray100,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                singleLine = true,
                cursorBrush = SolidColor(colors.main500),
                textStyle = typography.label.copy(color = colors.black),
                decorationBox = { innerTextField ->
                    Box {
                        if (value.isBlank()) {
                            Text(
                                text = "채팅을 입력해주세요",
                                style = typography.label,
                                color = colors.gray400
                            )
                        }
                        innerTextField()
                    }
                }
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_image),
                contentDescription = "이미지 첨부",
                modifier = Modifier
                    .size(24.dp)
                    .then(
                        if (enabled) {
                            Modifier.GwangSanClickable { onImageClick() }
                        } else {
                            Modifier
                        }
                    ),
                tint = if (enabled) colors.black else colors.gray400
            )
        }
    }
}

@Composable
fun GwangSanSelectTextField(
    modifier: Modifier = Modifier,
    value: String,
    onTextChange: (String) -> Unit,
    label: String,
    placeHolder: String,
    onClick: () -> Unit,
    isError: Boolean = false,
    errorText: String = "",
    isDisabled: Boolean = false,
    readOnly: Boolean = false
) {
    GwangSanTheme { colors, typography ->
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                color = if (isError) GwangSanColor.error else GwangSanColor.black,
                style = typography.body5
            )

            val shape = RoundedCornerShape(8.dp)
            val borderColor = when {
                isError -> GwangSanColor.error
                value.isNotEmpty() -> GwangSanColor.subYellow500
                else -> GwangSanColor.gray400
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(shape)
                    .background(colors.white)
                    .border(1.dp, borderColor, shape)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .GwangSanClickable(enabled = !isDisabled) {
                            onClick()
                        }
                ) {
                    PlusIcon()
                }

                Spacer(modifier = Modifier.width(8.dp))

                TextField(
                    value = value,
                    onValueChange = { if (!isDisabled && !readOnly) onTextChange(it) },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    placeholder = {
                        Text(
                            text = placeHolder,
                            style = typography.body5,
                            color = GwangSanColor.gray400
                        )
                    },
                    singleLine = true,
                    readOnly = readOnly,
                    enabled = !isDisabled,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = GwangSanColor.black,
                        unfocusedTextColor = GwangSanColor.black,
                        disabledTextColor = GwangSanColor.gray400,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        cursorColor = GwangSanColor.subYellow500,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        focusedPlaceholderColor = GwangSanColor.gray400,
                        unfocusedPlaceholderColor = GwangSanColor.gray400
                    )
                )
            }

            if (isError) {
                Text(
                    text = errorText,
                    color = GwangSanColor.error,
                    style = typography.label
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GwangSanTextFieldPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        var defaultText by remember { mutableStateOf("") }
        GwangSanTextField(
            label = "이름",
            value = defaultText,
            onTextChange = { defaultText = it },
            placeHolder = "이름을 입력하세요"
        )

        var errorText by remember { mutableStateOf("") }
        GwangSanTextField(
            label = "이메일",
            value = errorText,
            onTextChange = { errorText = it },
            placeHolder = "이메일을 입력하세요",
            isError = true,
            errorText = "유효하지 않은 이메일입니다"
        )

        var focusText by remember { mutableStateOf("010-1234-5678") }
        GwangSanTextField(
            label = "전화번호",
            value = focusText,
            onTextChange = { focusText = it },
            placeHolder = "전화번호를 입력하세요"
        )
    }
}