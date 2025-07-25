package com.example.mazesolver.core

import com.example.mazesolver.core.domain.util.NetworkError
import com.example.mazesolver.core.domain.util.Result
import com.example.mazesolver.core.domain.util.onError
import com.example.mazesolver.core.domain.util.onSuccess
import org.junit.Assert.*
import org.junit.Test

class ResultTest {
    @Test
    fun `Success returns correct data`() {
        val result = Result.Success(42)
        assertEquals(42, result.data)
    }

    @Test
    fun `Error returns correct error`() {
        val error = NetworkError.NO_INTERNET
        val result = Result.Error<NetworkError>(error)
        assertEquals(error, result.error)
    }

    @Test
    fun `onSuccess executes action on success`() {
        var called = false
        val result = Result.Success(1).onSuccess { called = true }
        assertTrue(called)
        assertTrue(result is Result.Success)
    }

    @Test
    fun `onError executes action on error`() {
        var called = false
        val error = NetworkError.SERVER_ERROR
        val result = Result.Error(error).onError { called = true }
        assertTrue(called)
        assertTrue(result is Result.Error)
    }
} 