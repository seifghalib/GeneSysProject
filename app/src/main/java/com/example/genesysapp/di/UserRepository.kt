package com.example.genesysapp.di

import com.example.genesysapp.api.RandomUserApi
import com.example.genesysapp.data.RandomUserResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val randomUserApi: RandomUserApi) {

    suspend fun getRandomUserList(): RandomUserResponse {
        return randomUserApi.getRandomUser()
    }
}