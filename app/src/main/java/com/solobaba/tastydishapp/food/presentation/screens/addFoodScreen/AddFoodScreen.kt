package com.solobaba.tastydishapp.food.presentation.screens.addFoodScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.solobaba.tastydishapp.food.presentation.state.AddFoodScreenLayoutActions
import com.solobaba.tastydishapp.food.presentation.viewmodel.AddFoodViewModel
import com.solobaba.tastydishapp.util.createImageFile
import com.solobaba.tastydishapp.util.shortToast

@Composable
fun AddFoodScreen(navController: NavController) {
    val viewModel = hiltViewModel<AddFoodViewModel>()
    val uiFoodState = viewModel.uiAddFoodState.collectAsState().value
    val addFoodButtonState = viewModel.addFoodButtonState.collectAsState().value
    val context = LocalContext.current
    var addPhotoUri by remember { mutableStateOf<Uri?>(null) }

    val uploadImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia()
    ) { uris ->
        if (uris.isNotEmpty()) viewModel.setImageUris(uris) else context.shortToast("No images selected")
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                addPhotoUri?.let { uri ->
                    viewModel.setImageUris(listOf(uri))
                }
            } else {
                context.shortToast("Failed to capture photo!")
            }
        }
    )

    if (uiFoodState.isLoading) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(modifier = Modifier.size(50.dp))
            }
        }
    }

    AddFoodScreenLayout(
        navController = navController,
        addFoodButtonState = addFoodButtonState,
        uiFoodState = uiFoodState,
        onAction = {
            when (it) {
                AddFoodScreenLayoutActions.OnBackClick -> {
                    navController.navigateUp()
                }
                is AddFoodScreenLayoutActions.OnCaloriesChange -> {
                    viewModel.setCalories(it.calories)
                }
                is AddFoodScreenLayoutActions.OnCategorySelected -> {
                    viewModel.setCategory(it.category)
                }
                AddFoodScreenLayoutActions.OnCreateClick -> {
                    viewModel.addFood()
                }
                is AddFoodScreenLayoutActions.OnDeleteImage -> {
                    viewModel.deleteImageUri(it.uri)
                }
                is AddFoodScreenLayoutActions.OnDeleteTag -> {
                    viewModel.deleteTag(it.tag)
                }
                AddFoodScreenLayoutActions.OnTakePhotoClick -> {
                    val file = createImageFile(context)
                    addPhotoUri = FileProvider.getUriForFile(
                        context, "${context.packageName}.fileprovider", file
                    )
                    addPhotoUri?.let { uri -> cameraLauncher.launch(uri) }
                }
                is AddFoodScreenLayoutActions.OnDescriptionChange -> {
                    viewModel.setDescription(it.description)
                }
                is AddFoodScreenLayoutActions.OnNameChange -> {
                    viewModel.setName(it.name)
                }
                AddFoodScreenLayoutActions.OnUploadClick -> {
                    uploadImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
                is AddFoodScreenLayoutActions.OnTagSelected -> {
                    viewModel.setTag(it.tag)
                }
            }
        })
}