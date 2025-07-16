package com.school_of_company.content.component

import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.button.GwangSanEnableButton
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.icons.DropDownIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.color.GwangSanColor
import com.school_of_company.model.enum.ReportType
import com.yourpackage.design_system.component.textField.GwangSanTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ReportBottomSheet(
    modifier: Modifier = Modifier,
    initialSelectedOption: String = "",
    initialReportContent: String = "",
    onDismiss: () -> Unit = {},
    onSubmit: (String, String) -> Unit = { _, _ -> },
    sheetState: SheetState = rememberModalBottomSheetState(),
) {
    GwangSanTheme { colors, _ ->

        var expanded by remember { mutableStateOf(false) }
        var selectedReportType by remember {
            mutableStateOf(
                ReportType.values().find { it.name == initialSelectedOption }
            )
        }
        var reportContent by remember { mutableStateOf(initialReportContent) }

        val isButtonEnabled = selectedReportType != null && reportContent.isNotBlank()

        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = sheetState,
            containerColor = colors.white,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        ) {
            Column(
                modifier = modifier
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                GwangSanSubTopBar(
                    startIcon = { Box(modifier = Modifier.size(24.dp)) },
                    betweenText = "신고하기",
                    endIcon = { CloseIcon(modifier = Modifier.GwangSanClickable { onDismiss() }) }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    GwangSanTextField(
                        value = selectedReportType?.value ?: "",
                        label = "신고유형",
                        placeHolder = "신고유형을 선택해주세요",
                        onTextChange = {},
                        isReadOnly = true,
                        icon = { DropDownIcon() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .GwangSanClickable { expanded = true },
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .background(GwangSanColor.white)
                    ) {
                        ReportType.entries.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type.value) },
                                onClick = {
                                    selectedReportType = type
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

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

                Spacer(modifier = Modifier.height(81.dp))

                GwangSanEnableButton(
                    text = "신고하기",
                    onClick = {
                        selectedReportType?.let { type ->
                            onSubmit(type.name, reportContent)
                        }
                    },
                    backgroundColor = if (isButtonEnabled) colors.error else colors.gray300,
                    textColor = colors.white,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "신고 활성화 상태")
@Composable
private fun PreviewReportBottomSheetEnabled() {
    ReportBottomSheet(
        initialSelectedOption = "사기",
        initialReportContent = "거짓 정보를 올렸어요"
    )
}