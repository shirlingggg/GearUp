package com.shirleen.gearup.data

// ServiceProviderDao.kt

import androidx.room.*
import com.shirleen.gearup.model.ServiceProvider
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceProviderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertServiceProvider(serviceProvider: ServiceProvider)

    @Update
    suspend fun updateServiceProvider(serviceProvider: ServiceProvider)

    @Delete
    suspend fun deleteServiceProvider(serviceProvider: ServiceProvider)

    @Query("SELECT * FROM service_providers WHERE userId = :userId")
    fun getServiceProviderByUserId(userId: String): Flow<ServiceProvider?>

    @Query("SELECT * FROM service_providers WHERE id = :providerId")
    fun getServiceProviderById(providerId: Int): Flow<ServiceProvider?>

    @Query("SELECT * FROM service_providers WHERE serviceType LIKE :searchQuery OR location LIKE :searchQuery")
    fun searchServiceProviders(searchQuery: String): Flow<List<ServiceProvider>>

    // ServiceProviderDao.kt (add these functions)
    @Query("SELECT * FROM service_providers")
    fun getAllServiceProviders(): Flow<List<ServiceProvider>>

    @Query("DELETE FROM service_providers WHERE id = :providerId")
    suspend fun deleteServiceProviderById(providerId: Int)
}