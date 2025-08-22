package com.shirleen.gearup.repository

import com.shirleen.gearup.data.CartDao
import com.shirleen.gearup.model.CartItem
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {

    val allItems: Flow<List<CartItem>> = cartDao.getAllItems()
    val subtotal: Flow<Double?> = cartDao.getSubtotal()

    suspend fun insertItem(item: CartItem) {
        cartDao.insertItem(item)
    }

    suspend fun deleteItem(item: CartItem) {
        cartDao.deleteItem(item)
    }
}