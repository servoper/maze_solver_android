package com.example.mazesolver.core.data.network

import com.example.mazesolver.core.domain.util.NetworkError
import com.example.mazesolver.core.domain.util.Result
import retrofit2.Response

fun <T> Response<T>.toResult(): Result<T, NetworkError> {
    this.body()
    return when (code()) {
        in 200..299 -> {
            Result.Success(body())
        }

        408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
        429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
        else -> Result.Error(NetworkError.UNKNOWN)
    }
}