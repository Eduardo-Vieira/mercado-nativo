package com.br.mercado.ui.shoppingcart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.br.mercado.data.repository.ShoppingCartRepository
import javax.inject.Inject

class ShoppingCartViewModelFactory @Inject constructor (private val repository: ShoppingCartRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingCartViewModel::class.java)) {
            return ShoppingCartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}