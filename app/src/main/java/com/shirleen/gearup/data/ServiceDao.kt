package com.shirleen.gearup.data

import androidx.room.*
import com.shirleen.gearup.model.Service

@Dao
interface ServiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(service: Service)

    @Update
    suspend fun updateService(service: Service)

    @Delete
    suspend fun deleteService(service: Service)

    @Query("SELECT * FROM services ORDER BY name ASC")
    suspend fun getAllServices(): List<Service>

    @Query("SELECT * FROM services WHERE id = :id")
    suspend fun getServiceById(id: Int): Service?
}
