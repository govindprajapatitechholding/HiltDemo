package com.techholding.hiltdemo.data.api

import com.techholding.hiltdemo.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}