package com.shirleen.gearup.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.shirleen.gearup.model.Car
import com.shirleen.gearup.repository.CarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// CarViewModel handles UI-related data and logic
class CarViewModel(private val repository: CarRepository) : ViewModel() {

    // A private MutableStateFlow to hold the list of cars.
    private val _cars = MutableStateFlow<List<Car>>(emptyList())

    // A public StateFlow that the UI can observe. It's read-only.
    val cars: StateFlow<List<Car>> = _cars.asStateFlow()

    // Initialize the ViewModel by collecting all cars from the repository.
    init {
        viewModelScope.launch {
            repository.getAllCars().collect { fetchedCars ->
                _cars.value = fetchedCars
            }
        }
    }

    // Function to add a new car. It launches a coroutine to call the repository.
    fun addCar(car: Car) {
        viewModelScope.launch {
            repository.addCar(car)
        }
    }

    // Function to update an existing car.
    fun updateCar(car: Car) {
        viewModelScope.launch {
            repository.updateCar(car)
        }
    }

    // Function to delete a car.
    fun deleteCar(car: Car) {
        viewModelScope.launch {
            repository.deleteCar(car)
        }
    }
}

// ViewModelFactory is needed to pass the repository to the ViewModel
class CarViewModelFactory(private val repository: CarRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}