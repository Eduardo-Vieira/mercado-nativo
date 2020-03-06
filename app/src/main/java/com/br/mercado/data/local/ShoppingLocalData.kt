package com.br.mercado.data.local

import com.br.mercado.data.dao.ShoppingDao
import com.br.mercado.data.model.Shopping
import com.br.mercado.utils.AppExecutors

class ShoppingLocalData(private val shoppingDao: ShoppingDao) {

    fun all(listShopping:(data:List<Shopping>?)-> Unit){
        AppExecutors().diskIO.execute {
            val list = shoppingDao.allList()
            listShopping(list)
        }
    }

    fun getRow(id: Long, rowShopping:(data: Shopping?)-> Unit){
        AppExecutors().diskIO.execute {
            val row = shoppingDao.detail(id)
            rowShopping(row)
        }
    }

    fun deleteItem(id:Long){
        AppExecutors().diskIO.execute {
            shoppingDao.deleteItem(id)
        }
    }

    fun update(data: Shopping){
        AppExecutors().diskIO.execute {
            shoppingDao.updateItem(data)
        }
    }

    fun insert(data: Shopping){
        AppExecutors().diskIO.execute {
            shoppingDao.addItem(data)
        }
    }
}