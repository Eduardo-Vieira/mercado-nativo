package com.br.mercado.ui.shoppingcart

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
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
    private var quant:Double = 0.0
    private lateinit var mHandler: Handler
    private lateinit var mRunnable:Runnable

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

        mHandler = Handler()

        btnSave.setOnClickListener {
            val vTxtQty = if(txtQty.text.toString().isNotEmpty()) txtQty.text.toString().toFloat() else 0F
            val vTxtPrice = if(txtPrice.text.toString().isNotEmpty()) txtPrice.text.toString().toFloat() else 0F
            val data = Cart(0,id, txtDescription.text.toString(), vTxtQty, vTxtPrice)
            viewModel.insertCart(data)
            hideKeyboard(this.context!!) // hide keyboard
            popFragment() // back fragment
        }

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

        totalCalc.text = "R$0,00"

        txtPrice.addTextChangedListener {
            calcTotal()
        }

        txtQty.addTextChangedListener {
            calcTotal()
        }

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