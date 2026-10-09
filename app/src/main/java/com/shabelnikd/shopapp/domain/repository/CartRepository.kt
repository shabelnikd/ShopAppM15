package com.shabelnikd.shopapp.domain.repository

import com.shabelnikd.shopapp.domain.models.CartItem
import com.shabelnikd.shopapp.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    val cartItems: Flow<List<CartItem>>
    
    suspend fun addToCart(product: Product)

    suspend fun clearCart()

    suspend fun checkout(): Result<String>
}