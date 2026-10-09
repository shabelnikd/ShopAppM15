package com.shabelnikd.shopapp.domain.di

import com.shabelnikd.shopapp.domain.usecases.GetProductsUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetProductsUseCase(repository = get()) }
}