package com.example.mazesolver.core

import com.example.mazesolver.core.domain.util.NetworkError
import org.junit.Assert.*
import org.junit.Test

class NetworkErrorTest {
    @Test
    fun `all network error types are present`() {
        val errors = NetworkError.entries
        assertTrue(errors.contains(NetworkError.REQUEST_TIMEOUT))
        assertTrue(errors.contains(NetworkError.TOO_MANY_REQUESTS))
        assertTrue(errors.contains(NetworkError.NO_INTERNET))
        assertTrue(errors.contains(NetworkError.SERVER_ERROR))
        assertTrue(errors.contains(NetworkError.SERIALIZATION))
        assertTrue(errors.contains(NetworkError.UNKNOWN))
    }
} 