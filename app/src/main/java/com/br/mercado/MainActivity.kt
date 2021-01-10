package com.br.mercado

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.br.mercado.ui.shopping.ShoppingFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.apptoolbar))

        // open fragment home
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentLayouts, ShoppingFragment.newInstance() , "ShoppingFragment")
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onNavigateUp()
    }
}
