package com.example.genesysapp.api

import com.example.genesysapp.data.RandomUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserApi {

    companion object {
        const val BASE_URL = "https://randomuser.me/"
        const val SEED = "4a4d397ffc2198e4"
        const val COUNT = 12
    }

    @GET("api")
    suspend fun getRandomUser(
        @Query("seed") seed : String = SEED,
        @Query("results") numberOfUser : Int = COUNT
    ) : RandomUserResponse
}