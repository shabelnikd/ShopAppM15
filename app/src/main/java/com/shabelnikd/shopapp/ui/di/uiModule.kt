package com.shabelnikd.shopapp.ui.di

import com.shabelnikd.shopapp.ui.fragments.product.ListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { ListViewModel(getProductsUseCase = get(), get()) }
}