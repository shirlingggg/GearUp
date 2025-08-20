package com.shirleen.gearup.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "services")
data class Service(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,           // e.g. "Oil Change"
    val price: String,          // you can keep 0.0 if not needed
    val duration: String        // e.g. "1 hr"
)

