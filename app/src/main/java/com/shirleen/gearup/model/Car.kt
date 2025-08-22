package com.shirleen.gearup.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class Car(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    val brand: String,
    val model: String,
    val yearOfManufacture: String,
    val mileage: String,
    override val price: String,
    val phone: String,
    override val imageUri: String,
): CartEligibleItem {
    override val name: String
        get() = "$brand $model"
}