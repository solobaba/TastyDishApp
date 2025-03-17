package com.solobaba.tastydishapp.food.data.remote.response

data class BaseResponse<T>(val data: T, val message: String, val status: String)