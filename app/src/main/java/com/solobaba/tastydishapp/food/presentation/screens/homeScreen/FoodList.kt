package com.solobaba.tastydishapp.food.presentation.screens.homeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.solobaba.tastydishapp.R
import com.solobaba.tastydishapp.food.domain.model.DomainFoodData
import com.solobaba.tastydishapp.food.presentation.components.CircularIndeterminateProgressBar
import com.solobaba.tastydishapp.food.presentation.viewmodel.FoodDishViewModel
import com.solobaba.tastydishapp.ui.theme.Black1
import com.solobaba.tastydishapp.ui.theme.Grey1
import com.solobaba.tastydishapp.ui.theme.White
import com.solobaba.tastydishapp.util.SpacerVertical10Dp

@Composable
fun FoodList(navController: NavController) {
    val viewmodel = hiltViewModel<FoodDishViewModel>()
    val foodState = viewmodel.foodState.collectAsState().value
    val foodData = viewmodel.domainFoodResponse.collectAsState().value
    val loading = viewmodel.loading.collectAsState().value

    FoodListLayout(
        navController = navController,
        foodState = foodData,
        loading = loading
    )
}

@Composable
fun FoodListLayout(
    navController: NavController,
    foodState: List<DomainFoodData?>,
    loading: Boolean
) {
    Column(modifier = Modifier.fillMaxSize()) {
        CircularIndeterminateProgressBar(isDisplayed = loading)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(color = White),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(foodState.size) { foodData ->
                FoodDataLayout(
                    navController = navController,
                    foodData = foodState[foodData])
            }
        }
    }
}

@Composable
fun FoodDataLayout(
    navController: NavController,
    foodData: DomainFoodData?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(width = 1.dp, color = Grey1),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        onClick = { }
    ) {
        Column {
            AsyncImage(
                modifier = Modifier.height(200.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(foodData?.foodImages?.firstOrNull()?.image_url)
                    .size(Size.ORIGINAL)
                    .error(R.drawable.ic_user_avatar)
                    .crossfade(true)
                    .build(),
                contentDescription = "FoodImage",
                placeholder = painterResource(id = R.drawable.ic_user_avatar),
                contentScale = ContentScale.Crop
            )

            SpacerVertical10Dp()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = foodData?.name ?: "",
                    fontFamily = FontFamily(Font(R.font.mulish_bold)),
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1F),
                    color = Black1
                )
                Image(
                    painter = painterResource(R.drawable.ic_favourite),
                    contentDescription = "Favourite icon",
                    modifier = Modifier.wrapContentWidth()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_fire),
                    contentDescription = "Calories icon"
                )
                SpacerVertical10Dp()
                Text(
                    text = "${foodData?.calories} Calories",
                    fontFamily = FontFamily(Font(R.font.mulish_regular)),
                    style = MaterialTheme.typography.bodySmall,
                    color = Black1
                )
            }
            SpacerVertical10Dp()
            Text(
                text = foodData?.description ?: "",
                fontFamily = FontFamily(Font(R.font.mulish_regular)),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Black1
            )
            SpacerVertical10Dp()
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(foodData!!.foodTags) {
                    TagComponent(tag = it)
                }
            }
            SpacerVertical10Dp()
        }
    }
}
