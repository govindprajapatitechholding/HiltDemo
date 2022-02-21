package com.techholding.hiltdemo.data.repository

import com.techholding.hiltdemo.data.api.ApiHelper
import com.techholding.hiltdemo.data.api.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiService) {

    suspend fun getUsers() =  apiHelper.getUsers()

}