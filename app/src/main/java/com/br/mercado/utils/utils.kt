package com.br.mercado.utils

import java.text.DateFormat
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class utils {

    fun  getCurrentDateTime(): String? {
        val calendar = Calendar.getInstance()
        val dateNow = DateFormat.getDateInstance().format(calendar.time)
        return dateNow
    }
    fun getCurrencyFormat(valor: Number): String? {
        val currencyFormat = DecimalFormat.getCurrencyInstance()
        return currencyFormat.format(valor)
    }
    fun getNumberFormat(valor: Number): String? {
        val numberFormat = NumberFormat.getNumberInstance()
        return numberFormat.format(valor)
    }
}