package com.solobaba.tastydishapp.food.presentation.events

sealed interface FoodUiEvent {
    object Paginate : FoodUiEvent
    object Navigate : FoodUiEvent
}