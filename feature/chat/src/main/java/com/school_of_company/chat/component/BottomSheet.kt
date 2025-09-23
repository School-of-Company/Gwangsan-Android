package com.school_of_company.chat.component



import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.school_of_company.chat.ui.model.ChatMessageUi
import com.school_of_company.chat.ui.model.TradeProductUi
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.theme.GwangSanTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TradeRequestBottomSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onRequestClick: (Long, Long) -> Unit, // (productId, otherMemberId)
    data: TradeProductUi,
    message: ChatMessageUi,
    onCancelClick: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState()
) {
    GwangSanTheme { colors, typography ->
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = colors.white,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "거래 요청하기",
                    style = typography.body1,
                    color = colors.gray900,
                    modifier = Modifier
                        .GwangSanClickable {
                            onRequestClick(data.id, message.senderId)
                        }
                        .padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "취소",
                    style = typography.body3,
                    color = colors.error,
                    modifier = Modifier.GwangSanClickable { onCancelClick() }
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
