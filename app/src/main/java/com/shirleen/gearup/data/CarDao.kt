package com.shirleen.gearup.data

import androidx.room.*
import com.shirleen.gearup.model.Car

@Dao
interface CarDao {

    // Insert a car
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCar(car: Car)

    // Insert multiple cars at once
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCars(cars: List<Car>)

    // Update a car
    @Update
    suspend fun updateCar(car: Car)

    // Delete a car
    @Delete
    suspend fun deleteCar(car: Car)

    // Delete all cars
    @Query("DELETE FROM cars")
    suspend fun deleteAllCars()

    // Get all cars
    @Query("SELECT * FROM cars ORDER BY brand ASC")
    suspend fun getAllCars(): List<Car>

    // Get a car by ID
    @Query("SELECT * FROM cars WHERE id = :carId LIMIT 1")
    suspend fun getCarById(carId: Int): Car?

    // Search cars by brand or type
    @Query("SELECT * FROM cars WHERE brand LIKE '%' || :searchQuery || '%' OR model LIKE '%' || :searchQuery || '%'")
    suspend fun searchCars(searchQuery: String): List<Car>
}