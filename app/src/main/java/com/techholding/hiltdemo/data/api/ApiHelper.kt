package com.techholding.hiltdemo.data.api

import com.techholding.hiltdemo.data.model.User
import retrofit2.Response

interface ApiHelper {
    suspend fun getUsers(): Response<List<User>>
}