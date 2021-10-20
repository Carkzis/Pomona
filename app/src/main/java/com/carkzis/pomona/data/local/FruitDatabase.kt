package com.carkzis.pomona.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carkzis.pomona.data.local.DatabaseFruit
import com.carkzis.pomona.data.local.FruitDao

@Database(entities = [DatabaseFruit::class], version = 0, exportSchema = false)
abstract class PomonaDatabase : RoomDatabase() {

    abstract fun fruitDao() : FruitDao

}