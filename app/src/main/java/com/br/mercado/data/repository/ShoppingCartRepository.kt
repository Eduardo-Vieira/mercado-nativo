package com.br.mercado.data.repository

import com.br.mercado.data.local.ShoppingCartLocalData
import com.br.mercado.data.model.Cart
import javax.inject.Inject

class ShoppingCartRepository(private val local:ShoppingCartLocalData) {

    fun getCart(idShopping: Long, listCart:(data:List<Cart>?)-> Unit) {
        return local.all(idShopping, listCart = {
            listCart(it)
        })
    }

    fun getFindCart(idShopping: Long, search: String, listCart:(data:List<Cart>?)-> Unit) {
        return local.findItem(idShopping, search, listCart = {
            listCart(it)
        })
    }

    fun getCartRow(id: Long, rowShopping:(data: Cart?)-> Unit){
        local.getRow(id, rowShopping = {
            rowShopping(it)
        })
    }

    fun insertCart(data: Cart){
        local.insert(data)
    }

    fun updateCart(data: Cart){
        local.update(data)
    }

    fun deleteItemCart(id:Long){
        local.deleteItem(id)
    }
    fun updateShoppingItem(id: Long, total:Float){
        local.updateShoppingItem(id, total)
    }
}