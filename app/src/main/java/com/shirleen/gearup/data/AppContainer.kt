package com.shirleen.gearup.data


import android.content.Context
import com.shirleen.gearup.data.CartDatabase
import com.shirleen.gearup.repository.CartRepository

/**
 * A simple dependency injection container at the application level.
 * This object holds and provides the singleton instances of the database and repository.
 */
interface AppContainer {
    val cartRepository: CartRepository
}

/**
 * Implementation for the AppContainer that uses the Room database.
 */
class AppDataContainer(private val context: Context) : AppContainer {

    // The Room database instance. We use lazy initialization to create it only when needed.
    private val database by lazy { CartDatabase.getDatabase(context) }

    /**
     * The repository for cart data.
     * It uses a lazy delegate to ensure the database instance is available
     * before the repository is created.
     */
    override val cartRepository: CartRepository by lazy {
        CartRepository(database.cartDao())
    }
}
