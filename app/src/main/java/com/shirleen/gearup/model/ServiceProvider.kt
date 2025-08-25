package com.shirleen.gearup.model

// ServiceProvider.kt

import androidx.room.Entity
import androidx.room.PrimaryKey

// ServiceProvider.kt (enhanced)
@Entity(tableName = "service_providers")
data class ServiceProvider(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val serviceType: String,
    val location: String,
    val phone: String,
    val email: String,
    val rating: Float = 0.0f,
    val description: String,
    val hourlyRate: String = "",
    val experience: String = "",
    val servicesOffered: String = "", // Comma-separated services
    val workingHours: String = "",
    val imageUri: String? = null,
    val userId: String
)
