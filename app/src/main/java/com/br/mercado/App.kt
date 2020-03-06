package com.br.mercado

import android.app.Application
import androidx.room.Room
import com.br.mercado.data.AppDatabase
import com.br.mercado.data.local.ShoppingCartLocalData
import com.br.mercado.data.local.ShoppingLocalData
import com.br.mercado.data.repository.ShoppingCartRepository
import com.br.mercado.data.repository.ShoppingRepository
import com.br.mercado.utils.DB_NAME
import com.facebook.stetho.Stetho

class App : Application() {


    companion object {
        private lateinit var appDatabase: AppDatabase

        private lateinit var shoppingRepository: ShoppingRepository
        private lateinit var shoppingCartRepository: ShoppingCartRepository

        fun injectShoppingRepository() = shoppingRepository
        fun injectShoppingCartRepository() = shoppingCartRepository
    }

    override fun onCreate() {
        super.onCreate()

        val initializerBuilder = Stetho.newInitializerBuilder(this)
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
        val initializer = initializerBuilder.build()
        Stetho.initialize(initializer)

        // open data base
        appDatabase = Room.databaseBuilder(this,
            AppDatabase::class.java, DB_NAME)
            .build()

        // inject's
        shoppingRepository = ShoppingRepository(ShoppingLocalData(appDatabase.shoppingDao()))
        shoppingCartRepository = ShoppingCartRepository(ShoppingCartLocalData(appDatabase.cartDao()))

    }


}