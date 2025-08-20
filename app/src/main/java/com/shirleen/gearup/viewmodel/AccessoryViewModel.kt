package com.shirleen.gearup.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shirleen.gearup.model.Accessory
import com.shirleen.gearup.repository.AccessoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing accessory data.
 * @param accessoryRepository The repository to handle data operations.
 */
class AccessoryViewModel(private val accessoryRepository: AccessoryRepository) : ViewModel() {

    // A flow of all accessories from the repository.
    val allAccessories: Flow<List<Accessory>> = accessoryRepository.allAccessories

    /**
     * Adds a new accessory to the database.
     * The operation is launched in a coroutine on the IO dispatcher to not block the UI.
     * @param accessory The accessory to add.
     */
    fun addAccessory(accessory: Accessory) {
        viewModelScope.launch(Dispatchers.IO) {
            accessoryRepository.addAccessory(accessory)
        }
    }

    /**
     * Updates an existing accessory in the database.
     * The operation is launched in a coroutine on the IO dispatcher.
     * @param accessory The accessory to update.
     */
    fun updateAccessory(accessory: Accessory) {
        viewModelScope.launch(Dispatchers.IO) {
            accessoryRepository.updateAccessory(accessory)
        }
    }

    /**
     * Deletes an accessory from the database.
     * The operation is launched in a coroutine on the IO dispatcher.
     * @param accessory The accessory to delete.
     */
    fun deleteAccessory(accessory: Accessory) {
        viewModelScope.launch(Dispatchers.IO) {
            accessoryRepository.deleteAccessory(accessory)
        }
    }
}

/**
 * A factory for creating an instance of AccessoryViewModel.
 * This is needed because the ViewModel has a constructor with a parameter (the repository).
 */
class AccessoryViewModelFactory(private val repository: AccessoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccessoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AccessoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
