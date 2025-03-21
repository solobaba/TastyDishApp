package com.solobaba.tastydishapp.food.presentation.screens.foodDetailsScreen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.solobaba.tastydishapp.food.domain.model.response.DomainFoodData
import com.solobaba.tastydishapp.food.presentation.viewmodel.FoodDetailsViewModel

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
}

@Composable
fun FoodDetailsContent(
    navController: NavController,
    foodDetails: DomainFoodData,
    loading: Boolean
) {
    LoadFoodDetailsContent(navController, foodDetails, loading)

    val context = LocalContext.current

    // Automatically track network state
//    val isNetworkAvailable by produceState(initialValue = NetworkUtils.isNetworkAvailable(context)) {
//        while (true) {
//            value = NetworkUtils.isNetworkAvailable(context)
//            Log.d("NetworkCheck", "Updated network state: $value") // Debugging log
//            delay(2000) // Check every 2 seconds
//        }
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        if (!isNetworkAvailable) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                RetryItem(
//                    modifier = Modifier
//                        .width(90.dp)
//                        .height(40.dp),
//                    message = stringResource(id = R.string.check_your_internet_connection),
//                    onClick = {
//                        navController.popBackStack()
//                        navController.navigate(FoodScreenRoute.HomeScreen.route)
//                    }
//                )
//            }
//        } else {
//            Log.d("FoodDetailsContent", "Displaying Food Details: $foodDetails")
//            LoadFoodDetailsContent(navController, foodDetails, loading)
//        }
//    }
}