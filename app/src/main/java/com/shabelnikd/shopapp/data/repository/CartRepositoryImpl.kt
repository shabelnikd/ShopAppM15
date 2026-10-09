package com.shabelnikd.shopapp.data.repository

import com.shabelnikd.shopapp.data.datasource.StoreApi
import com.shabelnikd.shopapp.data.model.CartProductDto
import com.shabelnikd.shopapp.data.model.CartRequestDto
import com.shabelnikd.shopapp.domain.models.CartItem
import com.shabelnikd.shopapp.domain.models.Product
import com.shabelnikd.shopapp.domain.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartRepositoryImpl(
    private val api: StoreApi
) : CartRepository {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    override val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    override suspend fun addToCart(product: Product) {
        _cartItems.update { currentList ->
            val existing = currentList.find { it.product.id == product.id }
            if (existing != null) {
                currentList.map { item ->
                    if (item.product.id == product.id) item.copy(quantity = item.quantity + 1)
                    else item
                }
            } else {
                currentList + CartItem(product, quantity = 1)
            }
        }
    }

    override suspend fun clearCart() {
        _cartItems.value = emptyList()
    }

    override suspend fun checkout(): Result<String> {
        return try {
            val items = _cartItems.value.map {
                CartProductDto(it.product.id, it.quantity)
            }

            val resp = api.checkout(CartRequestDto(products = items))

            Result.success("Заказ №${resp.id} успешно оформлен")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}