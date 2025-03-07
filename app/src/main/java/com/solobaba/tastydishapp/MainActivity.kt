package com.solobaba.tastydishapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.solobaba.tastydishapp.food.presentation.navigation.BottomNavigationBar
import com.solobaba.tastydishapp.food.presentation.navigation.TastyDishAppNavigation
import com.solobaba.tastydishapp.food.presentation.viewmodel.FoodDishViewModel
import com.solobaba.tastydishapp.ui.theme.TastyDishAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val viewModel = hiltViewModel<FoodDishViewModel>()

            TastyDishAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.White,
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController,
                            onEvent = viewModel::onEvent
                        )
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TastyDishAppTheme {
        Greeting("Android")
    }
}