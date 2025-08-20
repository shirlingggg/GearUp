package com.shirleen.gearup.repository


import com.shirleen.gearup.data.AccessoryDao
import com.shirleen.gearup.model.Accessory
import kotlinx.coroutines.flow.Flow

class AccessoryRepository(private val accessoryDao: AccessoryDao) {

    val allAccessories: Flow<List<Accessory>> = accessoryDao.getAllAccessories()

    suspend fun addAccessory(accessory: Accessory) {
        accessoryDao.addAccessory(accessory)
    }

    suspend fun updateAccessory(accessory: Accessory) {
        accessoryDao.updateAccessory(accessory)
    }

    suspend fun deleteAccessory(accessory: Accessory) {
        accessoryDao.deleteAccessory(accessory)
    }
}