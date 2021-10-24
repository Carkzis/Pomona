package com.carkzis.pomona.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * This database holds the DatabaseFruit entities.
 */
@Database(entities = [DatabaseFruit::class], version = 1, exportSchema = false)
abstract class PomonaDatabase : RoomDatabase() {

    abstract fun fruitDao() : FruitDao

}