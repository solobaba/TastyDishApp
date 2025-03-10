package com.solobaba.tastydishapp.food.presentation.screens.foodDetailsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.solobaba.tastydishapp.R
import com.solobaba.tastydishapp.food.domain.model.DomainFoodData
import com.solobaba.tastydishapp.food.presentation.components.AppBarComponent
import com.solobaba.tastydishapp.food.presentation.components.CircularIndeterminateProgressBar
import com.solobaba.tastydishapp.food.presentation.screens.homeScreen.TagComponent
import com.solobaba.tastydishapp.ui.theme.Black1
import com.solobaba.tastydishapp.ui.theme.Grey5
import com.solobaba.tastydishapp.ui.theme.White
import com.solobaba.tastydishapp.util.SpacerVertical10Dp
import com.solobaba.tastydishapp.util.SpacerVertical5Dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadFoodDetailsContent(
    navController: NavController,
    foodDetailsState: DomainFoodData,
    loading: Boolean
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {},
            navigationIcon = {
                AppBarComponent(
                    icon = R.drawable.ic_arrow_square_back,
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = White),
            actions = {
                AppBarComponent(
                    icon = R.drawable.ic_favourite,
                    onClick = {},
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                )
                AppBarComponent(
                    icon = R.drawable.ic_edit,
                    onClick = {},
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                )
            }
        )

        CircularIndeterminateProgressBar(isDisplayed = loading)

        val pagerState = rememberPagerState { foodDetailsState.foodImages.size }

        LaunchedEffect(foodDetailsState) {
            pagerState.scrollToPage(0) //Ensure it starts from first page
        }

        SpacerVertical5Dp()

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth(),
                    beyondViewportPageCount = 1,
                    pageContent = { position ->
                        AsyncImage(
                            modifier = Modifier
                                .height(290.dp)
                                .fillMaxWidth(),
                            model = foodDetailsState.foodImages[position].image_url,
                            contentDescription = "FoodDetailsImage",
                            placeholder = painterResource(id = R.drawable.ic_user_avatar),
                            contentScale = ContentScale.Crop,
                            error = painterResource(id = R.drawable.ic_user_avatar),
                            onError = { it.result.throwable.printStackTrace() }
                        )
                    })
                Text(
                    text = "${pagerState.settledPage + 1}/${foodDetailsState.foodImages.count()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Black1,
                    fontFamily = FontFamily(Font(R.font.mulish_regular)),
                    modifier = Modifier
                        .padding(16.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(6.dp)
                        .align(Alignment.BottomEnd)
                )
            }
            SpacerVertical10Dp()

            Text(
                text = foodDetailsState.name ?: "",
                fontFamily = FontFamily(Font(R.font.mulish_bold)),
                fontSize = 16.sp,
                color = Black1,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            SpacerVertical5Dp()

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                foodDetailsState.let {
                    items(it.foodTags) { foodTags ->
                        TagComponent(tag = foodTags)
                    }
                }
            }

            SpacerVertical10Dp()

            Text(
                modifier = Modifier.padding(16.dp),
                text = foodDetailsState.description ?: "",
                fontFamily = FontFamily(Font(R.font.mulish_regular)),
                style = MaterialTheme.typography.bodyMedium,
                color = Black1
            )
            SpacerVertical10Dp()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(color = Grey5, shape = RoundedCornerShape(4.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Nutrition",
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = FontFamily(Font(R.font.mulish_regular)),
                    color = Black1
                )
                SpacerVertical5Dp()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_fire),
                        contentDescription = "Calories icon"
                    )
                    SpacerVertical5Dp()
                    Text(
                        text = "${foodDetailsState.calories} Calories",
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = FontFamily(Font(R.font.mulish_regular)),
                        color = Black1
                    )
                }
            }
        }
    }
}