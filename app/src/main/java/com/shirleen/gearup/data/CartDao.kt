package com.shirleen.gearup.data


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.shirleen.gearup.model.CartItem
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the CartItem entity.
 * This interface defines the database operations for the cart table.
 */
@Dao
interface CartDao {

    /**
     * Inserts a new cart item into the database.
     * @param item The CartItem to be inserted.
     */
    @Insert
    suspend fun insert(item: CartItem)

    /**
     * Deletes a cart item from the database.
     * @param item The CartItem to be deleted.
     */
    @Delete
    suspend fun delete(item: CartItem)

    /**
     * Gets all cart items from the database.
     * The returned Flow will emit new data whenever the table changes.
     * @return A Flow of a list of CartItem.
     */
    @Query("SELECT * FROM cart_table")
    fun getAllItems(): Flow<List<CartItem>>

    /**
     * Deletes all items from the cart.
     */
    @Query("DELETE FROM cart_table")
    suspend fun deleteAllItems()
}
