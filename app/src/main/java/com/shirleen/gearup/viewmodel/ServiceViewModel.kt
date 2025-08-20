package com.shirleen.gearup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shirleen.gearup.model.Service
import com.shirleen.gearup.repository.ServiceRepository

class ServiceViewModel(private val repository: ServiceRepository) : ViewModel() {

    private val _services = MutableLiveData<List<Service>>()
    val services: LiveData<List<Service>> get() = _services

    fun loadServices() {
        viewModelScope.launch {
            _services.value = repository.getAllServices()
        }
    }

    fun addService(service: Service) {
        viewModelScope.launch {
            repository.insertService(service)
            loadServices()
        }
    }

    fun updateService(service: Service) {
        viewModelScope.launch {
            repository.updateService(service)
            loadServices()
        }
    }

    fun deleteService(service: Service) {
        viewModelScope.launch {
            repository.deleteService(service)
            loadServices()
        }
    }
}
