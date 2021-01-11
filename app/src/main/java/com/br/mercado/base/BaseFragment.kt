package com.br.mercado.base

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.br.mercado.R

open class BaseFragment : Fragment() {

    fun pushFragment(fragment: Fragment){
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right,
                R.anim.slide_in_left, R.anim.slide_out_right)
            ?.replace(R.id.fragmentLayouts, fragment , fragment.tag)
            ?.addToBackStack(null)
            ?.commit()
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

    fun setAppActionBar(id:Int, popBackStack: Boolean = false){
        val supportActionBar = (activity as AppCompatActivity).supportActionBar
        supportActionBar?.apply {
            title = resources.getString(id)
            setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp)
            setDisplayHomeAsUpEnabled(popBackStack)
            setDisplayShowHomeEnabled(popBackStack)
        }
    }
}