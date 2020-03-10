package com.br.mercado.ui.shoppingcart

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
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
import kotlinx.android.synthetic.main.shopping_cart_add_fragment.*
import kotlinx.android.synthetic.main.shopping_cart_edit_fragment.*
import kotlinx.android.synthetic.main.shopping_cart_edit_fragment.btnAdd
import kotlinx.android.synthetic.main.shopping_cart_edit_fragment.btnRemove
import kotlinx.android.synthetic.main.shopping_cart_edit_fragment.btnSave
import kotlinx.android.synthetic.main.shopping_cart_edit_fragment.totalCalc
import kotlinx.android.synthetic.main.shopping_cart_edit_fragment.txtDescription
import kotlinx.android.synthetic.main.shopping_cart_edit_fragment.txtPrice
import kotlinx.android.synthetic.main.shopping_cart_edit_fragment.txtQty
import java.text.NumberFormat
import javax.inject.Inject

class ShoppingCartEditFragment: BaseFragment() {

    private var id: Long = 0
    private lateinit var itemCart:Cart
    private var quant:Double = 0.0
    private lateinit var mHandler: Handler
    private lateinit var mRunnable:Runnable

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

        mHandler = Handler()

        btnAdd.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> runnable(true)
                    MotionEvent.ACTION_UP -> mHandler.removeCallbacks(mRunnable)
                }
                return false
            }
        })

        btnRemove.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> runnable(false)
                    MotionEvent.ACTION_UP -> mHandler.removeCallbacks(mRunnable)
                }
                return false
            }
        })
    }

    private fun runnable(type:Boolean){
        mRunnable = Runnable {
            if(type) addQuant() else removeQuant()
            mHandler.postDelayed(
                mRunnable,
                500
            )
        }
        mHandler.postDelayed(
            mRunnable,
            500
        )
        if(type) addQuant() else removeQuant()
    }

    private fun addQuant(){
        quant += 1
        if(quant > 0) txtQty.setText(quant.toString())
    }

    private fun removeQuant(){
        if(quant > 0) {
            quant -= 1
            txtQty.setText(quant.toString())
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