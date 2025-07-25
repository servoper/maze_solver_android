package com.example.mazesolver.solvedmaze

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import android.graphics.Bitmap
import com.example.mazesolver.solvedmaze.domain.MazeSolverRepository
import com.example.mazesolver.solvedmaze.presentation.viewmodel.SolvedMazeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SolvedMazeViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: MazeSolverRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `state is updated with solved image on success`() = runTest {
        val bitmap = mock<Bitmap>()
        whenever(repository.solveMaze("http://test.url/image.png")).thenReturn(bitmap)
        val viewModel = SolvedMazeViewModel("Test Maze", "http://test.url/image.png", repository)
        advanceUntilIdle()
        val state = viewModel.state
            .drop(1)
            .first { !it.isLoading }
        assertEquals(bitmap, state.solvedImageBitmap)
    }

    @Test
    fun `state is updated with null on failure`() = runTest {
        whenever(repository.solveMaze("http://test.url/image.png")).thenReturn(null)
        val viewModel = SolvedMazeViewModel("Test Maze", "http://test.url/image.png", repository)
        advanceUntilIdle()
        val state = viewModel.state
            .first { !it.isLoading }
        assertNull(state.solvedImageBitmap)
    }
} 