package com.yourpackage.design_system.component.textField

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.color.GwangSanColor

@Composable
fun GwangSanTextField(
    modifier: Modifier = Modifier,
    value: String? = null,
    label: String,
    placeHolder: String,
    errorText: String = "",
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onTextChange: (String) -> Unit,
    icon: @Composable () -> Unit = {}
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