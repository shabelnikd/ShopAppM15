package com.shabelnikd.shopapp.domain.repository

import com.shabelnikd.shopapp.domain.models.Product

interface ProductRepository {
    suspend fun getProducts(): List<Product>
}