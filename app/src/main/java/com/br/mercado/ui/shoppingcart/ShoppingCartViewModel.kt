package com.br.mercado.ui.shoppingcart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.br.mercado.data.model.Cart
import com.br.mercado.data.repository.ShoppingCartRepository
import javax.inject.Inject

class ShoppingCartViewModel @Inject constructor(private val repository: ShoppingCartRepository) : ViewModel() {
    var itemCart : MutableLiveData<Cart> = MutableLiveData()
    var cart : MutableLiveData<List<Cart>>? = MutableLiveData()

    fun getCart(id:Long){
        repository.getCart(id,listCart = {
            cart?.postValue(it)
        })
    }

    fun getFindCart(id:Long, search: String){
        repository.getFindCart(id, search, listCart = {
            cart?.postValue(it)
        })
    }

    fun getCartRow(id: Long){
        repository.getCartRow(id, rowShopping = {
            itemCart?.postValue(it)
        })
    }

    fun insertCart(data:Cart){
        repository.insertCart(data)
    }

    fun updateCart(data:Cart){
        repository.updateCart(data)
    }

    fun deleteItemCart(id:Long){
        repository.deleteItemCart(id)
    }

    fun updateShoppingItem(id: Long,total:Float){
        repository.updateShoppingItem(id,total)
    }

    fun sumTotal(data:List<Cart>):Float {
        var sum:Float = 0F
        for (item in data){
            sum = sum + (item.price * item.qty)
        }
        return sum
    }
}
