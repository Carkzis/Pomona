package com.carkzis.pomona.di

import android.content.Context
import androidx.room.Room
import com.carkzis.pomona.data.local.FruitDao
import com.carkzis.pomona.data.local.PomonaDatabase
import com.carkzis.pomona.data.Repository
import com.carkzis.pomona.data.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * This is a module for allowing Hilt to provide objects when requested.
 */
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    /**
     * This tells Hilt to provide the FruitDao when it is requested.
     */
    @Provides
    fun provideFruitDao(database: PomonaDatabase) : FruitDao {
        return database.fruitDao()
    }

    /**
     * This tells Hilt to provide the database when it is requested.
     */
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : PomonaDatabase {
        return Room.databaseBuilder(
            context,
            PomonaDatabase::class.java,
            "fruit.db",
        ).fallbackToDestructiveMigration().build()
    }

    /**
     * This tells Hilt to provide the repository when it is requested.
     */
    @Singleton
    @Provides
    fun provideRepository(database: PomonaDatabase) : Repository {
        return RepositoryImpl(database)
    }

}