package com.br.mercado.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.br.mercado.R
import com.br.mercado.data.model.Shopping
import com.br.mercado.utils.utils
import kotlinx.android.synthetic.main.shopping_row.view.*
import java.text.NumberFormat

class ShoppingAdapter(private val shopping:List<Shopping>,
                      private val onItemClickListener: (idReg: Long) -> Unit,
                      private val onRemoveClickListener: (idReg: Long) -> Unit,
                      private val onEditClickListener: (itemShopping: Shopping) -> Unit): RecyclerView.Adapter<ShoppingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_row,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = shopping.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Set dados na view
        holder.dateCreated.text = shopping[position].dateCreated
        holder.descriptionNote.text = shopping[position].descriptionNote
        holder.total.text = utils().getCurrencyFormat(shopping[position].sumTotal)

        // funcao do botao itens
        holder.btnItens.setOnClickListener {
            onItemClickListener(shopping[position].id)
        }

        // funcao do botao Remover
        holder.btnRemove.setOnClickListener {
            onRemoveClickListener(shopping[position].id)
        }

        // funcao do botao Edit
        holder.btnEdit.setOnClickListener {
            onEditClickListener(shopping[position])
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val dateCreated: TextView = itemView.dateCreated
        val descriptionNote: TextView = itemView.descriptionNote
        val total:TextView = itemView.total
        val btnItens:Button = itemView.btnItens
        val btnRemove:Button = itemView.btnRemove
        val btnEdit:Button = itemView.btnEdit
    }

}