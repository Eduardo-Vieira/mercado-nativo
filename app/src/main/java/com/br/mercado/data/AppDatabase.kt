package com.br.mercado.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.br.mercado.data.dao.CartDao
import com.br.mercado.data.dao.ShoppingDao
import com.br.mercado.data.model.Cart
import com.br.mercado.data.model.Shopping

@Database(entities = [Shopping::class, Cart::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun shoppingDao(): ShoppingDao

    abstract fun cartDao(): CartDao

}