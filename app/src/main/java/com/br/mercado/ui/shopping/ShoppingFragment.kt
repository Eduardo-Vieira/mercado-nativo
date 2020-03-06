package com.br.mercado.ui.shopping

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.br.mercado.App

import com.br.mercado.R
import com.br.mercado.ui.adapters.ShoppingAdapter
import com.br.mercado.ui.shoppingcart.ShoppingCartFragment
import com.br.mercado.base.BaseFragment
import kotlinx.android.synthetic.main.shopping_fragment.*

class ShoppingFragment : BaseFragment() {

    companion object {
        fun newInstance() = ShoppingFragment()
    }

    private lateinit var viewModelFactory: ShoppingViewModelFactory

    private lateinit var viewModel: ShoppingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shopping_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set appActionBar
        setAppActionBar(R.string.shopping_title, false)

        viewModelFactory = ShoppingViewModelFactory(App.injectShoppingRepository())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ShoppingViewModel::class.java)

        initObserveShopping()

        floatingActionButton.setOnClickListener {
            pushFragment(ShoppingAddFragment.newInstance(),"ShoppingAddFragment")
        }

    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        refresh()
    }

    private fun refresh() {
        viewModel.getShopping()
    }

    private fun initObserveShopping(){
        viewModel.shopping?.observe(this, Observer {
            if(!it.isEmpty()){
                RecyclerViewShopping.apply {
                    layoutManager = LinearLayoutManager(this.context)
                    adapter = ShoppingAdapter(it, { idReg ->
                        // Push Fragment
                        pushFragment(ShoppingCartFragment.newInstance(idReg), "ShoppingCardFragment")
                    }, {idReg ->
                        // Remove
                        viewModel.deleteItemShopping(idReg)
                        refresh()
                    }, { itemShopping ->
                        // Edit
                        pushFragment(ShoppingEditFragment.newInstance(itemShopping.id), "ShoppingEditFragment")
                    })
                }
            }
        })
        viewModel.getShopping()
    }

}
