package com.school_of_company.content.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.button.GwangSanEnableButton
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.content.component.RatingSlider
import com.yourpackage.design_system.component.textField.GwangSanTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ReviewBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onSubmit: (Int, String) -> Unit = { _, _ -> },
    sheetState: SheetState = rememberModalBottomSheetState()
) {
    GwangSanTheme { colors, typography ->

        var rating by remember { mutableIntStateOf(1) }
        var reviewText by remember { mutableStateOf("") }
        val isButtonEnabled = reviewText.isNotBlank()

        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = sheetState,
            containerColor = colors.white,
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp
            ),
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 16.dp
                    )
            ) {
                GwangSanSubTopBar(
                    startIcon = { Box(modifier = Modifier.size(24.dp)) },
                    betweenText = "후기",
                    endIcon = { CloseIcon(modifier = Modifier.GwangSanClickable { onDismiss() }) }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "밝기",
                    style = typography.label,
                    color = colors.gray800
                )

                Spacer(modifier = Modifier.height(8.dp))

                RatingSlider(
                    value = rating,
                    onValueChange = { rating = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                GwangSanTextField(
                    label = "후기작성",
                    value = reviewText,
                    onTextChange = { reviewText = it },
                    placeHolder = "후기작성",
                    isError = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(185.dp)
                )

                Spacer(modifier = Modifier.height(81.dp))

                GwangSanEnableButton(
                    text = "작성완료",
                    onClick = {
                        if (isButtonEnabled) onSubmit(rating, reviewText)
                    },
                    backgroundColor = if (isButtonEnabled) colors.main500 else colors.gray300,
                    textColor = colors.white,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewReviewBottomSheet() {
    ReviewBottomSheet()
}
