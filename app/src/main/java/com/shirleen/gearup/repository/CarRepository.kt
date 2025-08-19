package com.shirleen.gearup.repository

import com.shirleen.gearup.data.CarDao
import com.shirleen.gearup.model.Car
import kotlinx.coroutines.flow.Flow

class CarRepository(private val carDao: CarDao) {

    // Fetch all cars from the database
    fun getAllCars(): Flow<List<Car>> {
        return carDao.getAllCars()
    }

    // Add a new car to the database
    suspend fun addCar(car: Car) {
        carDao.addCar(car)
    }

    // Update an existing car
    suspend fun updateCar(car: Car) {
        carDao.updateCar(car)
    }

    // Delete a car from the database
    suspend fun deleteCar(car: Car) {
        carDao.deleteCar(car)
    }
}