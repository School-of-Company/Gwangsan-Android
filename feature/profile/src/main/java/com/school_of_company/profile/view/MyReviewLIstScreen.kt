import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.school_of_company.design_system.componet.clickable.GwangSanClickable
import com.school_of_company.design_system.componet.icons.CloseIcon
import com.school_of_company.design_system.componet.icons.DownArrowIcon
import com.school_of_company.design_system.componet.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.profile.component.MyProfileReviewListItem
import com.school_of_company.profile.component.Review

@Composable
internal fun MyReviewRoute(
    onBackClick: () -> Unit,
) {
    MyReviewScreen(
        onBackClick = onBackClick,
        item = listOf(),
    )
}
@Composable
private fun MyReviewScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    item: List<Review>
) {
    GwangSanTheme { colors, _ ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp)
                .background(color = colors.white)

        ) {
            GwangSanSubTopBar(
                startIcon = { DownArrowIcon(modifier = Modifier.GwangSanClickable { onBackClick() }) },
                betweenText = "내가 작성한 후기",
                modifier = Modifier.padding(24.dp),
            )

            Spacer(modifier = Modifier.height(12.dp))

            MyReviewProfileList(
                items = item,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun MyReviewProfileList(
    items: List<Review>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { review ->
            MyProfileReviewListItem(
                data = review, // ✅ 수정
            )
        }
    }
}
