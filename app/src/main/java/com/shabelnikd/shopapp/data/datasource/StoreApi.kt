package com.shabelnikd.shopapp.data.datasource

import com.shabelnikd.shopapp.data.model.CartRequestDto
import com.shabelnikd.shopapp.data.model.CartResponseDto
import com.shabelnikd.shopapp.data.model.ProductDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StoreApi {
    @GET("products")
    suspend fun getAllProducts(): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDto

    @POST("carts")
    suspend fun checkout(@Body cart: CartRequestDto): CartResponseDto
}