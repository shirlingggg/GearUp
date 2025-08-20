package com.shirleen.gearup.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shirleen.gearup.model.Accessory
import kotlinx.coroutines.flow.Flow

@Dao
interface AccessoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAccessory(accessory: Accessory)

    @Query("SELECT * FROM accessories ORDER BY id ASC")
    fun getAllAccessories(): Flow<List<Accessory>>

    @Update
    suspend fun updateAccessory(accessory: Accessory)

    @Delete
    suspend fun deleteAccessory(accessory: Accessory)

    @Query("SELECT * FROM accessories WHERE id = :id")
    fun getAccessory(id: Int): Flow<Accessory>
}