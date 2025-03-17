package com.solobaba.tastydishapp.food.presentation.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solobaba.tastydishapp.R
import com.solobaba.tastydishapp.food.domain.model.DomainCategory
import com.solobaba.tastydishapp.ui.theme.Blue
import com.solobaba.tastydishapp.ui.theme.Grey1
import com.solobaba.tastydishapp.ui.theme.Grey2

@Composable
fun FoodCategoriesChip(chipItems: List<DomainCategory>, onItemClick: (DomainCategory) -> Unit) {

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        //contentPadding = PaddingValues(vertical = 4.dp, horizontal = 16.dp)
    ) {
        itemsIndexed(chipItems) { index, chipItem ->
            val isSelected = selectedIndex == index
            FilterChip(
                selected = true,
                onClick = {
                    selectedIndex = index
                    onItemClick(chipItem)
                },
                label = {
                    Text(
                        text = chipItem.name,
                        modifier = Modifier.padding(3.dp),
                        color = if (isSelected) White else Grey2,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.mulish_regular))
                    )
                },
                shape = RoundedCornerShape(4.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = if (isSelected) Blue else Grey1
                )
            )
        }
    }
}

val categories = listOf(
    DomainCategory("", "", 1, "All", ""),
    DomainCategory("", "", 1, "Morning Feast", ""),
    DomainCategory("", "", 1, "Sunrise Meal", ""),
    DomainCategory("", "", 1, "Dawn Delicacies", "")
)

@Preview
@Composable
fun ChipGroupPreview() {
    FoodCategoriesChip(chipItems = categories) {
    }
}