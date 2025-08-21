package com.shirleen.gearup.repository


import com.shirleen.gearup.data.CartDao
import com.shirleen.gearup.model.CartItem
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing cart items.
 * This class provides an abstraction layer over the database operations.
 */
class CartRepository(private val cartDao: CartDao) {

    /**
     * Gets all cart items from the database.
     * @return A Flow of a list of CartItem.
     */
    val allItems: Flow<List<CartItem>> = cartDao.getAllItems()

    /**
     * Inserts a new cart item.
     * @param item The CartItem to be added.
     */
    suspend fun insert(item: CartItem) {
        cartDao.insert(item)
    }

    /**
     * Deletes a cart item.
     * @param item The CartItem to be deleted.
     */
    suspend fun delete(item: CartItem) {
        cartDao.delete(item)
    }
}
