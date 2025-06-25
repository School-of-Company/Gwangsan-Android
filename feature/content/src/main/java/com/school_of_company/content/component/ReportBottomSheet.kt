package com.school_of_company.content.view

import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.button.GwangSanEnableButton
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.icons.DropDownIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.color.GwangSanColor
import com.yourpackage.design_system.component.textField.GwangSanTextField

@Composable
internal fun ReportBottomSheet(
    modifier: Modifier = Modifier,
    initialSelectedOption: String = "",
    initialReportContent: String = "",
    onDismiss: () -> Unit = {},
    onSubmit: (String, String) -> Unit = { _, _ -> }
) {
    GwangSanTheme { colors, typography ->
        var expanded by remember { mutableStateOf(false) }
        var selectedOption by remember { mutableStateOf(initialSelectedOption) }
        var reportContent by remember { mutableStateOf(initialReportContent) }

        val isButtonEnabled = selectedOption.isNotBlank() && reportContent.isNotBlank()

        Surface(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            color = colors.white
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                GwangSanSubTopBar(
                    startIcon = { Box(modifier = Modifier.size(24.dp)) },
                    betweenText = "신고하기",
                    endIcon = { CloseIcon(modifier = Modifier.GwangSanClickable { onDismiss() }) }
                )

                Spacer(Modifier.height(24.dp))

                Box {
                    GwangSanTextField(
                        value = selectedOption,
                        label = "신고유형",
                        placeHolder = "신고유형을 선택해주세요",
                        onTextChange = {},
                        isReadOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .GwangSanClickable { expanded = true },
                        icon = { DropDownIcon() }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(GwangSanColor.white)
                    ) {
                        listOf("허위매물등록", "욕설/비방", "사기", "기타").forEach {
                            DropdownMenuItem(
                                text = { Text(it) },
                                onClick = {
                                    selectedOption = it
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                GwangSanTextField(
                    label = "신고사유",
                    value = reportContent,
                    onTextChange = { reportContent = it },
                    placeHolder = "신고사유를 입력해주세요",
                    isError = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(185.dp)
                )

                Spacer(Modifier.height(24.dp))

                GwangSanEnableButton(
                    text = "신고하기",
                    onClick = {
                        if (isButtonEnabled) {
                            onSubmit(selectedOption, reportContent)
                        }
                    },
                    backgroundColor = if (isButtonEnabled) colors.error else colors.gray300,
                    textColor = colors.white,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "신고 활성화 상태")
@Composable
private fun PreviewReportBottomSheetEnabled() {
    ReportBottomSheet(
        initialSelectedOption = "사기",
        initialReportContent = "거짓 정보를 올렸어요"
    )
}