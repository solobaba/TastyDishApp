package com.solobaba.tastydishapp.food.presentation.screens.foodDetailsScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.solobaba.tastydishapp.R
import com.solobaba.tastydishapp.food.domain.model.DomainFoodData
import com.solobaba.tastydishapp.food.presentation.components.CircularIndeterminateProgressBar
import com.solobaba.tastydishapp.food.presentation.components.RetryItem
import com.solobaba.tastydishapp.food.presentation.screens.FoodScreenRoute
import com.solobaba.tastydishapp.food.presentation.viewmodel.FoodDetailsViewModel
import com.solobaba.tastydishapp.util.NetworkUtils

@Composable
fun SelectedFoodDetailsScreen(navController: NavController, foodID: Int) {
    val viewmodel = hiltViewModel<FoodDetailsViewModel>()

    //Ensure the function is called only once per foodID
    LaunchedEffect(foodID) {
        viewmodel.fetchSelectedFoodDetails(foodID)
    }
    val foodDetails = viewmodel.foodData.collectAsState().value
    val loading = viewmodel.loading.collectAsState().value
    Log.d("FoodDetailsScreen", "Recomposing UI. Loading: $loading, FoodDetails: $foodDetails")
    if (foodDetails != null) {
        FoodDetailsContent(navController, foodDetails, loading)
    }

//    when {
//        loading -> CircularIndeterminateProgressBar(isDisplayed = true)
//        foodDetails != null -> FoodDetailsContent(navController, foodDetails, loading)
//    }
}

@Composable
fun FoodDetailsContent(
    navController: NavController,
    foodDetails: DomainFoodData,
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
                    navController.popBackStack()
                    navController.navigate(FoodScreenRoute.HomeScreen.route)
                }
            )
        }
    } else {
        Log.d("FoodDetailsContent", "Displaying Food Details: $foodDetails")
        LoadFoodDetailsContent(navController, foodDetails, loading)
    }
}