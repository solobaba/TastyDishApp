package com.solobaba.tastydishapp.food.presentation.screens.foodDetailsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.solobaba.tastydishapp.R
import com.solobaba.tastydishapp.food.presentation.components.RetryItem
import com.solobaba.tastydishapp.food.presentation.screens.FoodScreenRoute
import com.solobaba.tastydishapp.food.presentation.state.FoodDetailsState
import com.solobaba.tastydishapp.food.presentation.viewmodel.FoodDetailsViewModel
import com.solobaba.tastydishapp.util.NetworkUtils

@Composable
fun SelectedFoodDetailsScreen(navController: NavController, foodID: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val viewmodel = hiltViewModel<FoodDetailsViewModel>()
        val foodDetailsState = viewmodel.foodDetailsState.collectAsState().value
        val loading = viewmodel.loading.collectAsState().value
        viewmodel.foodID = foodID
        FoodDetailsContent(navController, foodDetailsState, loading)
    }
}

@Composable
fun FoodDetailsContent(
    navController: NavController,
    foodDetailsState: FoodDetailsState,
    loading: Boolean
) {
    val context = LocalContext.current

    if (!NetworkUtils.isNetworkAvailable(context)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            RetryItem(
                modifier = Modifier
                    .width(90.dp)
                    .height(40.dp),
                message = stringResource(id = R.string.check_your_internet_connection),
                onClick = {
                    //onEvent(MovieUiEvent.Navigate)
                    navController.popBackStack()
                    navController.navigate(FoodScreenRoute.HomeScreen.route)
                }
            )
        }
    } else {
        
    }
}
