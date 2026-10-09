package com.shabelnikd.shopapp.domain.usecases

import com.shabelnikd.shopapp.domain.models.Product
import com.shabelnikd.shopapp.domain.repository.ProductRepository

class GetProductsUseCase (
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): List<Product> {
        return repository.getProducts()
    }
}

