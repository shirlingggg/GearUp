package com.shirleen.gearup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shirleen.gearup.repository.CartRepository
import com.shirleen.gearup.model.CartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    val allItems: Flow<List<CartItem>> = repository.allItems
    val subtotal: Flow<Double?> = repository.subtotal

    fun insertItem(item: CartItem) {
        viewModelScope.launch {
            repository.insertItem(item)
        }
    }

    fun deleteItem(item: CartItem) {
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }
}