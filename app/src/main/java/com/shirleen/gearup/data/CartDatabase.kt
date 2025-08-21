package com.shirleen.gearup.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shirleen.gearup.data.CartDao
import com.shirleen.gearup.model.CartItem

/**
 * The Room database for the GearUp application.
 * This class provides a singleton instance of the database.
 */
@Database(entities = [CartItem::class], version = 1, exportSchema = false)
abstract class CartDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var Instance: CartDatabase? = null

        /**
         * Returns a singleton instance of the database.
         * The synchronized block ensures that only one thread can access this code at a time,
         * preventing multiple database instances from being created.
         * @param context The application context.
         * @return A singleton CartDatabase instance.
         */
        fun getDatabase(context: Context): CartDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, CartDatabase::class.java, "cart_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
