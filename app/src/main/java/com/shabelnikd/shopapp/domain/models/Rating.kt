package com.shabelnikd.shopapp.domain.models


data class Rating(
    val rate: Double, // 3.9
    val count: Int // 120
) {
    companion object {
        fun empty(): Rating {
            return Rating(
                rate = 0.0,
                count = 0
            )
        }
    }
}