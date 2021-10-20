package com.carkzis.pomona

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .baseUrl("http://bit.ly/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface FruitApiService {

    @GET("1QW6CrK")
    suspend fun getFruitInformation(): FruitContainer

}

object FruitApi {
    val retroService: FruitApiService by lazy {
        retrofit.create(FruitApiService::class.java)
    }
}

interface StatsApiService {

    @GET("1M9S7RY")
    suspend fun generateUsageStats(
        @Query("event") event: String,
        @Query("data") data: String
    )

}

object StatsApi {
    val retroService: StatsApiService by lazy {
        retrofit.create(StatsApiService::class.java)
    }
}