package com.techholding.hiltdemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.techholding.hiltdemo.data.api.ApiService
import com.techholding.hiltdemo.data.repository.MainRepository
import com.techholding.hiltdemo.utils.MockResponseFileReader
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(JUnit4::class)
class ApiResponseTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val server = MockWebServer()
    private lateinit var repository: MainRepository
    private lateinit var mockedResponse: String

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    @Before
    fun init() {
        server.start(8000)

        val BASE_URL = server.url("/").toString()

        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        val service = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build().create(ApiService::class.java)

        repository = MainRepository(service)
    }

    @Test
    fun testApiSuccess() {
        mockedResponse = MockResponseFileReader("success.json").content

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )

        val response = runBlocking { repository.getUsers() }
        val json = gson.toJson(response.body())
        val resultResponse = JsonParser.parseString(json)
        val expectedresponse = JsonParser.parseString(mockedResponse)

        Assert.assertNotNull(response)
        Assert.assertTrue(resultResponse.equals(expectedresponse))
    }
}