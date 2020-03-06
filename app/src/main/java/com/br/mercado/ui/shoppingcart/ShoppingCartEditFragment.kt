package com.br.mercado.ui.shoppingcart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.br.mercado.App
import com.br.mercado.R
import com.br.mercado.base.BaseFragment
import com.br.mercado.data.model.Cart
import com.br.mercado.utils.ARG_ID
import com.br.mercado.utils.utils
import kotlinx.android.synthetic.main.app_action_bar.*
import kotlinx.android.synthetic.main.shopping_cart_edit_fragment.*
import java.text.NumberFormat
import javax.inject.Inject

class ShoppingCartEditFragment: BaseFragment() {

    private var id: Long = 0
    private lateinit var itemCart:Cart

    companion object {
        @JvmStatic
        fun newInstance(id: Long) =
            ShoppingCartEditFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ID, id)
                }
            }
    }

    @Inject
    lateinit var viewModelFactory: ShoppingCartViewModelFactory
    private lateinit var viewModel: ShoppingCartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shopping_cart_edit_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            id = it.getLong(ARG_ID)
        }

        // Set appActionBar
        setAppActionBar(R.string.shoppingcart_edit_title, true)

        viewModelFactory = ShoppingCartViewModelFactory(App.injectShoppingCartRepository())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ShoppingCartViewModel::class.java)

        initObserveEditCart()

        btnSave.setOnClickListener {
            itemCart.price = txtPrice.text.toString().toFloat()
            itemCart.description = txtDescription.text.toString()
            itemCart.qty = txtQty.text.toString().toFloat()
            viewModel.updateCart(itemCart)
            hideKeyboard(this.context!!) // hide keyboard
            popFragment() // back fragment
        }

        txtPrice.addTextChangedListener {
            calcTotal()
        }

        txtQty.addTextChangedListener {
            calcTotal()
        }
    }

    private fun initObserveEditCart(){
        viewModel.itemCart?.observe(this, Observer {
            itemCart = it
            txtDescription.setText(it.description)
            txtQty.setText(it.qty.toString())
            txtPrice.setText(it.price.toString())
            totalCalc.text = utils().getCurrencyFormat(it.qty * it.price)
        })
        viewModel.getCartRow(id)
    }

    private fun calcTotal() {
        totalCalc.text = "0,00"
        if (!txtPrice.text.isNullOrEmpty()) {
            if (!txtQty.text.isNullOrEmpty()) {
                totalCalc.text =
                    utils().getCurrencyFormat(txtPrice.text.toString().toFloat() * txtQty.text.toString().toFloat())
            }
        }
    }
}