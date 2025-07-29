package com.school_of_company.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.member.response.GetAllMemberResponseModel
import com.school_of_company.profile.ui.model.GetAllMemberResponseUi

@Composable
internal fun OtherPersonIntroduce(
    modifier: Modifier = Modifier,
    data: GetAllMemberResponseUi
) {
    GwangSanTheme { colors, typography ->

        Column(modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp)
        ) {
            Text(
                text = "소개",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colors.black,
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                data.specialties.forEach { specialty ->
                    Text(
                        text = specialty,
                        style = typography.body5,
                        color = colors.gray600,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .border(1.dp, colors.gray600, RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .padding(
                                horizontal = 12.dp,
                                vertical = 6.dp
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = data.description,
                style = typography.body5,
                color = colors.black
            )
        }
    }
}




