package com.shirleen.gearup.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class representing a single item in the cart.
 * This class is also an entity in the Room database.
 *
 * @param id The unique identifier for the item. The primary key.
 * @param brand The brand name of the item (e.g., "Ford").
 * @param model The model name of the item (e.g., "Mustang GT").
 * @param price The price of the item as a string (e.g., "$55,000").
 */
@Entity(tableName = "cart_table")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val brand: String,
    val model: String,
    val price: String
)

