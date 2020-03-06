package com.br.mercado.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Shopping (
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0,
    val dateCreated:String?,
    var descriptionNote:String,
    val sumTotal:Float = 0F
)