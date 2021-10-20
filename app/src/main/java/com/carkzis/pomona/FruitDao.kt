package com.carkzis.pomona

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FruitDao {

    @Query("SELECT * FROM DatabaseFruit")
    fun getAllFruit() : Flow<List<DatabaseFruit>>

}