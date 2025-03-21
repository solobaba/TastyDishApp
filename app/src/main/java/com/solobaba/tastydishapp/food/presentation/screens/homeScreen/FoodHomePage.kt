package com.solobaba.tastydishapp.food.presentation.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.solobaba.tastydishapp.R
import com.solobaba.tastydishapp.food.domain.model.response.DomainCategory
import com.solobaba.tastydishapp.food.presentation.state.FoodDishState
import com.solobaba.tastydishapp.food.presentation.viewmodel.FoodDishViewModel
import com.solobaba.tastydishapp.util.SpacerVertical20Dp
import com.solobaba.tastydishapp.util.shortToast
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun FoodHomePage(navController: NavController, onFoodCategoryClicked: (DomainCategory) -> Unit) {
    val viewmodel = hiltViewModel<FoodDishViewModel>()
    val uiFoodState by viewmodel.uiFoodState.collectAsState()
    val error = viewmodel.error.observeAsState().value
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        UsersProfile()
        SpacerVertical20Dp()
        SearchBar()
        SpacerVertical20Dp()

        //Food categories
        val _foodUIState = MutableStateFlow(FoodDishState())
        //val foodState = _foodUIState.asStateFlow()
        //ChipGroup(chipItems = foodState.collectAsState().value.categories, onItemClick = { })

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            //contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                FoodCategoriesChip(chipItems = uiFoodState.categories, onItemClick = {
                    viewmodel.setFoodCategory(it)
                })
                error?.let { context.shortToast(it) }
                SpacerVertical20Dp()
            }
            item(span = { GridItemSpan(2) }) {
                Text(
                    text = if (uiFoodState.currentCategory?.name?.isNotEmpty() == true) {
                        uiFoodState.currentCategory?.name.orEmpty()
                    } else {
                        stringResource(R.string.all_food)
                    },
                    fontFamily = FontFamily(Font(R.font.mulish_bold)),
                    fontSize = 20.sp
                )
            }
            items(items = uiFoodState.foodResponse, key = { it.id }) {}
        }
        SpacerVertical20Dp()
        FoodList(navController)
    }
}