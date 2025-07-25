package com.example.mazesolver.solvedmaze

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import coil.ImageLoader
import com.example.mazesolver.solvedmaze.domain.MazeSolverRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class MazeSolverRepositoryTest {
    private lateinit var context: Context
    private lateinit var imageLoader: ImageLoader
    private lateinit var repository: MazeSolverRepository

    @Before
    fun setUp() {
        context = mock()
        imageLoader = mock()
        repository = MazeSolverRepository(context, imageLoader)
    }

    @Test
    fun `solveAndDrawMaze returns original bitmap if no start or end point`() = runTest {
        val bitmap = Bitmap.createBitmap(5, 5, Bitmap.Config.ARGB_8888)
        val result = repository.solveAndDrawMaze(bitmap)
        assertEquals(bitmap, result)
    }

    @Test
    fun `solveAndDrawMaze finds path in simple maze`() = runTest {
        val bitmap = Bitmap.createBitmap(3, 3, Bitmap.Config.ARGB_8888)

        bitmap.eraseColor(Color.WHITE)

        bitmap.setPixel(0, 0, Color.RED)
        bitmap.setPixel(2, 2, Color.BLUE)

        val result = repository.solveAndDrawMaze(bitmap)

        assertEquals(Color.GREEN, result.getPixel(0, 1))
        assertEquals(Color.RED, result.getPixel(0, 0))
        assertEquals(Color.BLUE, result.getPixel(2, 2))
    }

    @Test
    fun `solveAndDrawMaze returns original bitmap if no path exists`() = runTest {
        val bitmap = Bitmap.createBitmap(3, 3, Bitmap.Config.ARGB_8888)
        bitmap.setPixel(0, 0, Color.RED)
        bitmap.setPixel(2, 2, Color.BLUE)

        bitmap.setPixel(1, 0, Color.BLACK)
        bitmap.setPixel(1, 1, Color.BLACK)
        bitmap.setPixel(1, 2, Color.BLACK)
        val result = repository.solveAndDrawMaze(bitmap)

        assertEquals(bitmap.getPixel(0,0), result.getPixel(0,0))
        assertEquals(bitmap.getPixel(2,2), result.getPixel(2,2))
        assertEquals(bitmap.getPixel(1,1), result.getPixel(1,1))
        assertEquals(bitmap.getPixel(1,0), result.getPixel(1,0))
        assertEquals(bitmap.getPixel(0,1), result.getPixel(0,1))
    }
} 