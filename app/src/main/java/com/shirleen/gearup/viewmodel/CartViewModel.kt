package com.shirleen.gearup.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.shirleen.gearup.model.CartItem

/**
 * ViewModel for the CartScreen.
 * This simplified ViewModel holds the state of the cart directly
 * and provides methods to modify it without a database or repository.
 */
class CartViewModel : ViewModel() {

    // The list of cart items. It is observable,
    // so the UI will automatically update when it changes.
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> get() = _cartItems

    // The subtotal of all items in the cart.
    // We'll calculate this directly from the list.
    private var _subtotal: Double = 0.0
    val subtotal: Double get() = _subtotal

    init {
        // Dummy data for initial demonstration.
        _cartItems.addAll(
            listOf(
                CartItem(1, "Ford", "Mustang GT", "Ksh 1,999,000"),
                CartItem(2, "Brembo", "Brake Pads", "Ksh 2,000"),
                CartItem(3, "Honda", "Civic Sport", "Ksh 2,800,000")
            )
        )
        updateSubtotal()
    }

    /**
     * Adds an item to the cart.
     * @param item The CartItem to be added.
     */
    fun addItem(item: CartItem) {
        _cartItems.add(item)
        updateSubtotal()
    }

    /**
     * Removes an item from the cart.
     * @param item The CartItem to be removed.
     */
    fun removeItem(item: CartItem) {
        _cartItems.remove(item)
        updateSubtotal()
    }

    /**
     * Calculates the subtotal by parsing the price strings and summing them.
     */
    private fun updateSubtotal() {
        val sum = _cartItems.sumOf {
            // Remove "$" and "," from the string and convert to Double.
            it.price.replace("Ksh", "").replace(",", "").toDoubleOrNull() ?: 0.0
        }
        _subtotal = sum
    }
}
