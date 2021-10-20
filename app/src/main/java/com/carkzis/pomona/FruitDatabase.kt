package com.carkzis.pomona

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DatabaseFruit::class], version = 0, exportSchema = false)
abstract class PomonaDatabase : RoomDatabase() {

    abstract fun fruitDao() : FruitDao

}