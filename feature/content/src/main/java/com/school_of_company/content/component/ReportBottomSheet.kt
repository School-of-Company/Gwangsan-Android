package com.school_of_company.content.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.school_of_company.content.viewmodel.ContentViewModel
import com.school_of_company.design_system.component.button.GwangSanEnableButton
import com.school_of_company.design_system.component.clickable.GwangSanClickable
import com.school_of_company.design_system.component.icons.CloseIcon
import com.school_of_company.design_system.component.icons.DropDownIcon
import com.school_of_company.design_system.component.icons.PlussIcon
import com.school_of_company.design_system.component.topbar.GwangSanSubTopBar
import com.school_of_company.design_system.theme.GwangSanTheme
import com.school_of_company.design_system.theme.color.GwangSanColor
import com.school_of_company.model.enum.ReportType
import com.school_of_company.design_system.component.textfield.GwangSanTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportBottomSheet(
    modifier: Modifier = Modifier,
    initialSelectedOption: String = "",
    initialReportContent: String = "",
    onDismiss: () -> Unit = {},
    onSubmit: (String, String) -> Unit = { _, _ -> },
    sheetState: SheetState = rememberModalBottomSheetState(),
    viewModel: ContentViewModel = hiltViewModel()
) {
    val selectedImageUris by viewModel.selectedImages.collectAsStateWithLifecycle()
    val existingImageUrls by viewModel.existingImageUrls.collectAsStateWithLifecycle()
    val uploadedUris = remember { mutableStateListOf<Uri>() }

    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.addImage(it) }
    }

    LaunchedEffect(selectedImageUris) {
        selectedImageUris.forEach { uri ->
            if (!uploadedUris.contains(uri)) {
                try {
                    val imageId = viewModel.imageUpLoad(context, uri)
                    viewModel.onImageIdAdded(imageId)
                    uploadedUris.add(uri)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    var expanded by remember { mutableStateOf(false) }
    var selectedReportType by remember {
        mutableStateOf(ReportType.values().find { it.name == initialSelectedOption })
    }
    var reportContent by remember { mutableStateOf(initialReportContent) }

    val isButtonEnabled = selectedReportType != null && reportContent.isNotBlank()

    GwangSanTheme { colors, typography ->

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
                        icon = {
                            DropDownIcon(
                                modifier = Modifier.GwangSanClickable { expanded = true },
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
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

                Spacer(modifier = Modifier.height(12.dp))

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
                                    viewModel.removeExistingImage(index)
                                }
                        )
                    }

                    itemsIndexed(selectedImageUris) { index, imageUri ->
                        AsyncImage(
                            model = imageUri,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(60.dp)
                                .GwangSanClickable {
                                    viewModel.removeNewImage(index)
                                }
                        )
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFF5F6F8))
                                .GwangSanClickable { galleryLauncher.launch("image/*") }
                        ) {
                            PlussIcon(
                                tint = colors.black,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }

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