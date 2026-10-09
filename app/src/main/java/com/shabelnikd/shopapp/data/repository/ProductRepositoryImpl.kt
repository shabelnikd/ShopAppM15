package com.shabelnikd.shopapp.data.repository

import com.shabelnikd.shopapp.data.datasource.StoreApi
import com.shabelnikd.shopapp.data.mappers.toDomain
import com.shabelnikd.shopapp.domain.models.Product
import com.shabelnikd.shopapp.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val api: StoreApi
) : ProductRepository {
    override suspend fun getProducts(): List<Product> =
        api.getAllProducts().map { it.toDomain() }
}