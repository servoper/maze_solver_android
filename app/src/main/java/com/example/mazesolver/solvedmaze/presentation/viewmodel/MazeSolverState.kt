package com.example.mazesolver.solvedmaze.presentation.viewmodel

import android.graphics.Bitmap

data class MazeSolverState(
    val isLoading: Boolean = false,
    val mazeName: String?,
    val solvedImageBitmap: Bitmap? = null
)
