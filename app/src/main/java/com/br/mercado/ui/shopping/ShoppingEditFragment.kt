package com.br.mercado.ui.shopping

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.br.mercado.App
import com.br.mercado.R
import com.br.mercado.base.BaseFragment
import com.br.mercado.data.model.Shopping
import com.br.mercado.utils.ARG_ID
import kotlinx.android.synthetic.main.shopping_add_fragment.*
import javax.inject.Inject

class ShoppingEditFragment : BaseFragment() {

    private var id: Long = 0
    private lateinit var itemShopping:Shopping

    private lateinit var viewModelFactory: ShoppingViewModelFactory
    private lateinit var viewModel: ShoppingViewModel

    companion object {
        @JvmStatic
        fun newInstance(id: Long) =
            ShoppingEditFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_ID, id)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shopping_edit_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            id = it.getLong(ARG_ID)
        }

        viewModelFactory = ShoppingViewModelFactory(App.injectShoppingRepository())

        viewModel = ViewModelProviders.of(this,viewModelFactory).get(ShoppingViewModel::class.java)

        // Set appActionBar
        setAppActionBar(R.string.shoppingedit_title, true)

        // open data
        initObserveShopping()

        // Save data in db
        btnSave.setOnClickListener {
            itemShopping.descriptionNote = inputDescriptionNote.text.toString()
            viewModel.updateShopping(itemShopping) // save data
            hideKeyboard(this.context!!) // hide keyboard
            popFragment() // back fragment
        }
    }

    private fun initObserveShopping(){
        viewModel.itemShopping?.observe(this, Observer {
            itemShopping = it
            inputDescriptionNote.setText(it.descriptionNote)
        })
        viewModel.getShoppingRow(id)
    }

}
