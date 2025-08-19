package com.shirleen.gearup.data

import androidx.room.*
import com.shirleen.gearup.model.Car
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    // This function must return a Flow<List<Car>> and NOT be a suspend function.
    @Query("SELECT * FROM cars")
    fun getAllCars(): Flow<List<Car>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCar(car: Car)

    @Update
    suspend fun updateCar(car: Car)

    @Delete
    suspend fun deleteCar(car: Car)
}