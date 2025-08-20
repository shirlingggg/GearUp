package com.shirleen.gearup.repository

import com.shirleen.gearup.data.ServiceDao
import com.shirleen.gearup.model.Service

class ServiceRepository(private val dao: ServiceDao) {

    suspend fun insertService(service: Service) = dao.insertService(service)

    suspend fun updateService(service: Service) = dao.updateService(service)

    suspend fun deleteService(service: Service) = dao.deleteService(service)

    suspend fun getAllServices() = dao.getAllServices()

    suspend fun getServiceById(id: Int) = dao.getServiceById(id)
}
