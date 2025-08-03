package com.school_of_company.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.icons.EllipseIcon
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.post.response.Member

@Composable
internal fun MyProfileUserLevel(
    modifier: Modifier = Modifier,
    data: Member,
    onClick: (Long) -> Unit,
) {
    GwangSanTheme { colors, typography ->

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .background(colors.white)
                .padding(vertical = 8.dp)
        ) {
            Box(modifier = Modifier.size(40.dp)) {
                EllipseIcon(
                    modifier = Modifier.GwangSanClickable {onClick(data.memberId) },
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = data.nickname,
                    color = colors.black,
                    style = typography.body5,
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = data.placeName,
                    color = colors.gray500,
                    style = typography.body5
                )
            }

            Text(
                text = "${data.light}단계",
                color = colors.subYellow500,
                style = typography.body5,
            )
        }
    }
}