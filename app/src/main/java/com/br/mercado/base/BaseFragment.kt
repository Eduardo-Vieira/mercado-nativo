package com.br.mercado.base

import androidx.fragment.app.Fragment
import com.br.mercado.R
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.app_action_bar.*

open class BaseFragment : Fragment() {

    fun pushFragment(fragment: Fragment, tag:String){
        activity!!.supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,
                R.anim.slide_in_left, R.anim.slide_out_right)
            .replace(R.id.fragmentLayouts, fragment , tag)
            .addToBackStack(null)
            .commit()
    }

    fun popFragment(){
        activity!!.supportFragmentManager.popBackStack()
    }

    //hide keyboard
    fun hideKeyboard(ctx: Context) {
        val inputManager = ctx
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // check if no view has focus:
        val v = (ctx as Activity).currentFocus ?: return

        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

    fun setAppActionBar(id:Int, popBackStack: Boolean = false, searchButton: Boolean = false){
        // Set appActionBar
        barTitle.text = resources.getString(id)
        btnPopBackStack.visibility = if(popBackStack) View.VISIBLE else View.GONE
        btnSearch.visibility = if(searchButton) View.VISIBLE else View.GONE
        // PopBackStack
        btnPopBackStack.setOnClickListener {
            this.popFragment()
        }
        // Search
        btnSearch.setOnClickListener {
            txtSearch.visibility = View.VISIBLE
            barTitle.visibility = View.GONE
            btnSearch.visibility = View.GONE
            btnClosed.visibility =View.VISIBLE
        }
        // Close
        btnClosed.setOnClickListener {
            txtSearch.visibility = View.GONE
            txtSearch.setText("")
            barTitle.visibility = View.VISIBLE
            btnSearch.visibility = View.VISIBLE
            btnClosed.visibility =View.GONE
        }
    }
}