package com.example.mazesolver.solvedmaze.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import androidx.core.graphics.get
import androidx.core.graphics.set
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.LinkedList
import java.util.Queue

private const val START_COLOR = Color.RED // #FFFF0000
private const val END_COLOR = Color.BLUE  // #FF0000FF
private const val WALL_COLOR = Color.BLACK // #FF000000
private const val PATH_COLOR = Color.GREEN // #FF00FF00

class MazeSolverRepository(val context: Context, private val imageLoader: ImageLoader) {
    suspend fun solveMaze(imageUrl: String): Bitmap? {
        try {
            val request = ImageRequest.Builder(context).data(imageUrl)
                .allowHardware(false)
                .build()

            val result = imageLoader.execute(request)
            if (result is SuccessResult) {
                val drawable = result.drawable
                if (drawable is BitmapDrawable) {
                    val originalBitmap = drawable.bitmap
                    return solveAndDrawMaze(originalBitmap)
                }
            }

            return null
        } catch (e: Exception){
            e.printStackTrace()
            return null
        }
    }

    // Helper data class to represent a point (pixel coordinate) in the maze
    data class Point(val x: Int, val y: Int)

    suspend fun solveAndDrawMaze(originalBitmap: Bitmap): Bitmap = withContext(Dispatchers.Default) {
        val mutableBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val width = mutableBitmap.width
        val height = mutableBitmap.height

        var startPoint: Point? = null
        var endPoint: Point? = null

        // Find start and end points
        for (y in 0 until height) {
            for (x in 0 until width) {
                val pixel = mutableBitmap[x, y]
                if (pixel == START_COLOR) {
                    startPoint = Point(x, y)
                } else if (pixel == END_COLOR) {
                    endPoint = Point(x, y)
                }
                if (startPoint != null && endPoint != null) break
            }
            if (startPoint != null && endPoint != null) break
        }

        // If start or end points are not found, return the original bitmap
        if (startPoint == null || endPoint == null) {
            println("Error: Start (red) or end (blue) point not found in the maze.")
            return@withContext originalBitmap
        }

        // BFS (Breadth-First Search) implementation for pathfinding
        val queue: Queue<Point> = LinkedList()

        val visited = Array(height) { BooleanArray(width) }

        val parentDirection = Array(height) { ByteArray(width) { -1 } }

        queue.add(startPoint)
        visited[startPoint.y][startPoint.x] = true

        var pathFound = false

        while (queue.isNotEmpty()) {
            val current = queue.poll()

            if(current== null) {
                continue
            }

            if (current == endPoint) {
                pathFound = true
                break // Path found!
            }

            // Iterate through all possible directions
            for (direction in Direction.entries) {
                val nextX = current.x + direction.dx
                val nextY = current.y + direction.dy
                val neighbor = Point(nextX, nextY)

                // Check bounds
                if (nextX >= 0 && nextX < width && nextY >= 0 && nextY < height) {
                    val neighborColor = mutableBitmap[nextX, nextY]

                    // Check if it's not a wall and not visited
                    if (neighborColor != WALL_COLOR && !visited[nextY][nextX]) {
                        queue.add(neighbor)
                        visited[nextY][nextX] = true
                        // Store the direction from which we arrived at this neighbor
                        parentDirection[nextY][nextX] = direction.opposite().ordinal.toByte()
                    }
                }
            }
        }

        // Draw the path if found
        if (pathFound) {
            var current = endPoint
            // Traverse back from the end point to the start point using parentDirection
            while (current != null && current != startPoint) {
                // Only draw if the pixel is not the start or end color (to preserve them)
                if (mutableBitmap[current.x, current.y] != START_COLOR &&
                    mutableBitmap[current.x, current.y] != END_COLOR) {
                    mutableBitmap[current.x, current.y] = PATH_COLOR // Set pixel to green
                }

                // Reconstruct the parent based on the stored direction
                val dirOrdinal = parentDirection[current.y][current.x]
                if (dirOrdinal != (-1).toByte()) {
                    val cameFromDirection = Direction.entries[dirOrdinal.toInt()]
                    current = Point(current.x + cameFromDirection.dx, current.y + cameFromDirection.dy)
                } else {
                    // This should ideally not happen if pathFound is true and current is not startPoint
                    current = null
                }
            }
            // Ensure start and end points are not overwritten if they are part of the path
            mutableBitmap[startPoint.x, startPoint.y] = START_COLOR
            mutableBitmap[endPoint.x, endPoint.y] = END_COLOR
        } else {
            println("No path found from red to blue.")
        }

        mutableBitmap
    }

    enum class Direction(val dx: Int, val dy: Int) {
        NORTH(0, -1),
        SOUTH(0, 1),
        EAST(1, 0),
        WEST(-1, 0);

        // Helper to get the opposite direction for backtracking
        fun opposite(): Direction {
            return when (this) {
                NORTH -> SOUTH
                SOUTH -> NORTH
                EAST -> WEST
                WEST -> EAST
            }
        }
    }
}
