package com.br.mercado.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.br.mercado.R
import com.br.mercado.data.model.Cart
import com.br.mercado.utils.utils
import kotlinx.android.synthetic.main.shopping_cart_row.view.*
import java.text.NumberFormat

class ShoppingCartAdapter(private var cart:List<Cart> = arrayListOf(),
                          private val onEditClickListener: (itemCart: Cart) -> Unit): RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_cart_row,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = cart.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtDescription.text = cart[position].description
        holder.txtQty.text = utils().getNumberFormat(cart[position].qty)
        holder.txtPrice.text = utils().getCurrencyFormat(cart[position].price)
        holder.calcTotal.text = utils().getCurrencyFormat(cart[position].qty * cart[position].price)

        // funcao do botao Edit
        holder.itemView.setOnClickListener{
            onEditClickListener(cart[position])
        }
    }

    fun update(cart:List<Cart>){
        this.cart = cart
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return this.cart.get(position).id
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtDescription: TextView = itemView.txtDescription
        val txtQty: TextView = itemView.txtQty
        val txtPrice: TextView = itemView.txtPrice
        val calcTotal: TextView = itemView.calcTotal
    }
}