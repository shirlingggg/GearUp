package com.shirleen.gearup.data

// ServiceAppointmentDao.kt

import androidx.room.*
import com.shirleen.gearup.model.ServiceAppointment
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceAppointmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: ServiceAppointment)

    @Update
    suspend fun updateAppointment(appointment: ServiceAppointment)

    @Delete
    suspend fun deleteAppointment(appointment: ServiceAppointment)

    @Query("SELECT * FROM service_appointments WHERE serviceProviderId = :providerId ORDER BY createdAt DESC")
    fun getAppointmentsByProviderId(providerId: Int): Flow<List<ServiceAppointment>>

    @Query("SELECT * FROM service_appointments WHERE id = :appointmentId")
    fun getAppointmentById(appointmentId: Int): Flow<ServiceAppointment?>

    @Query("UPDATE service_appointments SET status = :status WHERE id = :appointmentId")
    suspend fun updateAppointmentStatus(appointmentId: Int, status: String)
}