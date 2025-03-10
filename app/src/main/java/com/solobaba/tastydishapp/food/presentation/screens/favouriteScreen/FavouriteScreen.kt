package com.solobaba.tastydishapp.food.presentation.screens.favouriteScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.solobaba.tastydishapp.R
import com.solobaba.tastydishapp.food.presentation.components.AppBarComponent
import com.solobaba.tastydishapp.ui.theme.Black1
import com.solobaba.tastydishapp.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.favourite),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Black1,
                        fontFamily = FontFamily(Font(R.font.mulish_regular)),
                        fontSize = 16.sp
                    )
                },
                navigationIcon = {
                    AppBarComponent(
                        icon = R.drawable.ic_arrow_square_back,
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = White),
            )
        }
    ) { paddingValues ->
        // Content inside the screen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Ensure it doesn't overlap with the AppBar
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.favourite_screen),
                style = MaterialTheme.typography.bodyLarge,
                color = Black1,
                fontFamily = FontFamily(Font(R.font.mulish_regular)),
                fontSize = 20.sp
            )
        }
    }
}