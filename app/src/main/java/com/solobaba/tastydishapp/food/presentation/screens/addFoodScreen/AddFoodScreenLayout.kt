package com.solobaba.tastydishapp.food.presentation.screens.addFoodScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.solobaba.tastydishapp.R
import com.solobaba.tastydishapp.food.presentation.components.AppBarComponent
import com.solobaba.tastydishapp.food.presentation.components.ImageComponent
import com.solobaba.tastydishapp.food.presentation.components.TagsComponent
import com.solobaba.tastydishapp.food.presentation.components.UploadFoodComponent
import com.solobaba.tastydishapp.food.presentation.state.AddFoodScreenLayoutActions
import com.solobaba.tastydishapp.food.presentation.state.AddFoodUIState
import com.solobaba.tastydishapp.ui.theme.Black1
import com.solobaba.tastydishapp.ui.theme.Blue2
import com.solobaba.tastydishapp.ui.theme.DefaultIconColor
import com.solobaba.tastydishapp.ui.theme.White
import com.solobaba.tastydishapp.util.MulishRegular
import com.solobaba.tastydishapp.util.SpacerVertical10Dp
import com.solobaba.tastydishapp.util.SpacerVertical3Dp
import com.solobaba.tastydishapp.util.SpacerVertical5Dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFoodScreenLayout(
    navController: NavController,
    addFoodButtonState: Boolean,
    uiFoodState: AddFoodUIState,
    onAction: (AddFoodScreenLayoutActions) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.add_new_food),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Black1,
                    fontFamily = FontFamily(Font(R.font.mulish_regular)),
                    fontSize = 16.sp
                )
            },
            navigationIcon = {
                AppBarComponent(
                    icon = R.drawable.ic_arrow_square_back,
                    onClick = { navController.navigateUp() },
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = White),
        )

        SpacerVertical5Dp()

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            SpacerVertical10Dp()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                UploadFoodComponent(
                    image = R.drawable.ic_camera,
                    text = stringResource(R.string.take_photo),
                    modifier = Modifier
                        .weight(1F)
                        .padding(start = 16.dp),
                    onClick = { onAction(AddFoodScreenLayoutActions.OnTakePhotoClick) }
                )
                UploadFoodComponent(
                    image = R.drawable.ic_upload,
                    text = stringResource(R.string.upload),
                    modifier = Modifier
                        .weight(1F)
                        .padding(end = 16.dp),
                    onClick = { onAction(AddFoodScreenLayoutActions.OnUploadClick) }
                )
            }

            SpacerVertical10Dp()

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = uiFoodState.imageUris, key = { it }) { uri ->
                    ImageComponent(modifier = Modifier.animateItem(), uri = uri, onDeleteClick = {
                        onAction(AddFoodScreenLayoutActions.OnDeleteImage(uri))
                    })
                }
            }

            SpacerVertical10Dp()

            Text(
                text = stringResource(R.string.name),
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = MulishRegular,
                    color = Color.Black
                )
            )
            SpacerVertical3Dp()
            OutlinedTextField(
                value = uiFoodState.name,
                onValueChange = { onAction(AddFoodScreenLayoutActions.OnNameChange(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholder = {
                    Text(
                        text = stringResource(R.string.enter_food_name),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = FontFamily(Font(R.font.mulish_regular)),
                            color = Color.Gray
                        )
                    )
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily(Font(R.font.mulish_regular)),
                    color = Color.Black
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = Color.DarkGray.copy(0.5f),
                    cursorColor = Color.DarkGray
                )
            )

            SpacerVertical10Dp()
            Text(
                text = stringResource(R.string.description),
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium.copy(),
                fontFamily = FontFamily(Font(R.font.mulish_regular)),
                color = Color.Black
            )
            SpacerVertical3Dp()
            OutlinedTextField(
                value = uiFoodState.description,
                onValueChange = { onAction(AddFoodScreenLayoutActions.OnDescriptionChange(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholder = {
                    Text(
                        text = stringResource(R.string.enter_food_description),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = FontFamily(Font(R.font.mulish_regular)),
                            color = Color.Gray
                        )
                    )
                },
                minLines = 3,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily(Font(R.font.mulish_regular)),
                    color = Color.Black
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = Color.DarkGray.copy(0.5f),
                    cursorColor = Color.DarkGray
                )
            )
            SpacerVertical10Dp()
            Text(
                text = stringResource(R.string.category),
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily(Font(R.font.mulish_regular)),
                color = Color.Black
            )
            SpacerVertical3Dp()
            var expandedCategory by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedCategory,
                onExpandedChange = { expandedCategory = it }) {
                OutlinedTextField(
                    value = uiFoodState.category?.name.orEmpty(),
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .menuAnchor(),
                    placeholder = {
                        Text(
                            text = stringResource(R.string.select_a_category),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = FontFamily(Font(R.font.mulish_regular)),
                                color = Color.Gray
                            )
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_caret_down),
                            contentDescription = "Drop down icon"
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily(Font(R.font.mulish_regular)),
                        color = Color.Black
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = Color.DarkGray.copy(0.5f),
                        cursorColor = Color.DarkGray
                    ),
                    readOnly = true
                )
                ExposedDropdownMenu(
                    expanded = expandedCategory,
                    onDismissRequest = { expandedCategory = false }) {
                    uiFoodState.categories.forEach {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = it.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontFamily = FontFamily(Font(R.font.mulish_regular)),
                                    color = Color.White
                                )
                            },
                            onClick = {
                                onAction(AddFoodScreenLayoutActions.OnCategorySelected(it))
                                expandedCategory = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }

            SpacerVertical10Dp()

            Text(
                text = stringResource(R.string.calories),
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily(Font(R.font.mulish_regular)),
                color = Color.Black
            )
            SpacerVertical3Dp()
            OutlinedTextField(
                value = uiFoodState.calories,
                onValueChange = { onAction(AddFoodScreenLayoutActions.OnCaloriesChange(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholder = {
                    Text(
                        text = stringResource(R.string.enter_number_of_calories),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = FontFamily(Font(R.font.mulish_regular)),
                            color = Color.Gray
                        )
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily(Font(R.font.mulish_regular)),
                    color = Color.Black
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = Color.DarkGray.copy(0.5f),
                    cursorColor = Color.DarkGray
                )
            )

            SpacerVertical10Dp()

            Text(
                text = stringResource(R.string.tags),
                modifier = Modifier.padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily(Font(R.font.mulish_regular)),
                color = Color.Black
            )
            SpacerVertical3Dp()
            var expandedTag by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedTag,
                onExpandedChange = { expandedTag = it }) {
                TagsComponent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .menuAnchor(),
                    tags = uiFoodState.selectedTags.toList(),
                    onDelete = { onAction(AddFoodScreenLayoutActions.OnDeleteTag(it)) }
                )
                ExposedDropdownMenu(
                    expanded = expandedTag,
                    onDismissRequest = { expandedTag = false }) {
                    uiFoodState.tags?.data?.forEach { tags ->
                        DropdownMenuItem(text = {
                            Text(
                                text = tags.name,
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily(Font(R.font.mulish_regular)),
                                color = Color.White
                            )
                        }, onClick = {
                            expandedTag = false
                            onAction(AddFoodScreenLayoutActions.OnTagSelected(tags))
                        })
                    }
                }
            }
            Spacer(Modifier.height(30.dp))
        }

        ElevatedButton(
            onClick = { onAction(AddFoodScreenLayoutActions.OnCreateClick) },
            shape = RoundedCornerShape(4.dp),
            enabled = addFoodButtonState,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = Blue2
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(52.dp)
        ) {
            Text(
                text = stringResource(R.string.add_food),
                fontFamily = FontFamily(Font(R.font.mulish_bold)),
                color = if (true) MaterialTheme.colorScheme.onPrimary else DefaultIconColor
            )
        }
    }
}