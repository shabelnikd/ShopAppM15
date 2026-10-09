package com.shabelnikd.shopapp.ui.fragments.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shabelnikd.shopapp.domain.models.Product
import com.shabelnikd.shopapp.domain.repository.CartRepository
import com.shabelnikd.shopapp.domain.usecases.GetProductsUseCase
import com.shabelnikd.shopapp.ui.models.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Product>>> = _state.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val products = getProductsUseCase()
                _state.value = UiState.Success(data = products)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "")
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            cartRepository.addToCart(product)
        }
    }
}