package com.br.mercado.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cart (
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    val idShopping:Long = 0,
    var description: String,
    var qty:Float = 0F,
    var price:Float = 0F
)