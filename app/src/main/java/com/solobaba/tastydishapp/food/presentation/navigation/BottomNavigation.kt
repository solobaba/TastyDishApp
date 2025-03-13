package com.solobaba.tastydishapp.food.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.solobaba.tastydishapp.R
import com.solobaba.tastydishapp.food.presentation.events.FoodUiEvent
import com.solobaba.tastydishapp.food.presentation.screens.FoodScreenRoute
import com.solobaba.tastydishapp.ui.theme.Blue
import com.solobaba.tastydishapp.ui.theme.BlueLight
import com.solobaba.tastydishapp.ui.theme.Gray500

@Composable
fun BottomNavigationBar(
    navController: NavController,
    onEvent: (FoodUiEvent) -> Unit
) {
    val bottomNavItems = listOf(
        BottomNavItems(
            title = stringResource(R.string.home),
            icon = R.drawable.ic_home
        ),
        BottomNavItems(
            title = stringResource(R.string.generator),
            icon = R.drawable.ic_generator
        ),
        BottomNavItems(
            title = stringResource(R.string.add),
            icon = R.drawable.ic_add
        ),
        BottomNavItems(
            title = stringResource(R.string.favourite),
            icon = R.drawable.ic_favourite
        ),
        BottomNavItems(
            title = stringResource(R.string.planner),
            icon = R.drawable.ic_planner
        )
    )

    val selectedItem = rememberSaveable { mutableIntStateOf(0) }

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(BlueLight)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                bottomNavItems.fastForEachIndexed { index, bottomItems ->
                    BottomNavigationItem(
                        selected = selectedItem.intValue == index,
                        selectedContentColor = BlueLight,
                        unselectedContentColor = Gray500,
                        onClick = {
                            selectedItem.intValue = index
                            when (selectedItem.intValue) {
                                0 -> {
                                    onEvent(FoodUiEvent.Navigate)
                                    navController.popBackStack()
                                    navController.navigate(FoodScreenRoute.HomeScreen.route)
                                }
                                1 -> {
                                    navController.navigate(FoodScreenRoute.GeneratorScreen.route)
                                }
                                2 -> {
                                    navController.navigate(FoodScreenRoute.AddFoodScreen.route)
                                }
                                3 -> {
                                    navController.navigate(FoodScreenRoute.FavouriteScreen.route)
                                }
                                4 -> {
                                    navController.navigate(FoodScreenRoute.PlannerScreen.route)
                                }
                            }
                        },
                        icon = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .width(21.dp)
                                        .height(24.dp),
                                    painter = rememberImagePainter(bottomItems.icon),
                                    contentDescription = bottomItems.title,
                                    tint = if (selectedItem.intValue == index) Blue else Color.Gray
                                )
                                Text(
                                    text = bottomItems.title,
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 1
                                )
                            }
                        })
                }
            }
        }
    }
}

data class BottomNavItems(
    var title: String,
    @DrawableRes var icon: Int
)