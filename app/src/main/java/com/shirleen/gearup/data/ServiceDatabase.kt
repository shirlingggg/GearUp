package com.shirleen.gearup.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shirleen.gearup.model.ServiceAppointment
import com.shirleen.gearup.model.ServiceProvider

// AppDatabase.kt (add to existing)
@Database(
    entities = [ServiceProvider::class, ServiceAppointment::class],
    version = 1, // Increment version if you're modifying existing database
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun serviceProviderDao(): ServiceProviderDao
    abstract fun serviceAppointmentDao(): ServiceAppointmentDao

    // ... existing functions
}