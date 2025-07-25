package com.example.mazesolver.list.presentation.viewmodel

import com.example.mazesolver.core.domain.util.Error
import com.example.mazesolver.core.presentation.model.Maze

data class MazeListState(
    val isLoading: Boolean = false,
    val mazes: List<Maze> = emptyList(),
    val error: Error? = null
)
