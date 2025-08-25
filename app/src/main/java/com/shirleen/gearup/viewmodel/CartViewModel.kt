package com.shirleen.gearup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shirleen.gearup.repository.CartRepository
import com.shirleen.gearup.model.CartItem
import kotlinx.coroutines.launch

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers



class CartViewModel(private val repository: CartRepository) : ViewModel() {

    val allCartItems: LiveData<List<CartItem>> = repository.allCartItems
    val totalPrice: LiveData<Double> = repository.totalPrice

    fun insert(cartItem: CartItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(cartItem)
    }

    fun update(cartItem: CartItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(cartItem)
    }

    fun delete(cartItem: CartItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(cartItem)
    }

    fun clearCart() = viewModelScope.launch(Dispatchers.IO) {
        repository.clear()
    }

    // ✅ Factory class inside the same file
    class CartViewModelFactory(private val repository: CartRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CartViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
