package com.br.mercado.ui.itemtouchhelpcallback

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.br.mercado.data.model.Cart

class CartItemTouchHelperCallback(private val cart:List<Cart>, private val onRemoveClickListener: (idReg: Long) -> Unit): ItemTouchHelper.Callback() {
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
       return makeMovementFlags(0,ItemTouchHelper.LEFT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position:Int = viewHolder.adapterPosition
        onRemoveClickListener(cart[position].id)

    }

}
