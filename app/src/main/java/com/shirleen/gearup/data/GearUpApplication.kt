package com.shirleen.gearup.data


import android.app.Application
import com.shirleen.gearup.data.AppDataContainer
import com.shirleen.gearup.data.AppContainer

/**
 * Custom Application class for the GearUp app.
 * This class is the entry point for the app and is used to initialize
 * the dependency injection container.
 */
class GearUpApplication : Application() {

    /**
     * AppContainer instance used for dependency injection.
     * We will make this available to the rest of the app for easy access.
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
