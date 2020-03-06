package com.br.mercado.ui.shoppingcart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import com.br.mercado.App
import com.br.mercado.R
import com.br.mercado.base.BaseFragment
import com.br.mercado.data.model.Cart
import com.br.mercado.utils.ARG_ID
import kotlinx.android.synthetic.main.shopping_cart_add_fragment.*
import java.text.NumberFormat

class ShoppingCartAddFragment: BaseFragment() {

    private var id: Long = 0

    companion object {
        @JvmStatic
        fun newInstance(id: Long) =
            ShoppingCartAddFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ID, id)
                }
            }
    }

    lateinit var viewModelFactory: ShoppingCartViewModelFactory
    private lateinit var viewModel: ShoppingCartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.shopping_cart_add_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            id = it.getLong(ARG_ID)
        }

        // Set appActionBar
        setAppActionBar(R.string.shoppingcart_add_title, true)

        viewModelFactory = ShoppingCartViewModelFactory(App.injectShoppingCartRepository())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ShoppingCartViewModel::class.java)

        btnSave.setOnClickListener {
            var data = Cart(0,id, txtDescription.text.toString(), txtQty.text.toString().toFloat(), txtPrice.text.toString().toFloat())
            viewModel.insertCart(data)
            hideKeyboard(this.context!!) // hide keyboard
            popFragment() // back fragment
        }

        totalCalc.text = "R$0,00"

        txtPrice.addTextChangedListener {
            calcTotal()
        }

        txtQty.addTextChangedListener {
            calcTotal()
        }

    }

    private fun calcTotal(){
        val currencyFormat = NumberFormat.getCurrencyInstance()
        totalCalc.text = "0,00"
        if(!txtPrice.text.isNullOrEmpty()){
            if(!txtQty.text.isNullOrEmpty()){
                totalCalc.text = currencyFormat.format(txtPrice.text.toString().toFloat() * txtQty.text.toString().toFloat())
            }
        }
    }
}