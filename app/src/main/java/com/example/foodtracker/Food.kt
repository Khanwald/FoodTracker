package com.example.foodtracker


import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class FoodResponse(
    @SerialName("data")
    val data: List<Food>?
)


@Keep
@Serializable
data class Food(
    @SerialName("name")
    val name: String?,
    @SerialName("calories")
    val calories: String?,
) : java.io.Serializable
