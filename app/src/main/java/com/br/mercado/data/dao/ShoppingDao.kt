package com.br.mercado.data.dao

import androidx.room.*
import com.br.mercado.data.model.Shopping

@Dao
interface ShoppingDao {

    @Query("select * from shopping")
    fun allList(): List<Shopping>

    @Insert
    fun addItem(vararg shopping: Shopping)

    @Update
    fun updateItem(vararg shopping: Shopping)

    @Query("SELECT * FROM shopping WHERE id = :id")
    fun detail(id: Long):Shopping

    @Query("DELETE FROM shopping WHERE id = :id")
    fun deleteItem(id:Long)

}