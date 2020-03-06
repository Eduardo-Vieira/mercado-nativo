package com.br.mercado

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.br.mercado.ui.shopping.ShoppingFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // open fragment home
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentLayouts, ShoppingFragment.newInstance() , "ShoppingFragment")
            .commit()
    }
}
