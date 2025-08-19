package com.shirleen.gearup.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accessories")
data class Accessories(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: String,
    val description: String,
    val price: String,
    val availability: String,
    val phone: String,
    val imageUri: String,
)
