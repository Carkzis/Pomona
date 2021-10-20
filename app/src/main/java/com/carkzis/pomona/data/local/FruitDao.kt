package com.carkzis.pomona.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.carkzis.pomona.data.local.DatabaseFruit
import kotlinx.coroutines.flow.Flow

@Dao
interface FruitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFruit(fruit: List<DatabaseFruit>)

    @Query("SELECT * FROM DatabaseFruit")
    fun getAllFruit() : Flow<List<DatabaseFruit>>

}