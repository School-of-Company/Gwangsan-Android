package com.school_of_company.post.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.school_of_company.design_system.component.button.GwangSanStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.icons.PlussIcon
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.model.enum.Mode
import com.school_of_company.model.enum.Type
import com.school_of_company.post.viewmodel.PostViewModel
import com.school_of_company.ui.previews.GwangsanPreviews
import com.yourpackage.design_system.component.textField.GwangSanTextField

@Composable
internal fun PostWriteRoute(
    type: Type,
    mode: Mode,
    onBackClick: () -> Unit,
    onNextClick: (String, String) -> Unit,
    viewModel: PostViewModel? = null,
) {
    val actualViewModel = viewModel ?: hiltViewModel()
    val subject by actualViewModel.title.collectAsState()
    val content by actualViewModel.content.collectAsState()

    val selectedImageUris by actualViewModel.selectedImages.collectAsStateWithLifecycle()
    val existingImageUrls by actualViewModel.existingImageUrls.collectAsState()

    val uploadedUris = remember { mutableStateListOf<Uri>() }

    val context = LocalContext.current

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                actualViewModel.addImage(uri)
            }
        }

    LaunchedEffect(selectedImageUris) {
        selectedImageUris.forEach { uri ->
            if (uploadedUris.contains(uri).not()) {
                try {
                    val imageId = actualViewModel.imageUpLoad(context, uri)
                    actualViewModel.onImageIdAdded(imageId)
                    uploadedUris.add(uri)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    PostWriteScreen(
        subject = subject,
        content = content,
        imageUri = selectedImageUris,
        existingImageUrls = existingImageUrls,
        onImageRemove = { index ->
            actualViewModel.removeNewImage(index)
        },
        onExistingImageRemove = { index ->
            actualViewModel.removeExistingImage(index)
        },
        onSubjectChange = actualViewModel::onTitleChange,
        onContentChange = actualViewModel::onContentChange,
        onImageAdd = { galleryLauncher.launch("image/*") },
        onNextClick = onNextClick,
        onBackClick = onBackClick,
    )
}

@Composable
private fun PostWriteScreen(
    modifier: Modifier = Modifier,
    subject: String,
    content: String,
    imageUri: List<Uri>,
    existingImageUrls: List<String>,
    onImageRemove: (Int) -> Unit,
    onExistingImageRemove: (Int) -> Unit,
    onSubjectChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onImageAdd: () -> Unit,
    onNextClick: (String, String) -> Unit,
    onBackClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val isNextEnabled = subject.isNotBlank() && content.isNotBlank()

    GwangSanTheme { colors, typography ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
                .padding(horizontal = 24.dp)
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
        ) {
            GwangSanTextField(
                value = subject,
                onTextChange = onSubjectChange,
                label = "주제",
                placeHolder = "주제를 작성해주세요"
            )

            Spacer(modifier = Modifier.height(28.dp))

            GwangSanTextField(
                value = content,
                onTextChange = onContentChange,
                label = "내용",
                placeHolder = "내용을 작성해주세요",
                singleLine = false,
                maxLines = Int.MAX_VALUE,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(185.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "사진첨부",
                style = typography.body5,
                color = colors.black
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                itemsIndexed(existingImageUrls) { index, imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(60.dp)
                            .GwangSanClickable {
                                onExistingImageRemove(index)
                            }
                    )
                }

                itemsIndexed(imageUri) { index, imageUri ->
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(60.dp)
                            .GwangSanClickable { onImageRemove(index) }
                    )
                }

                item {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF5F6F8))
                            .GwangSanClickable { onImageAdd() }
                    ) {
                        PlussIcon(
                            tint = colors.black,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.weight(1f, fill = true))

            GwangSanStateButton(
                text = "다음",
                state = if (isNextEnabled) ButtonState.Enable else ButtonState.Disable,
                onClick = { onNextClick(subject, content) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Composable
private fun ImageItem(
    uri: Uri,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(60.dp)
    ) {
        AsyncImage(
            model = uri,
            contentDescription = "선택된 이미지",
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .size(50.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@GwangsanPreviews
@Composable
private fun PostWritePreview() {
    PostWriteScreen(
        subject = "예시 주제",
        content = "예시 내용입니다.\n여러 줄로 작성된 내용도 미리보기 됩니다.",
        onSubjectChange = {},
        onContentChange = {},
        onImageAdd = {},
        onNextClick = { _, _ -> },
        onBackClick = {},
        imageUri = listOf(),
        onImageRemove = {},
        existingImageUrls = listOf(),
        onExistingImageRemove = {}
    )
}
