package com.carkzis.pomona

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideFruitDao(database: PomonaDatabase) : FruitDao {
        return database.fruitDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : PomonaDatabase {
        return Room.databaseBuilder(
            context,
            PomonaDatabase::class.java,
            "fruit",
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideRepository(database: PomonaDatabase) : Repository {
        return RepositoryImpl(database)
    }

}