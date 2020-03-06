package com.br.mercado.ui.shoppingcart

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.br.mercado.App
import com.br.mercado.R
import com.br.mercado.base.BaseFragment
import com.br.mercado.ui.adapters.ShoppingCartAdapter
import com.br.mercado.ui.itemtouchhelpcallback.CartItemTouchHelperCallback
import com.br.mercado.utils.ARG_ID
import kotlinx.android.synthetic.main.app_action_bar.*
import kotlinx.android.synthetic.main.shopping_cart_fragment.*
import java.text.NumberFormat

class ShoppingCartFragment : BaseFragment() {

    private var id: Long = 0

    companion object {
        @JvmStatic
        fun newInstance(id: Long) =
            ShoppingCartFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ID, id)
                }
            }
    }

    private lateinit var viewModelFactory: ShoppingCartViewModelFactory
    private lateinit var viewModel: ShoppingCartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shopping_cart_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            id = it.getLong(ARG_ID)
        }

        // Set appActionBar
        setAppActionBar(R.string.shoppingcart_title, true,true)

        viewModelFactory = ShoppingCartViewModelFactory(App.injectShoppingCartRepository())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ShoppingCartViewModel::class.java)

        initObserveShoppingCart()

        btnAddItem.setOnClickListener {
            pushFragment(ShoppingCartAddFragment.newInstance(id), "ShoppingCartAddFragment")
        }

        txtSearch.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Search item
                viewModel.getFindCart(id, "%${txtSearch.text}%")
                //Perform Code
                return@OnKeyListener true
            }
            false
        })
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        refresh()
    }

    private fun refresh() {
        loader.visibility = View.VISIBLE
        viewModel.getCart(id)
    }

    private fun initObserveShoppingCart(){

        viewModel.cart?.observe(this, Observer {
            loader.visibility = View.GONE
            if(it.isNotEmpty()){
                RecyclerViewShoppingCart.apply {
                    layoutManager = LinearLayoutManager(this.context)
                    adapter = ShoppingCartAdapter(it) { itemCart ->
                        // Edit
                        pushFragment(ShoppingCartEditFragment.newInstance(itemCart.id), "ShoppingCartEditFragment")
                    }
                }
                val itemTouchHelper = ItemTouchHelper(CartItemTouchHelperCallback(it) { idReg ->
                    // Remove
                    viewModel.deleteItemCart(idReg)
                    refresh()
                })
                itemTouchHelper.attachToRecyclerView(RecyclerViewShoppingCart)

                val currencyFormat = NumberFormat.getCurrencyInstance()
                val total = viewModel.sumTotal(it)
                viewModel.updateShoppingItem(id,total)
                txtTotal.text = currencyFormat.format(total)
            }
        })

        viewModel.getCart(id)

    }

}
