package com.br.mercado.data.local

import com.br.mercado.data.dao.CartDao
import com.br.mercado.data.model.Cart
import com.br.mercado.utils.AppExecutors

class ShoppingCartLocalData(private val cartDao:CartDao) {

    fun all(idShopping: Long, listCart:(data:List<Cart>?)-> Unit){
        AppExecutors().diskIO.execute {
            val list = cartDao.allList(idShopping)
            listCart(list)
        }
    }

    fun findItem(idShopping: Long, search: String, listCart:(data:List<Cart>?)-> Unit){
        AppExecutors().diskIO.execute {
            val list = cartDao.findShoppingItem(idShopping, search)
            listCart(list)
        }
    }

    fun getRow(id: Long, rowShopping:(data: Cart?)-> Unit){
        AppExecutors().diskIO.execute {
            val row = cartDao.detail(id)
            rowShopping(row)
        }
    }

    fun deleteItem(id:Long){
        AppExecutors().diskIO.execute {
            cartDao.deleteItem(id)
        }
    }

    fun update(data: Cart){
        AppExecutors().diskIO.execute {
            cartDao.updateItem(data)
        }
    }
    fun updateShoppingItem(id: Long,total:Float){
        AppExecutors().diskIO.execute {
            cartDao.updateShoppingItem(id, total)
        }
    }

    fun insert(data: Cart){
        AppExecutors().diskIO.execute {
            cartDao.addItem(data)
        }
    }
}