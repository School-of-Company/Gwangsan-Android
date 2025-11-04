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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.school_of_company.design_system.component.button.GwangSanStateButton
import com.school_of_company.design_system.component.button.state.ButtonState
import com.school_of_company.design_system.component.icons.PlussIcon
import com.school_of_company.design_system.component.textfield.GwangSanTextField
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.component.toast.makeToast
import com.school_of_company.post.viewmodel.PostViewModel
import com.school_of_company.ui.previews.GwangsanPreviews
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.school_of_company.post.viewmodel.uiState.ImageUpLoadUiState

@Composable
internal fun PostInputRoute(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    viewModel: PostViewModel? = null,
) {
    val actualViewModel = viewModel ?: hiltViewModel()
    val gwangsan by actualViewModel.gwangsan.collectAsState()

    val existingImageUrls by actualViewModel.existingImageUrls.collectAsState()

    val selectedImages by actualViewModel.selectedImages.collectAsState()

    val imageUpLoadUiState by actualViewModel.imageUpLoadUiState.collectAsStateWithLifecycle()

    val selectedImageUris = selectedImages.map { it.toString() }.toPersistentList()

    val uploadedUris = remember { mutableStateListOf<String>() }

    val context = LocalContext.current

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                val currentTotal = existingImageUrls.size + selectedImages.size
                if (currentTotal < 5) {
                    actualViewModel.addImage(uri)
                } else {
                    makeToast(context, "이미지는 5장까지 선택할 수 있습니다.")
                }
            }
        }

    LaunchedEffect(selectedImageUris, existingImageUrls) {
        val totalAllow = 5 - existingImageUrls.size
        if (totalAllow <= 0) return@LaunchedEffect

        var remainingSlots = totalAllow
        for (uriString in selectedImageUris) {
            if (remainingSlots <= 0) break
            if (!uploadedUris.contains(uriString)) {
                try {
                    val uri = Uri.parse(uriString)
                    val imageId = actualViewModel.imageUpLoad(context, uri)
                    actualViewModel.onImageIdAdded(imageId)
                    uploadedUris.add(uriString)
                    remainingSlots--
                } catch (_: Exception) {
                    makeToast(context, "이미지 업로드에 실패했습니다.")
                }
            }
        }
    }

    PostInputScreen(
        value = gwangsan,
        onValueChange = actualViewModel::onGwangsanChange,
        onNextClick = onNextClick,
        onBackClick = onBackClick,
        imageUri = selectedImageUris,
        existingImageUrls = existingImageUrls.toPersistentList(),
        onImageRemove = { index -> actualViewModel.removeNewImage(index) },
        onExistingImageRemove = { index -> actualViewModel.removeExistingImage(index) },
        onImageAdd = { galleryLauncher.launch("image/*") },
        imageUpLoadUiState = imageUpLoadUiState
    )
}

@Composable
private fun PostInputScreen(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
    imageUri: PersistentList<String>,
    existingImageUrls: PersistentList<String>,
    onImageRemove: (Int) -> Unit,
    onExistingImageRemove: (Int) -> Unit,
    onImageAdd: () -> Unit,
    imageUpLoadUiState: ImageUpLoadUiState
) {
    val focusManager = LocalFocusManager.current

    val totalImages = existingImageUrls.size + imageUri.size
    val canAddMore = totalImages < 5
    val hasAnyImage = totalImages > 0

    GwangSanTheme { colors, typography ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(colors.white)
                .padding(horizontal = 24.dp)
                .pointerInput(Unit) { detectTapGestures { focusManager.clearFocus() } }
        ) {
            Spacer(Modifier.height(28.dp))

            Text(text = "사진첨부", style = typography.body5, color = colors.black)
            Spacer(Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(existingImageUrls) { index, imageUrl ->
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                    ) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "기존 이미지",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .matchParentSize()
                                .GwangSanClickable { onExistingImageRemove(index) }
                        )
                    }
                }

                itemsIndexed(imageUri) { index, imageUriStr ->
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                    ) {
                        AsyncImage(
                            model = imageUriStr,
                            contentDescription = "선택된 이미지",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .matchParentSize()
                                .GwangSanClickable { onImageRemove(index) }
                        )
                    }
                }

                if (canAddMore) {
                    item {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF5F6F8))
                                .GwangSanClickable { onImageAdd() }
                        ) {
                            PlussIcon(tint = colors.black, modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            GwangSanTextField(
                value = value,
                onTextChange = { input ->
                    if (input.isEmpty() || input.all { it in '0'..'9' }) onValueChange(input)
                },
                label = "광산",
                placeHolder = "광산을 입력해주세요(ex: 1000 숫자로만)",
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f, fill = true))

            val canProceed = when {
                value.isBlank() -> false
                !hasAnyImage -> false
                imageUri.isEmpty() -> true
                imageUpLoadUiState is ImageUpLoadUiState.Success -> true
                else -> false
            }

            GwangSanStateButton(
                text = "다음",
                state = if (canProceed) ButtonState.Enable else ButtonState.Disable,
                onClick = onNextClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

