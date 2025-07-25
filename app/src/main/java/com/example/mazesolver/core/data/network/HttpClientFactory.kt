package com.example.mazesolver.core.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class HttpClientFactory : OkHttpClient() {
    val okHttpClient: OkHttpClient

    init {
        val builder = Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClient = builder.addInterceptor(interceptor).build()
    }
}