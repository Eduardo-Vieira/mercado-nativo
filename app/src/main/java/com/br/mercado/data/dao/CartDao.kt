package com.br.mercado.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.br.mercado.data.model.Cart
import com.br.mercado.data.model.Shopping

@Dao
interface CartDao {

    @Query("select * from cart where idShopping=:id")
    fun allList(id: Long): List<Cart>

    @Insert
    fun addItem(vararg cart: Cart)

    @Update
    fun updateItem(vararg cart: Cart)

    @Query("SELECT * FROM cart WHERE id = :id")
    fun detail(id: Long):Cart

    @Query("DELETE FROM cart WHERE id = :id")
    fun deleteItem(id:Long)

    @Query("UPDATE shopping set sumTotal = :total WHERE id = :id")
    fun updateShoppingItem(id:Long,total:Float)

    @Query("SELECT * FROM cart WHERE idShopping=:id and description like :description")
    fun findShoppingItem(id: Long, description:String): List<Cart>
}