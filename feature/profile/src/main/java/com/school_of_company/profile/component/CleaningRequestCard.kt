package com.school_of_company.profile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.post.response.Post

@Composable
internal fun CleaningRequestCard(
    modifier: Modifier = Modifier,
    data: Post
) {
    GwangSanTheme { colors, typography ->

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = data.title,
                style = typography.titleSmall,
                color = colors.black
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "${data.gwangsan}",
                style = typography.titleSmall,
                color = colors.black
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = data.content,
                style = typography.body4
            )
        }
    }
}