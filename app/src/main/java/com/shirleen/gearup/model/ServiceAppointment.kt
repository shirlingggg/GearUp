package com.shirleen.gearup.model

// ServiceAppointment.kt

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "service_appointments",
    foreignKeys = [
        ForeignKey(
            entity = ServiceProvider::class,
            parentColumns = ["id"],
            childColumns = ["serviceProviderId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ServiceAppointment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val serviceProviderId: Int,
    val customerName: String,
    val customerPhone: String,
    val customerEmail: String,
    val serviceType: String,
    val appointmentDate: String, // Format: "yyyy-MM-dd HH:mm"
    val vehicleDetails: String,
    val serviceDescription: String,
    val status: String = "Pending", // Pending, Confirmed, Completed, Cancelled
    val createdAt: Long = System.currentTimeMillis()
)
