package com.br.mercado.ui.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.br.mercado.App
import com.br.mercado.R
import com.br.mercado.base.BaseFragment
import com.br.mercado.data.model.Shopping
import com.br.mercado.utils.utils
import kotlinx.android.synthetic.main.shopping_add_fragment.*

class ShoppingAddFragment : BaseFragment() {

    companion object {
        fun newInstance() = ShoppingAddFragment()
    }

    private lateinit var viewModelFactory: ShoppingViewModelFactory
    private lateinit var viewModel: ShoppingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.shopping_add_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setAppActionBar(R.string.shoppingadd_title, true)

        viewModelFactory = ShoppingViewModelFactory(App.injectShoppingRepository())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ShoppingViewModel::class.java)

        // Save data in db
        btnSave.setOnClickListener {
            val dateNow = utils().getCurrentDateTime()
            val data = Shopping(0, dateNow ,inputDescriptionNote.text.toString(),0F)
            viewModel.insertShopping(data) // save data
            hideKeyboard(this.context!!) // hide keyboard
            popFragment() // back fragment
        }

    }
}
