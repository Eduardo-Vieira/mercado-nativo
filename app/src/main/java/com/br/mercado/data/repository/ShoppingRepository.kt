package com.br.mercado.data.repository

import com.br.mercado.data.local.ShoppingLocalData
import com.br.mercado.data.model.Shopping

class ShoppingRepository(private val local: ShoppingLocalData) {

    fun getShopping(listShopping:(data:List<Shopping>?)-> Unit) {
        return local.all(listShopping = {
            listShopping(it)
        })
    }

    fun getShoppingRow(id: Long, rowShopping:(data: Shopping?)-> Unit){
        local.getRow(id, rowShopping = {
            rowShopping(it)
        })
    }

    fun insertShopping(data: Shopping){
        local.insert(data)
    }

    fun updateShopping(data: Shopping){
        local.update(data)
    }

    fun deleteItemShopping(id:Long){
        local.deleteItem(id)
    }
}