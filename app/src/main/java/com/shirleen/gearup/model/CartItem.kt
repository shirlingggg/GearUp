package com.shirleen.gearup.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val price: String,
    val phone: String,
    val imageUri: String,
    var quantity: Int = 1
) {
    fun totalPrice(): Double {
        return (price.toDoubleOrNull() ?: 0.0) * quantity
    }
}
