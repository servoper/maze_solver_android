package com.example.mazesolver.list.presentation.viewmodel

import com.example.mazesolver.core.presentation.model.Maze

sealed interface MazeListActions {
    data class ItemSelected(val maze: Maze) : MazeListActions
}