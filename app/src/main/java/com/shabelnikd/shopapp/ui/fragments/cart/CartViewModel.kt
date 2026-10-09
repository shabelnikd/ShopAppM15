package com.shabelnikd.shopapp.ui.fragments.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shabelnikd.shopapp.domain.repository.CartRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: CartRepository
) : ViewModel() {

    val items = repository.cartItems
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    val total = items.map { list ->
        list.sumOf {
            val price = it.product.price
            price * it.quantity
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    fun checkout() {
        viewModelScope.launch {
            val result = repository.checkout()

            result.onSuccess {
                repository.clearCart()
            }
        }
    }


}