package com.carkzis.pomona.data.remote

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Initiates a Retrofit object.
private val retrofit = Retrofit.Builder()
    .baseUrl("https://bit.ly/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

/**
 * This is a retrofit service for retrieving fruit data from the server.
 */
interface FruitApiService {

    @GET("1QW6CrK")
    suspend fun getFruitInformation(): FruitContainer

}

/**
 * This is the entrypoint for generating usages stats via GET requests.
 */
object FruitApi {
    val retroService: FruitApiService by lazy {
        retrofit.create(FruitApiService::class.java)
    }
}

/**
 * This is a retrofit service for retrieving fruit data from the server.
 */
interface StatsApiService {

    @GET("1M9S7RY")
    suspend fun generateUsageStats(
        @Query("event") event: String,
        @Query("data") data: String
    )

}

/**
 * This is the entrypoint for accessing the StatsApi.
 */
object StatsApi {
    val retroService: StatsApiService by lazy {
        retrofit.create(StatsApiService::class.java)
    }
}