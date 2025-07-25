package com.example.mazesolver.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mazesolver.core.domain.util.Result
import com.example.mazesolver.list.data.dto.MazeListDto
import com.example.mazesolver.list.domain.MazeListRepository
import com.example.mazesolver.list.presentation.viewmodel.MazeListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class MazeListViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: MazeListRepository
    private lateinit var viewModel: MazeListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = MazeListViewModel(repository)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `state is updated with maze list on success`() = runTest {
        val dto = MazeListDto(listOf(), 0)
        whenever(repository.fetchMazes()).thenReturn(Result.Success(dto))
        val state = viewModel.state.first { !it.isLoading }
        assertEquals(dto.list, state.mazes)
        assertNull(state.error)
    }
} 