package com.shirleen.gearup.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accessories")
data class Accessory(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    override val name: String,
    val description: String,
    override val price: String,
    val phone: String,
    override val imageUri: String,
): CartEligibleItem
