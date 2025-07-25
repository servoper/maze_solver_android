package com.example.mazesolver.list

import com.example.mazesolver.core.domain.util.NetworkError
import com.example.mazesolver.core.domain.util.Result
import com.example.mazesolver.list.data.dto.MazeListDto
import com.example.mazesolver.list.data.network.MazesRemoteDataSource
import com.example.mazesolver.list.data.repository.MazeListRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MazeListRepositoryImplTest {
    private lateinit var dataSource: MazesRemoteDataSource
    private lateinit var repository: MazeListRepositoryImpl

    @Before
    fun setUp() {
        dataSource = mock()
        repository = MazeListRepositoryImpl(dataSource)
    }

    @Test
    fun `fetchMazes returns success`() = runTest {
        val dto = MazeListDto(listOf(), 0)
        whenever(dataSource.fetchMazes()).thenReturn(Result.Success(dto))
        val result = repository.fetchMazes()
        assertTrue(result is Result.Success)
        assertEquals(dto, (result as Result.Success).data)
    }

    @Test
    fun `fetchMazes returns error`() = runTest {
        whenever(dataSource.fetchMazes()).thenReturn(Result.Error(NetworkError.NO_INTERNET))
        val result = repository.fetchMazes()
        assertTrue(result is Result.Error)
        assertEquals(NetworkError.NO_INTERNET, (result as Result.Error).error)
    }
} 