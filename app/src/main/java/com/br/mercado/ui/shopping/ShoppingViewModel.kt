package com.br.mercado.ui.shopping

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.br.mercado.data.model.Shopping
import com.br.mercado.data.repository.ShoppingRepository

class ShoppingViewModel(private val repository: ShoppingRepository): ViewModel() {

    var itemShopping : MutableLiveData<Shopping>? = MutableLiveData()
    var shopping : MutableLiveData<List<Shopping>>? = MutableLiveData()

    fun getShopping(){
        repository.getShopping(listShopping = {
            shopping?.postValue(it)
        })
    }

    fun getShoppingRow(id: Long){
        repository.getShoppingRow(id, rowShopping = {
            itemShopping?.postValue(it)
        })
    }

    fun insertShopping(data:Shopping){
        repository.insertShopping(data)
    }

    fun updateShopping(data:Shopping){
        repository.updateShopping(data)
    }

    fun deleteItemShopping(id:Long){
        repository.deleteItemShopping(id)
    }
}
