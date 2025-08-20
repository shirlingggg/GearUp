package com.shirleen.gearup.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shirleen.gearup.model.Accessory

@Database(entities = [Accessory::class], version = 1, exportSchema = false)
abstract class AccessoryDatabase : RoomDatabase() {

    abstract fun accessoryDao(): AccessoryDao

    companion object {
        @Volatile
        private var INSTANCE: AccessoryDatabase? = null

        fun getDatabase(context: Context): AccessoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AccessoryDatabase::class.java,
                    "accessory_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}