package com.br.mercado.ui.shoppingcart

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.br.mercado.App
import com.br.mercado.R
import com.br.mercado.base.BaseFragment
import com.br.mercado.data.model.Cart
import com.br.mercado.ui.adapters.ShoppingCartAdapter
import com.br.mercado.ui.itemtouchhelpcallback.CartItemTouchHelperCallback
import com.br.mercado.utils.ARG_ID
import kotlinx.android.synthetic.main.shopping_cart_fragment.*
import java.text.NumberFormat

class ShoppingCartFragment : BaseFragment() {

    private var id: Long = 0

    private lateinit var adapter:ShoppingCartAdapter

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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_cart, menu)
        val searchItem = menu?.findItem(R.id.app_bar_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                //Search item
                viewModel.getFindCart(id, "%${newText}%")
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.shopping_cart_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            id = it.getLong(ARG_ID)
        }

        // Set appActionBar
        setAppActionBar(R.string.shoppingcart_title, true)

        viewModelFactory = ShoppingCartViewModelFactory(App.injectShoppingCartRepository())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ShoppingCartViewModel::class.java)

        configRecycler()

        initObserveShoppingCart()

        btnAddItem.setOnClickListener {
            pushFragment(ShoppingCartAddFragment.newInstance(id))
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        refresh()
    }

    private fun configRecycler(){
        adapter = ShoppingCartAdapter { itemCart ->
            // Edit
            pushFragment(ShoppingCartEditFragment.newInstance(itemCart.id))
        }

        RecyclerViewShoppingCart.layoutManager = LinearLayoutManager(this.context)
        RecyclerViewShoppingCart.adapter = this.adapter
        val itemTouchHelper = ItemTouchHelper(
            CartItemTouchHelperCallback(this.requireContext()){ position ->
            // Remove
            viewModel.deleteItemCart(adapter.getItemId(position))
            refresh()
        })
        itemTouchHelper.attachToRecyclerView(RecyclerViewShoppingCart)
    }

    private fun refresh() {
        loader.visibility = View.VISIBLE
        viewModel.getCart(id)
    }

    private fun initObserveShoppingCart(){

        viewModel.cart?.observe(this, Observer {
            loader.visibility = View.GONE
            if(it.isNotEmpty()){
                adapter.update(it)
                sumTotal(it)
            }
        })

        viewModel.getCart(id)

    }

    private fun sumTotal(it: List<Cart>) {
        val currencyFormat = NumberFormat.getCurrencyInstance()
        val total = viewModel.sumTotal(it)
        viewModel.updateShoppingItem(id, total)
        txtTotal.text = currencyFormat.format(total)
    }

}
