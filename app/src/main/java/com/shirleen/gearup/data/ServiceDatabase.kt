package com.shirleen.gearup.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shirleen.gearup.model.Service

@Database(entities = [Service::class], version = 1, exportSchema = false)
abstract class GearUpDatabase : RoomDatabase() {
    abstract fun serviceDao(): ServiceDao
}
