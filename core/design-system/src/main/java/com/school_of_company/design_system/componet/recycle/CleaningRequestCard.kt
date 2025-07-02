package com.school_of_company.design_system.componet.recycle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.GwangSanTheme

@Composable
fun CleaningRequestCard(
    modifier: Modifier = Modifier,
    title: String,
    priceAndLocation: String,
    description: String,
) {
    GwangSanTheme { colors, typography ->

        Column(modifier = modifier.fillMaxWidth()) {
            Text(
                text = title,
                style = typography.titleSmall,
                color = colors.black
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = priceAndLocation,
                style = typography.body3,
                color = colors.black
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = description,
                style = typography.body4
            )
        }
    }
}