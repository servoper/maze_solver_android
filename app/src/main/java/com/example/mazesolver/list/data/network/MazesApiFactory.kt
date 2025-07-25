package com.example.mazesolver.list.data.network

import com.example.mazesolver.core.data.network.HttpClientFactory
import com.example.mazesolver.core.data.parser.GsonFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_URL = "https://downloads-secured.bluebeam.com/homework/"

class MazesApiFactory(gson: GsonFactory, httpClientFactory: HttpClientFactory) {
    val api: MazesApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson.gson))
        .baseUrl(API_URL)
        .client(httpClientFactory)
        .build()
        .create(MazesApiService::class.java)
}