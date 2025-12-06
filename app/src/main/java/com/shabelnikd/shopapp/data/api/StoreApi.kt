package com.shabelnikd.shopapp.data.api

import com.shabelnikd.shopapp.data.model.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface StoreApi {

    @GET("products")
    suspend fun getAllProducts(): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDto
}