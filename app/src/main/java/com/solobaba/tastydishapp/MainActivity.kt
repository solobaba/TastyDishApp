package com.solobaba.tastydishapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.solobaba.tastydishapp.food.presentation.navigation.BottomNavigationBar
import com.solobaba.tastydishapp.food.presentation.navigation.TastyDishAppNavigation
import com.solobaba.tastydishapp.food.presentation.screens.FoodScreenRoute
import com.solobaba.tastydishapp.food.presentation.viewmodel.FoodDishViewModel
import com.solobaba.tastydishapp.ui.theme.TastyDishAppTheme
import com.solobaba.tastydishapp.util.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val viewModel = hiltViewModel<FoodDishViewModel>()

            val isAvailable = NetworkUtils.observeConnectivityAsFlow(this)
            Log.d("NetworkCheck", "Network available (before UI loads): $isAvailable")

            TastyDishAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White,
                    bottomBar = {
                        if (navBackStackEntry?.destination?.route == FoodScreenRoute.HomeScreen.route ||
                            navBackStackEntry?.destination?.route == FoodScreenRoute.GeneratorScreen.route ||
                            navBackStackEntry?.destination?.route == FoodScreenRoute.FavouriteScreen.route ||
                            navBackStackEntry?.destination?.route == FoodScreenRoute.PlannerScreen.route) {
                            BottomNavigationBar(
                                navController = navController,
                                onEvent = viewModel::onEvent
                            )
                        }
                    },
                    floatingActionButton = {
                        if (navBackStackEntry?.destination?.route == FoodScreenRoute.HomeScreen.route) {
                            FloatingActionButton(
                                onClick = { navController.navigate(FoodScreenRoute.AddFoodScreen.route) }, //Navigate to Add Food Screen
                                backgroundColor = Color.Red,
                                contentColor = Color.White,
                                shape = CircleShape
                            ) {
                                Icon(
                                    //painter = painterResource(id = android.R.drawable.ic_input_add),
                                    painter = painterResource(id = R.drawable.ic_add_food),
                                    contentDescription = "Add Food"
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        content = {
                            TastyDishAppNavigation(
                                navController = navController,
                                innerPadding = innerPadding
                            )
                        }
                    )
                }
            }
        }
    }
}