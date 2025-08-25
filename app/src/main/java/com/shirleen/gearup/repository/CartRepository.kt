package com.shirleen.gearup.repository

import com.shirleen.gearup.data.CartDao
import com.shirleen.gearup.model.CartItem

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {

    val allCartItems: LiveData<List<CartItem>> = cartDao.getAllCartItems()
    val totalPrice: LiveData<Double> = cartDao.getTotalPrice()

    suspend fun insert(cartItem: CartItem) {
        cartDao.insertCartItem(cartItem)
    }

    suspend fun update(cartItem: CartItem) {
        cartDao.updateCartItem(cartItem)
    }

    suspend fun delete(cartItem: CartItem) {
        cartDao.deleteCartItem(cartItem)
    }

    suspend fun clear() {
        cartDao.clearCart()
    }
}
